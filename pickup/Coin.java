package pickup;

import world.*;
import entity.Player;

public class Coin extends Pickup {
    public void action (Player player) {
        player.money = player.money + 2;
    }
    public Coin (int x, int y, Room room) {
        super (x, y, room);
        loadImage("coin.png");
        setSolidArea(0, 0, pickupSize, pickupSize);
    }
}
