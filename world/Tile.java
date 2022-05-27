package world;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.geom.Area;

public class Tile {
    public BufferedImage image;
    public Area solidArea = new Area(new Rectangle(0, 0, 0, 0));
}
