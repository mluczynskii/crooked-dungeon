package pickup;

import entity.Player;

public class Coin extends Pickup {
    public void action (Player player) {
        player.money = player.money + 1;
    }
    public Coin (int x, int y) {
        this.x = x;
        this.y = y;
    }
}
