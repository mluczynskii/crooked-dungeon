package world;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.geom.Area;
import main.GamePanel;

public class Tile {
    public BufferedImage image;
    public Area solidArea = new Area();
    public void addSolidArea (int x, int y, int width, int height) {
        Area rhs = new Area (new Rectangle (x * GamePanel.scale, y * GamePanel.scale, width * GamePanel.scale, height * GamePanel.scale));
        solidArea.add(rhs);
    }
}
