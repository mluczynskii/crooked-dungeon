package entity;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import world.Room;

public class Crooked extends Monster {
    Random rand = new Random();
    static int[] possibleDrops = {0};

    public Crooked (int x, int y, Room room) {
        super(room);
        getImages();
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
    void playDamageSound() {
        soundEffects.setFile("crooked-hit.wav");
        soundEffects.play(0.1f);
    }
    @Override
    void playDeathSound () {
        soundEffects.setFile("crooked-death.wav");
        soundEffects.play(0.1f);
    }
    @Override
    void drop() {
        int index = possibleDrops[rand.nextInt(possibleDrops.length)];
        generateDrop(index); 
    }
    public void getImages() {
        try{
            // Can split into several loops if animation length is different for any of the directions/actions
            for (int i = 1; i <= 2; i++) {
                up.add(ImageIO.read(getClass().getResourceAsStream(path + "crooked_up" + i + ".png")));
                down.add(ImageIO.read(getClass().getResourceAsStream(path + "crooked_down" + i + ".png")));
                left.add(ImageIO.read(getClass().getResourceAsStream(path + "crooked_left" + i + ".png")));
                right.add(ImageIO.read(getClass().getResourceAsStream(path + "crooked_right" + i + ".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
