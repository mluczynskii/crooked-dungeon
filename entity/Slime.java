package entity;

import java.util.Random;
import javax.imageio.ImageIO;

import main.CollisionChecker;

public class Slime extends Monster {
    Random rand = new Random();
    static String path = "/graphic_assets/characters/slime/";
    int interval = 0;
    public Slime (int x, int y) {
        try {
            this.idle = ImageIO.read(getClass().getResourceAsStream(path + "slime.png"));
        } catch (Exception e) {
            System.out.println("Missing sprites");
        }
        setStats(100, 2);
        setPosition(x, y);
        setSolidArea(2, 2, 28, 28);
    }
    public void update () {
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
        CollisionChecker.checkTiles(this);
        if (collisionOn != true) {
            switch (direction) {
                case "up": y -= speed; break;
                case "down": y += speed; break;
                case "right": x += speed; break;
                case "left": x -= speed; break;
            }
        }
        else
            interval = 120;
    }
}
