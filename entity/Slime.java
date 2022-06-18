package entity;

import java.util.Random;
import javax.imageio.ImageIO;

public class Slime extends Entity {
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
    @Override
    public void update () {
        System.out.println("chuj");
    }
    public String speak () {
        return "gluurb";
    }
}
