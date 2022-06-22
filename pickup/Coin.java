package pickup;

import world.*;
import entity.Player;
import main.GamePanel;
import java.awt.geom.*;
import java.awt.*;

public class Coin extends Pickup {
    public void action (Player player) {
        player.money = player.money + 1;
    }
    public Coin (int x, int y, Room room) {
        this.room = room;
        this.x = x;
        this.y = y;
        loadImage("coin.png");
        this.solidArea = new Area (new Rectangle (0, 0, GamePanel.tileSize, GamePanel.tileSize));
    }
}
