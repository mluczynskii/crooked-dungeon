package items;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics2D;
import main.GamePanel;
import entity.Player;
import java.awt.geom.Area;
import java.awt.Rectangle;

public abstract class Item {
    static String path = "/graphic_assets/sprites/items/";
    public String name, description;
    BufferedImage sprite;
    public int cost;
    public boolean lookup = false;
    public Area buyArea = new Area (new Rectangle (0, 0, GamePanel.tileSize * GamePanel.scale, GamePanel.tileSize * GamePanel.scale));
    public Item (String name, String description, int cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
    }
    void loadImage (String filename) {
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream(path + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void drawItem (Graphics2D g, int x, int y) {
        g.drawImage(sprite, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
    }
    public abstract void action (Player player);
}
