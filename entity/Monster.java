package entity;

import world.*;
import main.GamePanel;
import pickup.*;
//import ai.PathFinder;

public abstract class Monster extends Entity {
    Room room;
    //PathFinder ai;
    static String[] allDrops = {"pickup.Coin", "pickup.Heart"};
    public abstract void update ();
    Monster (Room room) {
        this.room = room;
    }
    void die () {
        room.monsterList.remove(this);
        room.entityList.remove(this);
        drop();
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
