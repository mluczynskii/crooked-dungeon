package entity;

import java.util.Random;
import world.Room;

public class Crooked extends Monster {
    Random rand = new Random();
    static int[] possibleDrops = {0};

    public Crooked (int x, int y, Room room) {
        super(room);
        loadIdle("crooked");
        setStats(200, 3, 20);
        setPosition(x, y);

        // TODO: Add solidArea
        //setSolidArea(2, 2, 28, 28);
    }
    @Override
    public void update() {
        // TODO Auto-generated method stub    
    }

    @Override
    void drop() {
        soundEffects.setFile("crooked-death.wav");
        soundEffects.play(0.1f);
        int index = possibleDrops[rand.nextInt(possibleDrops.length)];
        generateDrop(index); 
    }
    
}
