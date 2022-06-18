package entity;

import java.awt.image.BufferedImage;

import main.Drawable;
import main.GamePanel;
import java.awt.geom.Area;
import java.awt.*;

public abstract class Entity implements Drawable {
    public int speed, x, y;
    public double maxHealth, currentHealth;
    public double dmg;

    public BufferedImage idle;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction = "up";

    public int spriteCounter=0;
    public int spriteNum=1;
    public Area solidArea;
    public boolean collisionOn=false;

    static final int spriteChangeRate = 14;
    void setSolidArea (int x, int y, int width, int height) {
        solidArea = new Area (new Rectangle(x * GamePanel.scale, y * GamePanel.scale, width * GamePanel.scale, height * GamePanel.scale));
    }
    void setStats (double maxHealth, int speed) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.speed = speed;
    }
    public void setPosition (int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void draw (Graphics2D g) {
        g.drawImage(idle, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
    }
    public int height () { return this.y; }
    public int compareTo (Drawable e) { // Checks which entity is higher
        if (this.height() > e.height())
            return 1;
        else if (this.height() == e.height())
            return 0;
        return -1;
    }
}
