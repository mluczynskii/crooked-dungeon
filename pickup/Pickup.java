package pickup;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import entity.Player;
import main.Drawable;
import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import world.*;

public abstract class Pickup implements Drawable {
    static String path = "/graphic_assets/sprites/pickups/";
    static int pickupSize = GamePanel.tileSize / 4;
    BufferedImage sprite;
    public int x, y;
    public Area solidArea;
    Room room;
    abstract void action (Player player);
    public void getPickedUp (Player player) {
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
