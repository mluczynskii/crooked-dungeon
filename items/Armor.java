package items;

import entity.Player;

public class Armor extends Item {
    static String info = "Reduces incoming dmg by 20%.\nDoes not stack";
    public Armor () {
        super ("Armor", info, 15);
        super.loadImage("armor.png");
    }
    @Override
    public void action (Player player) {
        player.dmg_reduction = 0.8;
    }
}
