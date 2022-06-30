package items;

import entity.Player;

public class Halo extends Item {
    static String info = "Player's HP becomes 1 but\nhe deals enormous amounts of dmg";
    public Halo () {
        super("Holy halo", info, 40);
        super.loadImage("halo.png");
    }
    @Override
    public void action (Player player) {
        player.maxHealth = 1;
        player.currentHealth = 1;
        player.dmg = 999;
    }
}
