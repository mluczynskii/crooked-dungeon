package pickup;

import world.*;
import entity.Player;

public class Coin extends Pickup {
    public void action (Player player) {
        player.money = player.money + 1;
    }
    public Coin (int x, int y, Room room) {
        this.room = room;
        this.x = x;
        this.y = y;
    }
}
