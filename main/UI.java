package main;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.*;

public class UI {
    GamePanel gp;
    Font font;
    static String path = "/graphic_assets/sprites/icons/";
    BufferedImage dmg, speed;

    public UI (GamePanel gp) {
        this.gp = gp;
        this.font = new Font("Impact", Font.PLAIN, 20);
        try {
            this.dmg = ImageIO.read(getClass().getResourceAsStream(path + "dmg.png"));
            this.speed = ImageIO.read(getClass().getResourceAsStream(path + "speed.png"));
        } catch (Exception e) {
            System.out.println("Missing sprites");
        }
    }
    public void drawUI (Graphics2D g) {
        g.setFont(font);
        g.setColor(Color.RED);
        g.fillRect(0, 0, (int)(gp.player.currentHealth/gp.player.maxHealth * GamePanel.screenWidth/3), 25);
        g.setColor(Color.WHITE);
        g.drawString("HP: " + (int)gp.player.currentHealth + "/" + (int)gp.player.maxHealth, 0, 20);
        
        g.setColor(Color.BLACK);
        // DMG meter
        g.drawImage(dmg, 0, 26, 2*GamePanel.tileSize/3, 2*GamePanel.tileSize/3, null);
        g.drawString(Double.toString(gp.player.dmg), 2*GamePanel.tileSize/3 + 2, 60);

        // Speed meter
        g.drawImage(speed, 85, 28, 2*GamePanel.tileSize/3, 2*GamePanel.tileSize/3, null);
        g.drawString(Integer.toString(gp.player.speed), 2 * GamePanel.tileSize/2 + 80 + 2, 60);

        // TODO: Come up with some smart grid-type icon/text placing
    }
}
