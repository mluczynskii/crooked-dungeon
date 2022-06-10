package world;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import entity.Entity;
import main.GamePanel;
import java.awt.Graphics2D;

public class Room {
    public int[][] roomTileNum;
    public ArrayList<Entity> entityList = new ArrayList<>();

    public Room (String filepath) {
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
    }
    void drawRoom (Graphics2D g) {
        for (int row = 0, y = 0; row < GamePanel.rowNum; row++, y += GamePanel.tileSize) {
            for (int col = 0, x = 0; col < GamePanel.colNum; col++, x += GamePanel.tileSize) {
                int tileNum = roomTileNum[row][col];
                g.drawImage(TileManager.tiles[tileNum].image, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
            }
        }
    }
}
