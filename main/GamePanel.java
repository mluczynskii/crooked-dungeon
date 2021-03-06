package main;

import javax.swing.*;
import java.awt.*;

import entity.Player;
import pickup.*;
import world.TileManager;
import java.util.PriorityQueue;
import entity.*;
import world.Prop;

public class GamePanel extends JPanel implements Runnable {
    public static final int ogTileSize = 32; // 32x32 sprites and tiles
    public static final int scale = 2;

    public static final int tileSize = ogTileSize * scale; // 64x64 after scaling
    public static final int colNum = 16;
    public static final int rowNum = 10;

    public static final int screenWidth = colNum * tileSize; // 1024px
    public static final int screenHeight = rowNum * tileSize; // 640px

    public enum State {
        PLAY, PAUSE, DIALOGUE, TITLE, GAMEOVER
    }
    public State gameState = State.TITLE;


    Thread gameLoop;
    KeyController keyC = new KeyController(this);
    public Player player = new Player (this, this.keyC);
    TileManager tM = new TileManager(this);
    PriorityQueue<Drawable> q = new PriorityQueue<>();
    public UI ui = new UI(this);

    final int fps = 60;

    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyC);
        this.setFocusable(true);
    }
    public void startGameThread () {
        gameLoop = new Thread(this);
        gameLoop.start();
    }
    @Override
    public void run() {
        double interval = 1e9 / fps;
        double nextDrawTime = System.nanoTime() + interval; 
        while (gameLoop != null) {
            update();
            repaint();
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
        if (gameState == State.DIALOGUE){
            player.dialogue();
            return;
        }
        else if (gameState == State.TITLE){
            return;
        }
        else if (gameState == State.PLAY) {
            player.update();
            for (Monster m : TileManager.currentRoom.monsterList) {
                if (m != null)
                    m.update();
            }
        }
    }
    
    @Override 
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //title state
        if(gameState == State.TITLE){
            ui.drawUI(g2);
            return;
        }


        tM.drawRoom(g2);

        q.add(player);
        for (Entity e : TileManager.currentRoom.entityList)
            q.add(e);
        for (Pickup pickup : TileManager.currentRoom.pickupList)
            q.add(pickup);
        for (Prop prop : TileManager.currentRoom.propList)
            q.add(prop);
        while (!q.isEmpty()) {
            q.poll().draw(g2);
        }
        
        ui.drawUI(g2);
        g2.dispose();
    }
}
