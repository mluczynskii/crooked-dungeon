package world;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import main.GamePanel;

public class Room {
    public int[][] roomTileNum;
    public String name;
    public Room (String filepath) {
        name = filepath;
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
}
