package pickup;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import entity.Player;
import main.Drawable;
import main.GamePanel;
import main.Sound;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import world.*;
import java.awt.*;

public abstract class Pickup implements Drawable {
    static String path = "/graphic_assets/sprites/pickups/";
    static int pickupSize = GamePanel.tileSize / 2;
    BufferedImage sprite;
    public int x, y;
    public Area solidArea;
    Room room;
    Sound soundEffects = new Sound();
    
    abstract void action (Player player);
    abstract void playSoundEffect();
    public void getPickedUp (Player player) {
        playSoundEffect();
        room.pickupList.remove(this);
        action(player);
    }
    void loadImage (String filename) {
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream(path + filename));
        } catch (Exception e) {
            System.out.println("Missing image: " + filename);
        }
    }
    Pickup (int x, int y, Room room) {
        this.x = x;
        this.y = y;
        this.room = room;
    }
    void setSolidArea (int x, int y, int width, int height) {
        this.solidArea = new Area (new Rectangle (x * GamePanel.scale, y * GamePanel.scale, width * GamePanel.scale, height * GamePanel.scale));
    }
    public int height () { return this.y; }
    public int compareTo (Drawable e) { 
        if (this.height() > e.height())
            return 1;
        else if (this.height() == e.height())
            return 0;
        return -1;
    }
    public void draw (Graphics2D g) {
        g.drawImage(sprite, x, y, pickupSize, pickupSize, null);
    }
}
