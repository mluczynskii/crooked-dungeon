package world;

import java.io.*;
import javax.imageio.ImageIO;
import main.GamePanel;
import java.awt.*;

public class TileManager {
    public static Tile[] tiles;

    public static Level currentLevel;
    public static int roomX, roomY;
    public static Room currentRoom;
    GamePanel gp;

    static String tilePath = "/graphic_assets/tiles/";

    static String[] tileNames = {// Stone wall tiles
                                 "wall_left_up_entry.png", "wall_left.png", "wall_top_left.png", // [0] [1] [2]
                                 "wall_top1.png", "wall_top2.png", "wall_top_left_entry.png", // [3] [4] [5]
                                 "wall_top_right_entry.png", "wall_top_right.png", "wall_right.png", // [6] [7] [8]
                                 "wall_right_up_entry.png", "wall_right_down_entry.png", "wall_bot_right.png", // [9] [10] [11]
                                 "wall_bot1.png", "wall_bot2.png", "wall_bot_right_entry.png", // [12] [13] [14]
                                 "wall_bot_left_entry.png", "wall_bot_left.png", "wall_left_down_entry.png", // [15] [16] [17]
                                 "grass.png", // [18]
                                 
                                 // Floor tiles
                                 "stone_path1.png", "stone_path2.png", "stone_path3.png", // [19] [20] [21]
                                 
                                 // Water tiles
                                 "water_bot.png", "water_botright.png", "water_right.png", "water_topright.png", // [22] [23] [24] [25]
                                 "water_top.png", "water_topleft.png", "water_left.png", "water_botleft.png", // [26] [27] [28] [29]
                                 "water.png"}; // [30] 

    public TileManager (GamePanel gp) {
        tiles = new Tile[tileNames.length];
        this.gp = gp;
        getTileImages();
        initSolidBounds();
        currentLevel = new Level(gp);
        roomX = 0; roomY = 0;
        currentRoom = currentLevel.roomGrid[0][0];
    }
    void getTileImages() {
        try {
            int i = 0;
            while (i < tileNames.length) { 
                tiles[i] = new Tile();
                try {
                    tiles[i].image = ImageIO.read(getClass().getResourceAsStream(tilePath + tileNames[i]));
                } catch (Exception e) {
                    System.out.println("Cannot read: " + tileNames[i]);
                    throw e;
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void drawRoom (Graphics2D g) {
        currentRoom = currentLevel.roomGrid[roomY][roomX];
        currentRoom.drawRoom(g);
    }
    
    private void initSolidBounds () {
        int full = GamePanel.ogTileSize;

        // Water tiles
        tiles[30].addSolidArea(0, 0, full, full);
        tiles[29].addSolidArea(8, 0, full - 8, full - 6);
        tiles[28].addSolidArea(8, 0, full - 8, full);
        tiles[27].addSolidArea(8, 6, full - 8, full - 6);
        tiles[26].addSolidArea(0, 6, full, full - 6);
        tiles[25].addSolidArea(0, 6, full - 8, full - 6);
        tiles[24].addSolidArea(0, 0, full - 8, full);
        tiles[23].addSolidArea(0, 0, full - 8, full-6);
        tiles[22].addSolidArea(0, 0, full, full - 6);

        // Stone wall tiles
        tiles[0].addSolidArea(0, 0, full - 5, full - 2);
        tiles[1].addSolidArea(0, 0, full - 5, full);

        tiles[2].addSolidArea(0, 0, full, full - 2);
        tiles[2].addSolidArea(0, 0, full - 5, full);

        tiles[3].addSolidArea(0, 0, full, full - 2);
        tiles[4].addSolidArea(0, 0, full, full - 2);
        tiles[5].addSolidArea(0, 0, full - 2, full - 2);
        tiles[6].addSolidArea(2, 0, full - 2, full - 2);

        tiles[7].addSolidArea(0, 0, full, full - 2);
        tiles[7].addSolidArea(5, 0, full - 5, full);

        tiles[8].addSolidArea(5, 0, full - 5, full);
        tiles[9].addSolidArea(5, 0, full - 5, full - 2);
        tiles[10].addSolidArea(5, 2, full - 5, full - 2);

        tiles[11].addSolidArea(5, 0, full - 5, full);
        tiles[11].addSolidArea(0, 2, full, full - 2);

        tiles[12].addSolidArea(0, 2, full, full - 2);
        tiles[13].addSolidArea(0, 2, full, full - 2);
        tiles[14].addSolidArea(2, 2, full - 2, full - 2);
        tiles[15].addSolidArea(0, 2, full - 2, full - 2);

        tiles[16].addSolidArea(0, 0, full - 5, full);
        tiles[16].addSolidArea(0, 2, full, full - 2);

        tiles[17].addSolidArea(0, 2, full - 5, full - 2);
    }

}
