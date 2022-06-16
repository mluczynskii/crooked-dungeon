package main;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.font.TextLayout;
import java.awt.geom.*;

public class UI {
    GamePanel gp;

    static String path = "/graphic_assets/sprites/icons/";
    static Font font = new Font("Impact", Font.PLAIN, 25);

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
        }
    }
    void drawPauseScreen(Graphics2D g) {
        // TODO: Do stuff
    }
    void drawIcons (Graphics2D g) {
        Rectangle Info = new Rectangle(iconSize, 30, iconSize, iconSize);
        g.drawImage(dmgIcon, 0, 30, iconSize, iconSize, null);
        drawCenteredText(Info, Double.toString(gp.player.dmg), g);

        g.drawImage(speedIcon, 2 * iconSize, 30, iconSize, iconSize, null);
        Info.x = 3 * iconSize;
        drawCenteredText(Info, Integer.toString(gp.player.speed), g);

        g.drawImage(coinIcon, 4 * iconSize, 30, iconSize, iconSize, null);
        Info.x = 5 * iconSize;
        drawCenteredText(Info, Integer.toString(gp.player.money), g);
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

        drawCenteredText(hpBar, "HP: " + hp + "/" + maxHp, g);
    }
    void drawCenteredText (Rectangle container, String str, Graphics2D g) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = container.x + (container.width - metrics.stringWidth(str)) / 2;
        int y = container.y + ((container.height - metrics.getHeight()) / 2) + metrics.getAscent();
        drawText(str, x, y, g);
    }
    void drawText (String str, int x, int y, Graphics2D g) {
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
}
