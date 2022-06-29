package entity;

import world.*;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Sound;
import pickup.*;
//import ai.PathFinder;

public abstract class Monster extends Entity {
    static String path = "/graphic_assets/characters/";
    Room room;
    //PathFinder ai;
    static String[] allDrops = {"pickup.Coin", "pickup.Heart"};
    Sound soundEffects = new Sound();
    public abstract void update ();
    Monster (Room room) {
        this.room = room;
    }
    void die () {
        room.monsterList.remove(this);
        room.entityList.remove(this);
        drop();
    }
    void loadIdle (String name) {
        try {
            this.idle = ImageIO.read(getClass().getResourceAsStream(path + name + "/" + name + ".png"));
        } catch (Exception e) {
            System.out.println("Missing sprites: " + name);
        }
    }
    void generateDrop (int index) {
        Pickup pickup = null;
        String pickupName = allDrops[index];
        try {
            Class<?> classDef = Class.forName(pickupName);
            Class<?>[] cArg = {Integer.TYPE, Integer.TYPE, Room.class};
            pickup = (Pickup) classDef.getDeclaredConstructor(cArg).newInstance(x + GamePanel.tileSize/2, y + GamePanel.tileSize/2, this.room);
            this.room.pickupList.add(pickup);
        } catch (Exception e) {
            System.out.println("Something's wrong with generating: " + pickupName);
            e.printStackTrace();
        }
    }
    abstract void drop ();
}
