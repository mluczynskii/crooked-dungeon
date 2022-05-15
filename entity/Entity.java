package entity;
import java.awt.image.BufferedImage;

public class Entity {
    public int speed, x, y;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter=0;
    public int spriteNum=1;

    static final int spriteChangeRate = 14;
}
