package entity;

import java.io.IOException;
import javax.imageio.ImageIO;
import world.Shop;
import main.GamePanel;

public class Shopkeeper extends Entity {
    Shop shop;
    static String path = "/graphic_assets/characters/shopkeeper/";
    public Shopkeeper () {
        try {
            this.idle = ImageIO.read(getClass().getResourceAsStream(path + "spamton.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.setStats(Integer.MAX_VALUE, 0);
        super.setPosition((Integer)GamePanel.screenWidth/2 - GamePanel.tileSize/2, (Integer)GamePanel.screenHeight/3 - GamePanel.tileSize/3);
        super.setSolidArea(10, 0, 15, 32);
    }
}
