package main;


import java.awt.*;
import javax.imageio.ImageIO;

import world.TileManager;
import entity.*;
import java.awt.image.*;
import java.awt.font.TextLayout;
import java.awt.geom.*;

public class UI {
    GamePanel gp;

    static String path = "/graphic_assets/sprites/icons/";

    // Fonts
    static Font infoFont = new Font("Impact", Font.PLAIN, 25);
    static Font pauseFont = new Font("Impact", Font.PLAIN, 60);
    static Font textFont = new Font ("haxorville Nerd Font", Font.PLAIN, 30);
    static Font titleFont1 = new Font("haxorville Nerd Font", Font.PLAIN, 35);
    
    //Commands
    public enum Command {
        NEW, LOAD, QUIT;
    }
    public Command currentCommand = Command.NEW;

    // Colors
    static Color textColor = Color.WHITE;
    static Color hpBarColor = Color.RED;
    static Color outlineColor = Color.BLACK;

    // Icons
    static int iconSize = GamePanel.tileSize * 2/3;
    BufferedImage dmgIcon, speedIcon, coinIcon;

    Rectangle hpContainer = new Rectangle(0, 0, GamePanel.screenWidth/3, 30);

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
                drawMonsterHP(g);
                break;
            case PAUSE:
                drawPauseScreen(g);
                break;
            case DIALOGUE:
                drawDialogueScreen(g);
                break;
            case TITLE:
                drawTitleScreen(g);
                break;
        }
    }

    void drawTitleScreen(Graphics2D g){


        String text = "NEW GAME";
        int x = 415;
        int y = 480;
        drawText(text, x, y, g, titleFont1);
        if(currentCommand == Command.NEW){
            drawText(">", x - 30, y, g, titleFont1);
        }

        text = "LOAD GAME";
        y = 540;
        drawText(text, x, y, g, titleFont1);
        if(currentCommand == Command.LOAD){
            drawText(">", x - 30, y, g, titleFont1);
        }

        text = "QUIT";
        y = 600;
        drawText(text, x, y, g, titleFont1);
        if(currentCommand == Command.QUIT){
            drawText(">", x - 30, y, g, titleFont1);
        }

    }
    void drawMonsterHP (Graphics2D g) {
        for (Monster m : TileManager.currentRoom.monsterList) {
            g.setColor(Color.BLACK);
            Rectangle monsterHPContainer = new Rectangle (m.x, m.y - 10, GamePanel.tileSize, 3);
            g.fill(monsterHPContainer);

            double hp = m.currentHealth;
            double maxHp = m.maxHealth;
            double percent = hp/maxHp;
            int width = (int)(percent * monsterHPContainer.width);
            Rectangle hpBar = new Rectangle (m.x, m.y - 10, width, monsterHPContainer.height);

            g.setColor(hpBarColor);
            g.fill(hpBar);
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
        g.setColor(Color.BLACK);
        g.fill(hpContainer);

        // Calculate the width of the HP bar
        double hp = gp.player.currentHealth;
        double maxHp = gp.player.maxHealth;
        double percent = hp/maxHp;
        int width = (int)(percent * hpContainer.width);
        Rectangle hpBar = new Rectangle (0, 0, width, hpContainer.height);

        g.setColor(hpBarColor);
        g.fill(hpBar);

        String msg = "HP: " + gp.player.currentHealth + "/" + gp.player.maxHealth;
        drawCenteredText(hpContainer, msg, g, infoFont);
    }
    void drawCenteredText (Rectangle container, String str, Graphics2D g, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = container.x + (container.width - metrics.stringWidth(str)) / 2;
        int y = container.y + ((container.height - metrics.getHeight()) / 2) + metrics.getAscent();
        drawTextOutline(str, x, y, g, font);
    }
    void drawTextOutline (String str, int x, int y, Graphics2D g, Font font) {
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
    void drawText (String str, int x, int y, Graphics2D g, Font font) {
        TextLayout tl = new TextLayout(str, font, g.getFontRenderContext());
        AffineTransform matrix = new AffineTransform();
        matrix.translate(x, y);
        // Draw Text
        g.setColor(textColor);
        tl.draw(g, x, y);

    }
    public void drawDialogueScreen(Graphics2D g){
        int x = 120;
        int y = 400;
        int width = 784;
        int height = 190;
        Stroke defaultStroke = g.getStroke();
        
        String currentDialogue = gp.player.interactionNPC.speak();
        if(currentDialogue == ""){
            return;
        }
        drawSubWindow(g,x,y,width,height);
        drawDialogueText(g,currentDialogue,x,y,width,height,defaultStroke);

    }

    public void drawSubWindow(Graphics2D g, int x, int y, int width, int height){
        g.setColor(outlineColor);
        g.fillRect(x, y, width, height);

        g.setColor(textColor);
        g.setStroke(new BasicStroke(5));
        g.drawRect(x+5, y+5, width-10, height-10);
    }

    public void drawDialogueText(Graphics2D g, String text, int x, int y, int width, int height, Stroke st){     
        g.setColor(textColor);
        g.setStroke(st);
        drawText(text, x+30, y+50, g, textFont);
    }
}
