package world;

import java.io.*;
import javax.imageio.ImageIO;
import main.GamePanel;
import java.awt.*;
import java.awt.geom.Area;

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
                                 "water_right.png", "water_botright.png", "water_bot.png", // [15] [16] [17]
                                 "water_botleft.png", "water_left.png", "water_topright.png", // [18] [19] [20]
                                 "water_topleft.png", "water_top.png", "wall_top1.png", // [21] [22] [23]
                                 "wall_top2.png", "wall_top_left.png", "wall_left.png", // [24] [25] [26]
                                 "wall_right.png", "wall_bot2.png", "wall_bot1.png", // [27] [28] [29]
                                 "wall_bot_left.png", "wall_bot_right.png", "wall_top_right.png", // [30] [31] [32]
                                 "wall_left_up_entry.png", "wall_left_down_entry.png", "wall_right_up_entry.png", // [33] [34] [35]
                                 "wall_right_down_entry.png", "wall_bot_left_entry.png", "wall_bot_right_entry.png", // [36] [37] [38]
                                 "wall_top_left_entry.png", "wall_top_right_entry.png"}; // [39] [40]

    private void initSolidBounds () {
        Area defaultSolid = new Area(new Rectangle(0, 0, GamePanel.tileSize, GamePanel.tileSize));
        tiles[11].solidArea = new Area(new Rectangle(24 * GamePanel.scale, 16 * GamePanel.scale, 8 * GamePanel.scale, 16 * GamePanel.scale)); // "_tree_botleft.png"
        tiles[12].solidArea = new Area(new Rectangle(0, 16 * GamePanel.scale, 9 * GamePanel.scale, 16 * GamePanel.scale)); // "_tree_botright.png"
        tiles[13].solidArea = new Area(new Rectangle(0, 5 * GamePanel.scale, GamePanel.tileSize, 26 * GamePanel.scale)); // "_middle_fence1.png"
        tiles[14].solidArea = new Area(defaultSolid); // "_water.png"
        tiles[15].solidArea = new Area(new Rectangle(0, 0, 24 * GamePanel.scale, GamePanel.tileSize)); // "_water_right.png"
        tiles[16].solidArea = new Area(new Rectangle(0, 0, 24 * GamePanel.scale, 25 * GamePanel.scale)); // "_water_botright.png"
        tiles[17].solidArea = new Area(new Rectangle(0, 0, GamePanel.tileSize, 25 * GamePanel.scale)); // "_water_bot.png"
        tiles[18].solidArea = new Area(new Rectangle(8 * GamePanel.scale, 0, 24 * GamePanel.scale, 25 * GamePanel.scale)); // "water_botleft.png"
        tiles[19].solidArea = new Area(new Rectangle(8 * GamePanel.scale, 0, 24 * GamePanel.scale, GamePanel.tileSize)); // "water_left.png"
        tiles[20].solidArea = new Area(new Rectangle(0, 7 * GamePanel.scale, 24 * GamePanel.scale, 25 * GamePanel.scale)); // "water_topright.png"
        tiles[21].solidArea = new Area(new Rectangle(8 * GamePanel.scale, 7 * GamePanel.scale, 24 * GamePanel.scale, 25 * GamePanel.scale)); // "water_topleft.png"
        tiles[22].solidArea = new Area(new Rectangle(0, 7 * GamePanel.scale, GamePanel.tileSize, 25 * GamePanel.scale)); // "water_top.png"
        tiles[23].solidArea = new Area(defaultSolid); // "wall_top1.png"
        tiles[24].solidArea = new Area(defaultSolid); // "wall_top2.png"

        tiles[25].solidArea = new Area(new Rectangle(0, 0, GamePanel.tileSize, GamePanel.tileSize-2));
        tiles[25].solidArea.add(new Area(new Rectangle(0, GamePanel.tileSize-2, GamePanel.tileSize-4, 2))); // "wall_top_left.png"

        tiles[26].solidArea = new Area(new Rectangle(0, 0, GamePanel.tileSize-4, GamePanel.tileSize)); // "wall_left.png"
        tiles[27].solidArea = new Area(new Rectangle(4, 0, GamePanel.tileSize-4, GamePanel.tileSize)); // "wall_right.png"
        tiles[28].solidArea = new Area(defaultSolid); // "wall_bot2.png"
        tiles[29].solidArea = new Area(defaultSolid); // "wall_bot1.png"

        tiles[30].solidArea = new Area(new Rectangle(0, 0, GamePanel.tileSize-4, GamePanel.tileSize));
        tiles[30].solidArea.add(new Area(new Rectangle(GamePanel.tileSize-4, 2, 4, GamePanel.tileSize-2))); // "wall_bot_left.png"
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
        //System.out.println(Integer.toString(roomY) + " " + Integer.toString(roomX));
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
