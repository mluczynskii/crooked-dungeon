package items;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics2D;
import main.GamePanel;
import entity.Player;

public abstract class Item {
    static String path = "/graphic_assets/sprites/items/";
    String name, description;
    BufferedImage sprite;
    int cost;
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
    void drawItem (Graphics2D g, int x, int y) {
        if (sprite == null)
            return;
        g.drawImage(sprite, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
    }
    public abstract void action (Player player);
}
