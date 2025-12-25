package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity {

    public int defaultX, defaultY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCount = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int collisionSoundCounter = 0;
    public final int COLLISION_COOLDOWN = 30;
}
