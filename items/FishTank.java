package items;

import entity.Player;

public class FishTank extends Item {
    static String info = "We don't know how, but it\ngives player 1 extra speed point";
    public FishTank () {
        super("Fish Tank", info, 15);
        super.loadImage("fish_tank.png");
    }
    @Override
    public void action (Player player) {
        player.speed = player.speed + 1;
    }
}
