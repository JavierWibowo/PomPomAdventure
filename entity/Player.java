package entity;

import main.RpgPanel;
import main.Controller;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends Entity {

    RpgPanel gp;
    Controller controls;

    public final int screenX;
    public final int screenY;

    private int seconds = 0;
    private int collideSeconds = 0;
    private boolean executed = false;

    public Player(RpgPanel gp, Controller controls) {

        this.gp = gp;
        this.controls = controls;

        screenX = gp.screenWidth / 2 - (gp.TileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.TileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 7;
        solidArea.y = 20;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 33;
        solidArea.height = 28;

        setDefaultValues();
        getPlayerImg();
    }

    public void setDefaultValues() {

        defaultX = gp.TileSize * 23;
        defaultY = gp.TileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImg() {

        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/walkingSprites/pompom_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/walkingSprites/pompom_up_2.png"));

            down1 = ImageIO.read(getClass().getResourceAsStream("/walkingSprites/pompom_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/walkingSprites/pompom_down_2.png"));

            left1 = ImageIO.read(getClass().getResourceAsStream("/walkingSprites/pompom_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/walkingSprites/pompom_left_2.png"));

            right1 = ImageIO.read(getClass().getResourceAsStream("/walkingSprites/pompom_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/walkingSprites/pompom_right_2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (controls.up == true || controls.down == true || controls.left == true || controls.right == true) {
            if (controls.up == true) {
                direction = "up";
            } else if (controls.down == true) {
                direction = "down";
            } else if (controls.left == true) {
                direction = "left";
            } else if (controls.right == true) {
                direction = "right";
            }

            // CHECKING TILE COLLISION
            collisionOn = false;
            gp.checker.checkTile(this);

            // CHECKING OBJECT COLLISION
            int objIndex = gp.checker.checkObject(this, true);
            pickUpObject(objIndex);

            if (!collisionOn) {
                // Movement logic
                switch (direction) {
                    case "up":
                        defaultY -= speed;
                        break;
                    case "down":
                        defaultY += speed;
                        break;
                    case "left":
                        defaultX -= speed;
                        break;
                    case "right":
                        defaultX += speed;
                        break;
                }
                if (!executed) {
                    gp.playSE(9);
                    timer();
                    executed = true;
                }

                spriteCount++;

                if (spriteCount > 12) {
                    if (spriteNum == 1)
                        spriteNum = 2;
                    else if (spriteNum == 2)
                        spriteNum = 1;
                    spriteCount = 0;
                }
            } else {
                if (collisionSoundCounter <= 0) {
                    gp.playSE(8);
                    collisionSoundCounter = COLLISION_COOLDOWN;
                }
            }
        }
        if (collisionSoundCounter > 0) {
            collisionSoundCounter--;
        }
    }

    public void pickUpObject(int i) {

        if (i != 999) {

            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Slow":
                    gp.playSE(5);
                    Timer slowTimer = new Timer();
                    TimerTask slowTask = new TimerTask() {
                        public void run() {
                            while (speed > 2)
                                speed -= 1;
                            seconds++;
                            if (seconds == 4) {
                                speed = 4;
                                seconds = 0;
                                slowTimer.cancel();
                            }
                        }
                    };
                    slowTimer.scheduleAtFixedRate(slowTask, 0, 1000);
                    gp.obj[i] = null;
                    gp.ui.showMessage("You Got into SlowOrb.");
                    break;
                case "Fast":
                    gp.playSE(4);
                    while (speed <= 5)
                        speed += 1;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Speed Boost Potion.");
                    break;
                case "Teleport":
                    gp.playSE(2);

                    int distance = (int) (Math.random() * 3) + 5;
                    int totalSetback = distance * gp.TileSize;

                    int targetX = defaultX;
                    int targetY = defaultY;

                    if (direction.equals("up"))
                        targetY += totalSetback;
                    else if (direction.equals("down"))
                        targetY -= totalSetback;
                    else if (direction.equals("left"))
                        targetX += totalSetback;
                    else if (direction.equals("right"))
                        targetX -= totalSetback;

                    // Boundary Check
                    if (targetX >= 0 && targetX < gp.maxWorldCol * gp.TileSize &&
                            targetY >= 0 && targetY < gp.maxWorldRow * gp.TileSize) {

                        int targetCol = targetX / gp.TileSize;
                        int targetRow = targetY / gp.TileSize;

                        // Collision Check
                        int tileNum = gp.tiles.mapTile[targetCol][targetRow];
                        if (gp.tiles.tile[tileNum].collision == false) {
                            defaultX = targetX;
                            defaultY = targetY;
                            gp.ui.showMessage("Warped back " + distance + " tiles!");
                        } else {
                            gp.ui.showMessage("Teleport blocked by a wall!");
                        }
                    } else {
                        gp.ui.showMessage("Cannot teleport outside the map!");
                    }

                    gp.obj[i] = null;
                    break;
                case "EndTile":
                    gp.obj[i] = null;
                    gp.ui.gameFinished = true;
                    break;
                case "Stuck":
                    gp.playSE(3);
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        public void run() {
                            speed = 0;
                            seconds++;
                            if (seconds == 4) {
                                speed = 4;
                                seconds = 0;
                                timer.cancel();
                            }
                        }
                    };

                    timer.scheduleAtFixedRate(task, 0, 1000);
                    gp.ui.showMessage("You Got into ElectricOrb.");
                    gp.obj[i] = null;
                    break;
            }
        }
    }

    public void timer() {
        Timer collideTimer = new Timer();
        TimerTask collideTask = new TimerTask() {
            public void run() {
                collideSeconds++;
                if (collideSeconds == 2) {
                    executed = false;
                    collideSeconds = 0;
                    collideTimer.cancel();
                }
            }
        };
        collideTimer.scheduleAtFixedRate(collideTask, 0, 260);
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.TileSize, gp.TileSize, null);
    }
}
