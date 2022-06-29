package entity;

import java.awt.Rectangle;
import java.util.Random;

//import ai.PathFinder;
import world.*;
import main.CollisionChecker;
import main.GamePanel;

public class Slime extends Monster {
    Random rand = new Random();
    int interval = 0;
    int[] possibleDrops = {0, 1};
    final int invulnerable_cd = 50;

    public Slime (int x, int y, Room room) {
        super (room);
        //this.ai = new PathFinder(room, this);
        loadIdle("slime");
        setStats(100, 2, 10);
        setPosition(x, y);
        setSolidArea(2, 2, 28, 28);
    }
    @Override
    void playDamageSound() {
        soundEffects.setFile("slime-hit.wav");
        soundEffects.play(0.2f);
    }
    @Override
    void playDeathSound() {
        soundEffects.setFile("slime-death.wav");
        soundEffects.play(0.1f);
    }
    @Override
    void drop () {
        int index = rand.nextInt(possibleDrops.length);
        generateDrop(possibleDrops[index]);
    }
    public void update () {
        if (dead)
            die(); // Yeah well...

        interval++;
        if (interval >= 120) {
            int choice = rand.nextInt(4);
            switch (choice) {
                case 0: direction = "up"; break;
                case 1: direction = "down"; break;
                case 2: direction = "left"; break;
                case 3: direction = "right"; break;
            }
            interval = 0;
        }

        collisionOn = false;
        CollisionChecker.checkMonster(this, room);
        
        if (collisionOn != true) {
            Rectangle rect = room.player.solidArea.getBounds(); // This is used to prevent getting stuck inside a monster after entering the room
            switch (direction) {
                case "up": y = Math.max(rect.y + rect.height, y - speed); break;
                case "down": y = Math.min(GamePanel.screenHeight - (rect.y + rect.height) - GamePanel.tileSize, y + speed); break;
                case "right": x = Math.min(GamePanel.screenWidth - (rect.x + rect.width) - GamePanel.tileSize, x + speed); break;
                case "left": x = Math.max(rect.x + rect.width, x - speed); break;
            }
        }
        else
            interval = 120;

        if (invulnerable_tick < invulnerable_cd)
            invulnerable_tick++;
        else invulnerable = false;

        if (currentHealth == 0) 
            dead = true;
    }
}
