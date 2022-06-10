package items;

import entity.Player;

public class DevilPact extends Item {
    static String info = "Increases Player's DMG by 25% at the cost of reducing HP by 10%";
    public DevilPact () {
        super ("Devil Pact", info, 20);
        super.loadImage("devil_pact.png");
    }
    @Override
    public void action (Player player) {
        player.dmg = player.dmg * 1.25;
        player.maxHealth = player.maxHealth * 0.9;
        player.currentHealth = Math.min(player.currentHealth, player.maxHealth);
    }
}
