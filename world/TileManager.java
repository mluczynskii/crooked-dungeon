package world;

import java.io.*;

import javax.imageio.ImageIO;
import main.GamePanel;
import java.awt.*;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles;
    public int[][] mapTileNum;

    static String tilePath = "/graphic_assets/tiles/";

    // use '_' prefix for solid tiles
    static String[] tileNames = {"grass.png", "stone_path1.png", "stone_path2.png", // [0] [1] [2]
                                 "stone1.png", "stone2.png", "stone3.png", // [3] [4] [5]
                                 "stone4.png", "stone5.png", "stone_path3.png", // [6] [7] [8]
                                 "tree_upperleft.png", "tree_upperright.png", "_tree_botleft.png", // [9] [10] [11]
                                 "_tree_botright.png", "_middle_fence1.png", "_water.png", // [12] [13] [14]
                                 "_water_right.png", "_water_botright.png", "_water_bot.png"}; // [15] [16] [17]

    private void initSolidBounds () {
        tiles[11].solidArea = new Rectangle(24 * gp.scale, 16 * gp.scale, 8 * gp.scale, 16 * gp.scale); // "_tree_botleft.png"
        tiles[12].solidArea = new Rectangle(0, 16 * gp.scale, 9 * gp.scale, 16 * gp.scale); // "_tree_botright.png"
        tiles[13].solidArea = new Rectangle(0, 5 * gp.scale, gp.tileSize, 26 * gp.scale); // "_middle_fence1.png"
        tiles[14].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize); // "_water.png"
        tiles[15].solidArea = new Rectangle(0, 0, 24 * gp.scale, gp.tileSize); // "_water_right.png"
        tiles[16].solidArea = new Rectangle(0, 0, 24 * gp.scale, 25 * gp.scale); // "_water_botright.png"
        tiles[17].solidArea = new Rectangle(0, 0, gp.tileSize, 25 * gp.scale); // "_water_bot.png"
    }

    public TileManager (GamePanel gp) {
        this.gp = gp;
        this.tiles = new Tile[tileNames.length];
        this.mapTileNum = new int[gp.rowNum][gp.colNum];
        getTileImages();
        initSolidBounds();
        loadMap("/graphic_assets/layouts/map1.txt");
    }
    void getTileImages() {
        try {
            int i = 0;
            while (i < tileNames.length) { 
                tiles[i] = new Tile();
                tiles[i].image = ImageIO.read(getClass().getResourceAsStream(tilePath + tileNames[i]));
                i++;
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
