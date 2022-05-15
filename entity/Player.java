package entity;

import main.GamePanel;
import main.KeyController;
import java.awt.*;

public class Player extends Entity {
    GamePanel gp;
    KeyController keyC;

    public Player (GamePanel gp, KeyController keyC) {
        this.gp = gp;
        this.keyC = keyC;

        this.setDefaultValues();
    }
    public void update () {
        if (this.keyC.up == true)
            this.y -= this.speed;
        else if (this.keyC.down == true)
            this.y += this.speed;
        else if (this.keyC.right == true)
            this.x += this.speed;
        else if (this.keyC.left == true)
            this.x -= this.speed;
    }
    public void setDefaultValues () {
        this.x = 100;
        this.y = 100;
        this.speed = 4;
    }
    public void draw (Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.white);
        g2.fillRect(this.x, this.y, gp.tileSize, gp.tileSize);
        g2.dispose();
    }
}
