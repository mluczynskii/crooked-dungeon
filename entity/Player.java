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

        this.setDefaultValues();
        this.getPlayerImage();
    }
    public void update () {

        if(keyC.up == true || keyC.down == true || keyC.right == true || keyC.left == true){
            
        if (this.keyC.up == true){
            this.direction = "up";
            this.y -= this.speed;
        }
        else if (this.keyC.down == true){
            this.direction = "down";
            this.y += this.speed;
        }
        else if (this.keyC.right == true){
            this.direction = "right";
            this.x += this.speed;
        }
        else if (this.keyC.left == true){
            this.direction = "left";
            this.x -= this.speed;
        }
        this.spriteCounter++;
        if(this.spriteCounter>17){
            if(this.spriteNum == 1){
                this.spriteNum = 2;
            }
            else if (spriteNum == 2){
                this.spriteNum=1;
            }
            this.spriteCounter=0;
        }
        }


    }
    public void setDefaultValues () {
        this.x = 100;
        this.y = 100;
        this.speed = 4;
        direction = "down";
    }
    public void draw (Graphics g) {
      //  Graphics2D g2 = (Graphics2D)g;
      //  g2.setColor(Color.white);
      //  g2.fillRect(this.x, this.y, gp.tileSize, gp.tileSize);
      //  g2.dispose();
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
