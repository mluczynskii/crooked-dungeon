package world;

import java.io.*;

import javax.imageio.ImageIO;
import main.GamePanel;
import java.awt.*;

public class TileManager {
    GamePanel gp;
    Tile[] tiles;
    int[][] mapTileNum;

    static String tilePath = "/graphic_assets/tiles/";
    static String[] tileNames = {"grass.png", "stone_path1.png", "stone_path2.png", "stone1.png", "stone2.png", 
                                 "stone3.png", "stone4.png", "stone5.png", "water1.png", "water2.png", "water3.png",
                                 "water4.png"};

    public TileManager (GamePanel gp) {
        this.gp = gp;
        this.tiles = new Tile[tileNames.length];
        this.mapTileNum = new int[gp.rowNum][gp.colNum];
        getTileImages();
        loadMap("/graphic_assets/layouts/map1.txt");
    }
    void getTileImages() {
        try {
            for (int i = 0; i < tileNames.length; i++) {
                tiles[i] = new Tile();
                tiles[i].image = ImageIO.read(getClass().getResourceAsStream(tilePath + tileNames[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw (Graphics2D g) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (row < gp.rowNum) {
            while (col < gp.colNum) {
                int tileNum = mapTileNum[row][col];
                g.drawImage(tiles[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
                col++;
                x = x + gp.tileSize;
            }
            col = 0;
            row++;
            x = 0;
            y = y + gp.tileSize;
        }
    }
    public void loadMap (String filepath) {
        try {
            InputStream is = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;

            while (row < gp.rowNum) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                while (col < gp.colNum) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[row][col] = num;
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
}
