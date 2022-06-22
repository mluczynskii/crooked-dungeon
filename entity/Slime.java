package entity;

import java.util.Random;
import javax.imageio.ImageIO;
import world.*;
import main.CollisionChecker;
import main.GamePanel;

public class Slime extends Monster {
    Random rand = new Random();
    static String path = "/graphic_assets/characters/slime/";
    int interval = 0;
    boolean dead = false;
    int[] possibleDrops = {0, 1};

    public Slime (int x, int y, Room room) {
        this.room = room;
        try {
            this.idle = ImageIO.read(getClass().getResourceAsStream(path + "slime.png"));
        } catch (Exception e) {
            System.out.println("Missing sprites");
        }
        setStats(100, 2, 2);
        setPosition(x, y);
        setSolidArea(2, 2, 28, 28);
    }
    void drop () {
        int index = rand.nextInt(possibleDrops.length);
        generateDrop(possibleDrops[index]);
    }
    public void update () {
        if (dead)
            die(); // Yeah well...

        interval++;
        if (interval >= 120) {
            int choice = rand.nextInt(4);
            switch (choice) {
                case 0: direction = "up"; break;
                case 1: direction = "down"; break;
                case 2: direction = "left"; break;
                case 3: direction = "right"; break;
            }
            interval = 0;
        }

        collisionOn = false;
        CollisionChecker.checkMonster(this);
        
        if (collisionOn != true) {
            switch (direction) {
                case "up": y = Math.max(GamePanel.tileSize, y - speed); break;
                case "down": y = Math.min(GamePanel.screenHeight - GamePanel.tileSize, y + speed); break;
                case "right": x = Math.min(GamePanel.screenWidth - GamePanel.tileSize, x + speed); break;
                case "left": x = Math.max(GamePanel.tileSize, x - speed); break;
            }
        }
        else
            interval = 120;

        if (currentHealth == 0) 
            dead = true;
    }
}
