package entity;


import main.GamePanel;
import main.KeyController;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;


public class Player extends Entity {
    GamePanel gp;
    KeyController keyC;

    public Player (GamePanel gp, KeyController keyC) {
        this.gp = gp;
        this.keyC = keyC;

        setDefaultValues();
        getPlayerImage();
    }
    public void update () {

        if(keyC.up == true || keyC.down == true || keyC.right == true || keyC.left == true){
            
        if (this.keyC.up == true){
            direction = "up";
            y -= speed;
        }
        else if (keyC.down == true){
            direction = "down";
            y += speed;
        }
        else if (keyC.right == true){
            direction = "right";
            x += speed;
        }
        else if (keyC.left == true){
            direction = "left";
            x -= speed;
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
    }
    public void setDefaultValues () {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }
    public void draw (Graphics g) {
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
      g.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_idle1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_idle2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_idle1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_idle2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/graphic_assets/characters/ax/ax_right2.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
