package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.RpgPanel;;

public abstract class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, RpgPanel gp) {
        int screenX = worldX - gp.player.defaultX + gp.player.screenX;
        int screenY = worldY - gp.player.defaultY + gp.player.screenY;

        if (worldX + gp.TileSize > gp.player.defaultX - gp.player.screenX &&
                worldX - gp.TileSize < gp.player.defaultX + gp.player.screenX &&
                worldY + gp.TileSize > gp.player.defaultY - gp.player.screenY &&
                worldY - gp.TileSize < gp.player.defaultY + gp.player.screenY) {

            g2.drawImage(image, screenX, screenY, gp.TileSize, gp.TileSize, null);
        }
    }
}
