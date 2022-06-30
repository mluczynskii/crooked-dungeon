package items;

import entity.Player;

public class Poison extends Item {
    static String info = "Kill the player instantly";
    public Poison () {
        super("Poison", info, 10);
        super.loadImage("poison.png");
    }
    @Override
    public void action (Player player) {
        player.dead = true;
    }
}
