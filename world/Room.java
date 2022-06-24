package world;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Random;
import entity.NPC;
import entity.Entity;
import main.CollisionChecker;
import main.GamePanel;
import java.awt.Graphics2D;
import entity.*;
import pickup.*;

public class Room {
    public int[][] roomTileNum;

    GamePanel gp;
    public ConcurrentLinkedQueue<Entity> entityList = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<NPC> npcList = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<Monster> monsterList = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<Pickup> pickupList = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<Prop> propList = new ConcurrentLinkedQueue<>();
    public Player player;

    static String[] enemyNames = {"entity.Slime"};
    static int enemyCap = 5;
    static int propCap = 4;

    Random rand = new Random();

    public Room (String filepath, GamePanel gp) {
        this.gp = gp;
        player = gp.player;
        entityList.add(player);
        roomTileNum = new int[GamePanel.rowNum][GamePanel.colNum];
        try {
            InputStream is = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
            while (row < GamePanel.rowNum) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                while (col < GamePanel.colNum) {
                    int num = Integer.parseInt(numbers[col]);
                    roomTileNum[row][col] = num;
                    col++;
                }
                col = 0;
                row++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int it = rand.nextInt(enemyCap) + 1;
        while (it > 0) {
            String name = enemyNames[rand.nextInt(enemyNames.length)];
            generateEnemy(name);
            it--;
        }
        it = rand.nextInt(propCap) + 1;
        while (it > 0) {
            initializeProp();
            it--;
        }

    }
    void initializeProp () {
        String name = Prop.propNames[rand.nextInt(Prop.propNames.length)];
        Prop prop = new Prop (name, rand.nextInt(GamePanel.screenWidth), rand.nextInt(GamePanel.screenHeight));
        // Try to find a place where the prop can stand
        int tick = 0;
        int stop = 200;
        while (CollisionChecker.checkSpawn(prop, this) == false && tick < stop) {
            if (prop.y > GamePanel.screenHeight/2) prop.y--;
            else prop.y++;
            if (prop.x > GamePanel.screenWidth/2) prop.x--;
            else prop.x++;

            tick++;
        }
        if (tick == stop) {
            // Don't "spawn"
        }
        else propList.add(prop);
    }
    void drawRoom (Graphics2D g) {
        for (int row = 0, y = 0; row < GamePanel.rowNum; row++, y += GamePanel.tileSize) {
            for (int col = 0, x = 0; col < GamePanel.colNum; col++, x += GamePanel.tileSize) {
                int tileNum = roomTileNum[row][col];
                g.drawImage(TileManager.tiles[tileNum].image, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
            }
        }
    }
    void generateEnemy (String enemyName) {
            Monster monster = null;
            try {
                Class<?> classDef = Class.forName(enemyName);
                int x = Math.max(GamePanel.tileSize + 5, rand.nextInt(GamePanel.screenWidth - (GamePanel.tileSize + 5)));
                int y = Math.max(GamePanel.tileSize + 5, rand.nextInt(GamePanel.screenHeight - (GamePanel.tileSize + 5)));
                Class<?>[] cArg = {Integer.TYPE, Integer.TYPE, Room.class};
                monster = (Monster) classDef.getDeclaredConstructor(cArg).newInstance(x, y, this);

                // Try to find a spot where the monster doesn't get stuck on spawn
                int tick = 0;
                int stop = 200;
                while (CollisionChecker.checkSpawn (monster, this) == false && tick < stop) {
                    if (y > GamePanel.screenHeight/2) monster.y--;
                    else monster.y++;
                    if (x > GamePanel.screenWidth/2) monster.x--;
                    else monster.x++;

                    tick++;
                }
                if (tick == stop) {
                    // Don't "spawn"
                }
                else {
                    entityList.add(monster);
                    monsterList.add(monster);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
