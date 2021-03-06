package world;

import java.util.Random;
import entity.NPC_Shopkeeper;
import java.awt.Graphics2D;
import items.*;
import main.GamePanel;


public class Shop extends Room {
    NPC_Shopkeeper shopkeeper;
    
    static String path = "/graphic_assets/layouts/shops/";
    static String[] shop_layouts = {"shop1.txt", "shop2.txt", "shop3.txt"};

    String[] lowQualityItems = {"items.MagicMushroom", "items.Poison"};
    String[] medQualityItems = {"items.FishTank", "items.Armor"};
    String[] highQualityItems = {"items.DevilPact", "items.Halo"};

    
    static Random rand = new Random ();

    public Shop (GamePanel gp) {
        super(path + shop_layouts[rand.nextInt(shop_layouts.length)], gp, true);
        this.stock = new Item[3];
        this.shopkeeper = new NPC_Shopkeeper(this);
        generateStock();
        entityList.add(shopkeeper);
        npcList.add(shopkeeper);
    }
    void generateStock () {
        int index = rand.nextInt(lowQualityItems.length);
        stock[0] = generateItem(lowQualityItems[index]);
        
        index = rand.nextInt(medQualityItems.length);
        stock[1] = generateItem(medQualityItems[index]);

        index = rand.nextInt(highQualityItems.length);
        stock[2] = generateItem(highQualityItems[index]);
    }
    Item generateItem (String itemName) {
        Item item = null;
        try {
            Class<?> classDef = Class.forName(itemName);
            item = (Item) classDef.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Couldn't find class: " + itemName);
        }
        return item;
    }
    @Override
    void drawRoom (Graphics2D g) {
        super.drawRoom(g);
        int x = GamePanel.screenWidth/3 - GamePanel.tileSize/2;
        int step = GamePanel.screenWidth/6;
        int y = GamePanel.screenHeight * 2/3;
        for (Item item : stock) {
            if (item != null)
                item.drawItem(g, x, y);
            x += step;
        }
    }
}
