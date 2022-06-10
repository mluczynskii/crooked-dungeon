package entity;

import main.GamePanel;
import main.KeyController;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.geom.Area;

public class Player extends Entity {
    GamePanel gp;
    KeyController keyC;
    int idleCounter = 0;

    public Player (GamePanel gp, KeyController keyC) {
        this.gp = gp;
        this.keyC = keyC;
        
        setDefaultValues();
        getPlayerImage();

        solidArea = new Area(new Rectangle(12 * GamePanel.scale, 16 * GamePanel.scale, 9 * GamePanel.scale, 9 * GamePanel.scale));
    }
    public void update () {

        if(keyC.up == true || keyC.down == true || keyC.right == true || keyC.left == true){
            
            if (this.keyC.up == true){ direction = "up"; }
            else if (keyC.down == true){ direction = "down"; }
            else if (keyC.right == true){ direction = "right"; }
            else if (keyC.left == true){ direction = "left"; }

            collisionOn = false;
            gp.checker.checkTiles(this);

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
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if (spriteNum == 2){
                    spriteNum=1;
                }
                spriteCounter=0;
            }

        }
        else{
            idleCounter++;
            if(idleCounter == 20){
                spriteNum=2;
                idleCounter = 0;
            }
        }

        checkRoomTransition();
    }
    void checkRoomTransition () {
        Rectangle bounds = solidArea.getBounds();
        if (x + bounds.x + bounds.width > GamePanel.screenWidth) {
            gp.tM.roomX = gp.tM.roomX + 1;
            x = 0;           
        }
        if (x + bounds.x < 0) {
            gp.tM.roomX = gp.tM.roomX - 1;
            x = GamePanel.screenWidth - GamePanel.tileSize;           
        }
        if (y + bounds.y + bounds.height > GamePanel.screenHeight) {
            gp.tM.roomY = gp.tM.roomY + 1;
            y = 0;           
        }
        if (y + bounds.y < 0) {
            gp.tM.roomY = gp.tM.roomY - 1;
            y = GamePanel.screenHeight - GamePanel.tileSize;           
        }
    }
    public void setDefaultValues () {
        maxHealth = 100;
        currentHealth = maxHealth;
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }
    public void draw (Graphics2D g) {
      BufferedImage image = null;
      switch(direction){
        case "up":
            if(spriteNum == 1){
                image = up1;
            }
            if(spriteNum == 2){
                image = up2;
            }
            break;
        case "down":
            if(spriteNum == 1){
                image = down1;
            }
            if(spriteNum == 2){
                 image = down2;
            }
            break;
        case "left":
            if(spriteNum == 1){
                image = left1;
            }
            if(spriteNum == 2){
                image = left2;
            }
            break;
        case "right":
            if(spriteNum == 1){
                image = right1;
            }
            if(spriteNum == 2){
                image = right2;
            }
            break;
      }
      g.drawImage(image, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_right2.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
