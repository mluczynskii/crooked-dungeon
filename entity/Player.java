package entity;

import main.GamePanel.State;
import world.TileManager;
import main.CollisionChecker;
import main.GamePanel;
import main.KeyController;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.geom.Area;
import main.Sound;

public class Player extends Entity {
    static String path = "/graphic_assets/characters/ax/";

    GamePanel gp;
    KeyController keyC;
    public int money = 0;
    public NPC interactionNPC = null;
    public boolean attacking = false;
    boolean canAttack = true;
    Sound soundEffects = new Sound();
    
    
    final int invulnerable_cd = 60;

    final int dialogue_cd = 20;
    int dialogue_tick = 0;
    Boolean dialogue_ok = true;
    

    final int attack_first_frame = 20;
    final int attack_second_frame = 50;
    final int attack_cd = attack_first_frame + attack_second_frame + 15;
    
    int attack_tick = attack_cd;
    public Area attackArea = null;

    final Area solid_at_up = new Area(new Rectangle(0, -GamePanel.tileSize, GamePanel.tileSize, GamePanel.tileSize * 2));
    final Area solid_at_down = new Area(new Rectangle(0, 0, GamePanel.tileSize, GamePanel.tileSize * 2));
    final Area solid_at_left = new Area(new Rectangle(-GamePanel.tileSize, 0, GamePanel.tileSize * 2, GamePanel.tileSize));
    final Area solid_at_right = new Area(new Rectangle(0, 0, GamePanel.tileSize * 2, GamePanel.tileSize));

    final Area solid_default = new Area(new Rectangle(12 * GamePanel.scale, 16 * GamePanel.scale, 9 * GamePanel.scale, 9 * GamePanel.scale));

    public Player (GamePanel gp, KeyController keyC) {
        this.gp = gp;
        this.keyC = keyC;

        up = new ArrayList<>(); down = new ArrayList<>(); left = new ArrayList<>(); right = new ArrayList<>();
        at_up = new ArrayList<>(); at_down = new ArrayList<>(); at_left = new ArrayList<>(); at_right = new ArrayList<>(); 
        
        setDefaultValues();
        getPlayerImage();

        solidArea = solid_default;
    }
    void changeHitbox () {
        if (attackArea != null) return;
        switch (direction) {
            case "up": attackArea = (attacking ? solid_at_up : null); break;
            case "down": attackArea = (attacking ? solid_at_down : null); break;
            case "left": attackArea = (attacking ? solid_at_left : null); break;
            case "right": attackArea = (attacking ? solid_at_right : null); break;
        }
    }
    public void update () {
        if (dead) {
            gp.gameState = State.GAMEOVER;
            return;
        }
        if (attacking == true) {
            attack();
        }
        else if (keyC.attack == true && canAttack == true) { 
            spriteNum = 0;
            spriteCounter = 0;
            attacking = true; 
            canAttack = false;
            attack_tick = 0;
        }
        else if(keyC.up == true || keyC.down == true || keyC.right == true || keyC.left == true){
            if (this.keyC.up == true){ direction = "up"; }
            else if (keyC.down == true){ direction = "down"; }
            else if (keyC.right == true){ direction = "right"; }
            else if (keyC.left == true){ direction = "left"; }

            collisionOn = false;
            CollisionChecker.checkPlayer(this, TileManager.currentRoom);

            if(collisionOn == false){
                switch(direction){
                    case "up": y -= speed; break;
                    case "down": y += speed; break;
                    case "left": x -= speed; break;
                    case "right": x += speed; break;
                }
            }

            spriteCounter++;
            if(spriteCounter > spriteChangeRate){
                switch (direction) {
                    case "up": spriteNum = (spriteNum + 1) % up.size(); break;
                    case "down": spriteNum = (spriteNum + 1) % down.size(); break;
                    case "left": spriteNum = (spriteNum + 1) % left.size(); break;
                    case "right": spriteNum = (spriteNum + 1) % right.size(); break;
                }
                spriteCounter=0;
            }

        }


        if (invulnerable_tick < invulnerable_cd) 
            invulnerable_tick++;
        else invulnerable = false;

        if(dialogue_tick < dialogue_cd)
            dialogue_tick++;
        else dialogue_ok = true;

        if(keyC.z == true && dialogue_ok == true) {
            dialogue_tick = 0;
        }
        if (attack_tick < attack_cd)
            attack_tick++;
        else canAttack = true;

        if(keyC.z == true) {
            interactNPC(interactionNPC);
        }
 
        checkRoomTransition();
    }

