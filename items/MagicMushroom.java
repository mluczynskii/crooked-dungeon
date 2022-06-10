package items;

import entity.Player;

public class MagicMushroom extends Item {
    static String info = "Boosts player's health by 25%";
    public MagicMushroom () {
        super("Magic Mushroom", info, 10);
        super.loadImage("magic_mush.png");
    }
    @Override
    public void action (Player player) {
        player.maxHealth = player.maxHealth * 1.25; 
        player.currentHealth = player.maxHealth;
    }
}
