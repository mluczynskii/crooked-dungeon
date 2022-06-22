package entity;

import world.*;
import pickup.*;

public abstract class Monster extends Entity {
    Room room;
    static String[] allDrops = {"pickup.Coin"};
    public abstract void update ();
    void die () {
        room.monsterList.remove(this);
        room.entityList.remove(this);
        generateDrop (0);
        // drop ();
    }
    void generateDrop (int index) {
        Pickup pickup = null;
        String pickupName = allDrops[index];
        try {
            Class<?> classDef = Class.forName(pickupName);
            Class<?>[] cArg = {Integer.TYPE, Integer.TYPE, Room.class};
            pickup = (Pickup) classDef.getDeclaredConstructor(cArg).newInstance(x, y, this.room);
            this.room.pickupList.add(pickup);
        } catch (Exception e) {
            System.out.println("Something's wrong with generating: " + pickupName);
            e.printStackTrace();
        }
    }
    // public abstract void drop ();
}
