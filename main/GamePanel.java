package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int ogTileSize = 32; // 32x32 sprites and tiles
    final int scale = 2;

    public final int tileSize = ogTileSize * scale; // 64x64 after scaling
    public final int colNum = 16;
    public final int rowNum = 10;

    public final int screenWidth = colNum * tileSize; // 1024px
    public final int screenHeight = rowNum * tileSize; // 640px

    Thread gameLoop;
    KeyController keyC = new KeyController();

    final int fps = 60;

    //player info
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel () {
        this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyC);
        this.setFocusable(true);
    }
    public void startGameThread () {
        this.gameLoop = new Thread(this);
        this.gameLoop.start();
    }
    @Override
    public void run() {
        double interval = 1e9 / this.fps;
        double nextDrawTime = System.nanoTime() + interval; 
        while (this.gameLoop != null) {
            this.update();
            this.repaint();
            try {
                double remainder = nextDrawTime - System.nanoTime();
                remainder = remainder/1e6;
                if (remainder < 0)
                    remainder = 0;
                Thread.sleep((long)remainder);
                nextDrawTime = nextDrawTime + interval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void update () {
        if (this.keyC.up == true)
            this.playerY -= this.playerSpeed;
        else if (this.keyC.down == true)
            this.playerY += this.playerSpeed;
        else if (this.keyC.right == true)
            this.playerX += playerSpeed;
        else if (this.keyC.left == true)
            this.playerX -= playerSpeed;
    }
    @Override 
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.white);
        g2.fillRect(this.playerX, this.playerY, this.tileSize, this.tileSize);
        g2.dispose();
    }
}
