package pickup;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import entity.Player;
import main.Drawable;
import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.geom.Area;

public abstract class Pickup implements Drawable {
    static String path = "/graphic_assets/sprites/pickups/";
    static int pickupSize = GamePanel.tileSize / 4;
    BufferedImage sprite;
    int x, y;
    Area solidArea;
    public abstract void action (Player player);
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
