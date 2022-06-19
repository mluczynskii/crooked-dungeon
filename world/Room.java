package world;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import entity.NPC;
import entity.Entity;
import main.GamePanel;
import java.awt.Graphics2D;
import entity.*;

public class Room {
    public int[][] roomTileNum;


    GamePanel gp;
    public ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<NPC> npcList = new ArrayList<>();
    public ArrayList<Monster> monsterList = new ArrayList<>();

    static String[] enemyNames = {"entity.Slime"};
    static int enemyCap = 3;
    Random rand = new Random();

    public Room (String filepath, GamePanel gp) {
        this.gp = gp;
        entityList.add(gp.player);
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
        int it = rand.nextInt(enemyCap);
        while (it > 0) {
            String name = enemyNames[rand.nextInt(enemyNames.length)];
            generateEnemy(name);
            it--;
        }
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
                Class<?>[] cArg = {Integer.TYPE, Integer.TYPE};
                monster = (Monster) classDef.getDeclaredConstructor(cArg).newInstance(x, y);
                entityList.add(monster);
                monsterList.add(monster);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