    public void dialogue(){
        
        if (dialogue_tick < dialogue_cd) {
            dialogue_tick++;
            return;
        }
        else dialogue_ok = true;
        
        Boolean finished = interactionNPC.checkDialogue();
       
        if(keyC.z == true && dialogue_ok && !finished) {
            interactionNPC.updateDialogue();
            dialogue_tick = 0;
            dialogue_ok = false;
        }
        
        if(finished  && dialogue_ok && keyC.z) {
            dialogue_ok = false;
            dialogue_tick = 0;

            gp.gameState = State.PLAY;
        }
    }
    void attack () {
        spriteCounter++;
        if (spriteCounter < attack_first_frame) spriteNum = 0;
        else if (spriteCounter >= attack_first_frame && spriteCounter <= attack_second_frame) {
            if (spriteCounter == attack_first_frame) {
                soundEffects.setFile("sword-sound.wav");
                soundEffects.play(0.1f);
            }
            changeHitbox();
            spriteNum = 1;
        }
        else {
            spriteNum = 0;
            spriteCounter = 0;
            attacking = false;
            attackArea = null;
        }
    }

    void checkRoomTransition () {
        Rectangle bounds = solidArea.getBounds();
        if (x + bounds.x + bounds.width > GamePanel.screenWidth) {
            TileManager.roomX = TileManager.roomX + 1;
            x = 0;           
        }
        if (x + bounds.x < 0) {
            TileManager.roomX = TileManager.roomX - 1;
            x = GamePanel.screenWidth - GamePanel.tileSize;           
        }
        if (y + bounds.y + bounds.height > GamePanel.screenHeight) {
            TileManager.roomY = TileManager.roomY + 1;
            y = 0;           
        }
        if (y + bounds.y < 0) {
            TileManager.roomY = TileManager.roomY - 1;
            y = GamePanel.screenHeight - GamePanel.tileSize;           
        }
    }
    public void setDefaultValues () {
        maxHealth = 100;
        currentHealth = maxHealth;
        x = 100;
        y = 100;
        speed = 4;
        dmg = 50;
        direction = "down";
    }
    @Override
    void playDamageSound() {
        soundEffects.setFile("player-hit.wav");
        soundEffects.play(0.1f);
    }
    @Override
    public void draw (Graphics2D g) {
      BufferedImage image = null;
      int height = GamePanel.tileSize; int width = GamePanel.tileSize;
      int x = this.x; int y = this.y;
      switch(direction){
        case "up":
            if (attacking == true) { image = at_up.get(spriteNum); height = height * 2; y = y - GamePanel.tileSize; }
            else image = up.get(spriteNum);
            break;
        case "down":
            if (attacking == true) { image = at_down.get(spriteNum); height = height * 2; }
            else image = down.get(spriteNum);
            break;
        case "left":
            if (attacking == true) { image = at_left.get(spriteNum); width = width * 2; x = x - GamePanel.tileSize; }
            else image = left.get(spriteNum);
            break;
        case "right":
            if (attacking == true) { image = at_right.get(spriteNum); width = width * 2; }
            else image = right.get(spriteNum);
            break;
      }
      g.drawImage(image, x, y, width, height, null);

      /*// debug
      Rectangle bounds = solidArea.getBounds();
      g.setColor(Color.BLACK);
      g.drawRect(this.x + bounds.x, this.y + bounds.y, bounds.width, bounds.height);
      if (attackArea != null) {
            Rectangle bounds2 = attackArea.getBounds();
            g.setColor(Color.RED);
            g.drawRect(this.x + bounds2.x, this.y + bounds2.y, bounds2.width, bounds2.height);
      }*/
    }

    public void getPlayerImage() {
        try{
            // Can split into several loops if animation length is different for any of the directions/actions
            for (int i = 1; i <= 2; i++) {
                up.add(ImageIO.read(getClass().getResourceAsStream(path + "ax_up" + i + ".png")));
                down.add(ImageIO.read(getClass().getResourceAsStream(path + "ax_down" + i + ".png")));
                left.add(ImageIO.read(getClass().getResourceAsStream(path + "ax_left" + i + ".png")));
                right.add(ImageIO.read(getClass().getResourceAsStream(path + "ax_right" + i + ".png")));
                at_up.add(ImageIO.read(getClass().getResourceAsStream(path + "ax_at_up" + i + ".png")));
                at_down.add(ImageIO.read(getClass().getResourceAsStream(path + "ax_at_down" + i + ".png")));
                at_left.add(ImageIO.read(getClass().getResourceAsStream(path + "ax_at_left" + i + ".png")));
                at_right.add(ImageIO.read(getClass().getResourceAsStream(path + "ax_at_right" + i + ".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void interactNPC(NPC interactionN){
        CollisionChecker.findInteraction(this, TileManager.currentRoom);
        if(interactionN != null){
            gp.gameState = State.DIALOGUE;
        }
    }


}
