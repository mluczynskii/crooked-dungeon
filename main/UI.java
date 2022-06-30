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

    static String iconPath = "/graphic_assets/sprites/icons/";
    static String titlePath = "/graphic_assets/single objects/startscreen.png";

    // Fonts
    static Font infoFont = new Font("haxorville Nerd Font", Font.PLAIN, 25);
    static Font pauseFont = new Font("haxorville Nerd Font", Font.PLAIN, 60);
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
    BufferedImage w, a, s, d, z, x, p;

    BufferedImage titleScreen;

    Rectangle hpContainer = new Rectangle(0, 0, GamePanel.screenWidth/3, 30);

    public UI (GamePanel gp) {
        this.gp = gp;
        try {
            this.dmgIcon = ImageIO.read(getClass().getResourceAsStream(iconPath + "dmg.png"));
            this.speedIcon = ImageIO.read(getClass().getResourceAsStream(iconPath + "speed.png"));
            this.coinIcon = ImageIO.read(getClass().getResourceAsStream(iconPath + "coin.png"));
            this.w= ImageIO.read(getClass().getResourceAsStream(iconPath + "w-key.png"));
            this.a = ImageIO.read(getClass().getResourceAsStream(iconPath + "a-key.png"));
            this.s = ImageIO.read(getClass().getResourceAsStream(iconPath + "s-key.png")); 
            this.d = ImageIO.read(getClass().getResourceAsStream(iconPath + "d-key.png"));
            this.z = ImageIO.read(getClass().getResourceAsStream(iconPath + "z-key.png"));
            this.x = ImageIO.read(getClass().getResourceAsStream(iconPath + "x-key.png"));
            this.p = ImageIO.read(getClass().getResourceAsStream(iconPath + "p-key.png"));
            this.titleScreen = ImageIO.read(getClass().getResourceAsStream(titlePath));
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
            case GAMEOVER:
                drawGameOver(g);
                break;
        }
    }
    

    void drawTitleScreen(Graphics2D g){
        g.drawImage(titleScreen, 0, 0, GamePanel.screenWidth, GamePanel.screenHeight, null);
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
    void drawGameOver (Graphics2D g) {
        String text = "Game Over";
        Rectangle container = new Rectangle (0, 0, GamePanel.screenWidth, GamePanel.screenHeight);
        drawCenteredText(container, text, g, textFont);
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
        drawCenteredText(Info, Integer.toString((int)gp.player.dmg), g, infoFont);

        g.drawImage(speedIcon, 2 * iconSize, 30, iconSize, iconSize, null);
        Info.x = 3 * iconSize;
        drawCenteredText(Info, Integer.toString(gp.player.speed), g, infoFont);

        g.drawImage(coinIcon, 4 * iconSize, 30, iconSize, iconSize, null);
        Info.x = 5 * iconSize;
        drawCenteredText(Info, Integer.toString(gp.player.money), g, infoFont);

        // Draw only on first screen
        if (TileManager.roomX == 0 && TileManager.roomY == 0) {
            Info.y = 60 + iconSize;
            Info.x = 10;
            Info.width = Info.width * 3;
            BufferedImage[] help = {w, a, s, d, z, x, p};
            String[] text = {"up", "left", "down", "right", "action", "attack", "pause"};
            for (int i = 0; i < help.length; i++) {
                g.drawImage(help[i], Info.x, Info.y, iconSize, iconSize, null);
                Info.x = Info.x + iconSize;
                drawCenteredText(Info, text[i], g, infoFont);
                Info.x = 10;
                Info.y = Info.y + iconSize + 10;
            }
        }
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

        String msg = (int)gp.player.currentHealth + "/" + (int)gp.player.maxHealth;
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

        for (String line : currentDialogue.split("\n")) {
            drawDialogueText(g,line,x,y,width,height,defaultStroke);
            y = y + 40;
        }   
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
