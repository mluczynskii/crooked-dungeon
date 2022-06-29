package entity;

import java.awt.image.BufferedImage;

import main.Drawable;
import main.GamePanel;
import java.awt.geom.Area;
import java.awt.*;
import java.util.ArrayList;

public abstract class Entity implements Drawable {
    public int speed, x, y;
    public double maxHealth, currentHealth;
    public double dmg;

    public BufferedImage idle;
    public ArrayList<BufferedImage> up, down, left, right; // walking animation frames
    public ArrayList<BufferedImage> at_up, at_down, at_left, at_right; // attack animation frames
    public String direction = "up";

    public int spriteCounter = 0;
    public int spriteNum = 0;
    public Area solidArea = new Area();
    public boolean collisionOn = false;

    public boolean invulnerable = false;
    public int invulnerable_tick = 0;


    static final int spriteChangeRate = 14;
    public void takeDamage (Entity entity) {
        if (invulnerable == false) {
            currentHealth = currentHealth - entity.dmg;
            invulnerable = true;
            invulnerable_tick = 0;
        }
    }
    void setSolidArea (int x, int y, int width, int height) {
        solidArea = new Area (new Rectangle(x * GamePanel.scale, y * GamePanel.scale, width * GamePanel.scale, height * GamePanel.scale));
    }
    void setStats (double maxHealth, int speed, int dmg) {
        this.dmg = dmg;
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

        // Debug
        Rectangle bounds = solidArea.getBounds();
        g.setColor(Color.RED);
        g.drawRect(x + bounds.x, y + bounds.y, bounds.width, bounds.height);

        g.setColor(Color.BLUE);
        g.drawRect(x, y, GamePanel.tileSize, GamePanel.tileSize);
    }
    public int height () { 
        Rectangle rect = this.solidArea.getBounds();
        Point point = rect.getLocation();
        return point.y + this.y;
    }
    public int compareTo (Drawable e) { 
        if (this.height() > e.height())
            return 1;
        else if (this.height() == e.height())
            return 0;
        return -1;
    }
}
