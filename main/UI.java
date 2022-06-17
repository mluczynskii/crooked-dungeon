package main;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.font.TextLayout;
import java.awt.geom.*;

public class UI {
    GamePanel gp;

    static String path = "/graphic_assets/sprites/icons/";
    static Font infoFont = new Font("Impact", Font.PLAIN, 25);
    static Font pauseFont = new Font("Impact", Font.PLAIN, 60);

    // Colors
    static Color textColor = Color.WHITE;
    static Color hpBarColor = Color.RED;
    static Color outlineColor = Color.BLACK;

    // Icons
    static int iconSize = GamePanel.tileSize * 2/3;
    BufferedImage dmgIcon, speedIcon, coinIcon;

    public UI (GamePanel gp) {
        this.gp = gp;
        try {
            this.dmgIcon = ImageIO.read(getClass().getResourceAsStream(path + "dmg.png"));
            this.speedIcon = ImageIO.read(getClass().getResourceAsStream(path + "speed.png"));
            this.coinIcon = ImageIO.read(getClass().getResourceAsStream(path + "coin.png"));
        } catch (Exception e) {
            System.out.println("Missing sprites");
        }
    }
    public void drawUI (Graphics2D g) {
        switch (gp.gameState) {
            case PLAY:
                drawHP(g);
                drawIcons(g);
                break;
            case PAUSE:
                drawPauseScreen(g);
                break;
            case DIALOGUE:
                drawDialogueScreen(g);
        }
    }
    void drawPauseScreen(Graphics2D g) {
        Rectangle s = new Rectangle(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);
        g.draw(s);
        drawCenteredText(s, "PAUSED", g, pauseFont);
    }
    void drawIcons (Graphics2D g) {
        Rectangle Info = new Rectangle(iconSize, 30, iconSize, iconSize);
        g.drawImage(dmgIcon, 0, 30, iconSize, iconSize, null);
        drawCenteredText(Info, Double.toString(gp.player.dmg), g, infoFont);

        g.drawImage(speedIcon, 2 * iconSize, 30, iconSize, iconSize, null);
        Info.x = 3 * iconSize;
        drawCenteredText(Info, Integer.toString(gp.player.speed), g, infoFont);

        g.drawImage(coinIcon, 4 * iconSize, 30, iconSize, iconSize, null);
        Info.x = 5 * iconSize;
        drawCenteredText(Info, Integer.toString(gp.player.money), g, infoFont);
    }
    void drawHP (Graphics2D g) {
        int hp = (int)gp.player.currentHealth, maxHp = (int)gp.player.maxHealth;
        int width = (int)(hp/maxHp * GamePanel.screenWidth/3);
        int height = 30;
        Rectangle hpBar = new Rectangle(0, 0, width, height);

        g.setColor(hpBarColor);
        g.fill(hpBar);

        g.setColor(outlineColor);
        g.draw(hpBar);

        drawCenteredText(hpBar, "HP: " + hp + "/" + maxHp, g, infoFont);
    }
    void drawCenteredText (Rectangle container, String str, Graphics2D g, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = container.x + (container.width - metrics.stringWidth(str)) / 2;
        int y = container.y + ((container.height - metrics.getHeight()) / 2) + metrics.getAscent();
        drawText(str, x, y, g, font);
    }
    void drawText (String str, int x, int y, Graphics2D g, Font font) {
        TextLayout tl = new TextLayout(str, font, g.getFontRenderContext());
        AffineTransform matrix = new AffineTransform();
        matrix.translate(x, y);
        Shape shape = tl.getOutline(matrix);
        // Draw Text
        g.setColor(textColor);
        tl.draw(g, x, y);
        // Draw outline
        g.setColor(outlineColor);
        g.draw(shape);
    }
    public void drawDialogueScreen(Graphics2D g){
        int x = 120;
        int y = 580;
        int width = 784;
        int height = 100;
        
        drawSubWindow(g,x,y,width,height);
    }

    public void drawSubWindow(Graphics2D g, int x, int y, int width, int height){
        g.setColor(outlineColor);
        g.fillRect(x, y, width, height);
    }
}
