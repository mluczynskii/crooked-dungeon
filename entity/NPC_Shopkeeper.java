package entity;

import java.io.IOException;
import javax.imageio.ImageIO;
import world.Shop;
import main.GamePanel;

public class NPC_Shopkeeper extends NPC {
    Shop shop;
    static String path = "/graphic_assets/characters/shopkeeper/";
    @Override
    void playDamageSound() {
        // Nothing
    }

    public NPC_Shopkeeper (Shop shop) {
        this.shop = shop;
        try {
            this.idle = ImageIO.read(getClass().getResourceAsStream(path + "spamton.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.setStats(Integer.MAX_VALUE, 0, 0);
        super.setPosition((Integer)GamePanel.screenWidth/2 - GamePanel.tileSize/2, (Integer)GamePanel.screenHeight/3 - GamePanel.tileSize/3);
        super.setSolidArea(10, 0, 15, 32);
        String [] tmp = {"Howdy traveler!", "My name is Chris and\nI am but a humble merchant.", "You can look around if you want.", 
                         "So, whachu buyin' son?"};
        loadDialogue(tmp);
        this.dialogueLength = tmp.length;
    


    }
}
