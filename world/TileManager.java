package world;

import java.io.*;
import javax.imageio.ImageIO;
import main.GamePanel;
import java.awt.*;

public class TileManager {
    public Tile[] tiles;

    public Level currentLevel;
    public int roomX, roomY;
    public Room currentRoom;

    static String tilePath = "/graphic_assets/tiles/";

    // use '_' prefix for "tall" tiles
    static String[] tileNames = {"grass.png", "stone_path1.png", "stone_path2.png", // [0] [1] [2]
                                 "stone1.png", "stone2.png", "stone3.png", // [3] [4] [5]
                                 "stone4.png", "stone5.png", "stone_path3.png", // [6] [7] [8]
                                 "_tree_upperleft.png", "_tree_upperright.png", "tree_botleft.png", // [9] [10] [11]
                                 "tree_botright.png", "middle_fence1.png", "water.png", // [12] [13] [14]
                                 "water_right.png", "water_botright.png", "water_bot.png"}; // [15] [16] [17]

    private void initSolidBounds () {
        tiles[11].solidArea = new Rectangle(24 * GamePanel.scale, 16 * GamePanel.scale, 8 * GamePanel.scale, 16 * GamePanel.scale); // "_tree_botleft.png"
        tiles[12].solidArea = new Rectangle(0, 16 * GamePanel.scale, 9 * GamePanel.scale, 16 * GamePanel.scale); // "_tree_botright.png"
        tiles[13].solidArea = new Rectangle(0, 5 * GamePanel.scale, GamePanel.tileSize, 26 * GamePanel.scale); // "_middle_fence1.png"
        tiles[14].solidArea = new Rectangle(0, 0, GamePanel.tileSize, GamePanel.tileSize); // "_water.png"
        tiles[15].solidArea = new Rectangle(0, 0, 24 * GamePanel.scale, GamePanel.tileSize); // "_water_right.png"
        tiles[16].solidArea = new Rectangle(0, 0, 24 * GamePanel.scale, 25 * GamePanel.scale); // "_water_botright.png"
        tiles[17].solidArea = new Rectangle(0, 0, GamePanel.tileSize, 25 * GamePanel.scale); // "_water_bot.png"
    }

    public TileManager () {
        this.tiles = new Tile[tileNames.length];
        getTileImages();
        initSolidBounds();
        currentLevel = new Level();
        roomX = 0; roomY = 0;
        currentRoom = currentLevel.roomGrid[0][0];
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
    public void drawRoom (Graphics2D g) {
        currentRoom = currentLevel.roomGrid[roomY][roomX];
        System.out.println(currentRoom.name);
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (row < GamePanel.rowNum) {
            while (col < GamePanel.colNum) {
                int tileNum = currentRoom.roomTileNum[row][col];
                g.drawImage(tiles[tileNum].image, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
                col++;
                x = x + GamePanel.tileSize;
            }
            col = 0;
            row++;
            x = 0;
            y = y + GamePanel.tileSize;
        }
    }
}
