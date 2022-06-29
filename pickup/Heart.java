package pickup;

import entity.Player;
import world.Room;

public class Heart extends Pickup {
    public void action (Player player) {
        player.currentHealth = Math.min(player.maxHealth, player.currentHealth + 10);
    }
    public Heart (int x, int y, Room room) {
        super (x, y, room);
        loadImage("heart.png");
        setSolidArea(0, 0, pickupSize, pickupSize);
    }
    void playSoundEffect () {
        // TODO: Find good heart-pickup sound
    }
}
