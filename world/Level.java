package world;

import java.util.Random;

public class Level {
    static String layoutsPath = "/graphic_assets/layouts/level1/";

    public Room[][] roomGrid;
    int roomWidth, roomHeight;
    
    Random rand = new Random();

    public Level() {
        roomWidth = rand.nextInt(10) + 2;
        roomHeight = rand.nextInt(10) + 2;
        roomGrid = new Room[roomHeight][roomWidth];
        generateRooms();
    }
    private void generateRooms () {
        System.out.println("Generating " + Integer.toString(roomHeight) + "x" + Integer.toString(roomWidth));
        roomGrid[0][0] = new Room(layoutsPath + "topLeft" + Integer.toString(rand.nextInt(3)) + ".txt");
        for (int i = 1; i < roomWidth-1; i++)
            roomGrid[0][i] = new Room(layoutsPath + "top" + Integer.toString(rand.nextInt(3)) + ".txt");

        roomGrid[0][roomWidth-1] = new Room(layoutsPath + "topRight" + Integer.toString(rand.nextInt(3)) + ".txt");
        for (int i = 1; i < roomHeight-1; i++)
            roomGrid[i][roomWidth-1] = new Room(layoutsPath + "right" + Integer.toString(rand.nextInt(3)) + ".txt");

        roomGrid[roomHeight-1][roomWidth-1] = new Room(layoutsPath + "botRight" + Integer.toString(rand.nextInt(3)) + ".txt");
        for (int i = roomWidth-2; i > 0; i--)
            roomGrid[roomHeight-1][i] = new Room(layoutsPath + "bot" + Integer.toString(rand.nextInt(3)) + ".txt");

        roomGrid[roomHeight-1][0] = new Room(layoutsPath + "botLeft" + Integer.toString(rand.nextInt(3)) + ".txt");
        for (int i = roomHeight-2; i > 0; i--)
            roomGrid[i][0] = new Room(layoutsPath + "left" + Integer.toString(rand.nextInt(3)) + ".txt");

        for (int i = 1; i < roomHeight-1; i++) {
            for (int j = 1; j < roomWidth-1; j++) {
                roomGrid[i][j] = new Room(layoutsPath + "center" + Integer.toString(rand.nextInt(3)) + ".txt");
            }
        }
    }
}
