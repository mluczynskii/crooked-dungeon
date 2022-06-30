package world;

import main.Drawable;
import main.GamePanel;
import java.awt.geom.*;
import java.awt.*;
import java.awt.image.*;
import java.util.HashMap;
import java.awt.Point;

import javax.imageio.ImageIO;

public class Prop implements Drawable {
    static String path = "/graphic_assets/props/";
    public static String[] propNames = {"tree", "stone_wall"};
    public class Params {
        int width, height;
        public Area solidArea;
        Params (int width, int height, Rectangle rect) { 
            Area rhs = new Area (rect);
            this.width = width * GamePanel.scale; this.height = height * GamePanel.scale;
            this.solidArea = rhs;
        }
    }
    public int x, y;
    public Params params;
    BufferedImage img;
    static HashMap<String, Params> paramsDict = null;

    void initDimensions () {
        paramsDict = new HashMap<>();
        paramsDict.put("tree", new Params(64, 64, createRect (24, 48, 18, 16)));
        paramsDict.put("stone_wall", new Params(64, 32, createRect (0, 16, 64, 16)));
    }
    static Rectangle createRect (int x, int y, int width, int height) {
        return new Rectangle (x * GamePanel.scale, y * GamePanel.scale, width * GamePanel.scale, height * GamePanel.scale);
    }
    public Prop (String name, int x, int y) {
        if (paramsDict == null)
            initDimensions();
        this.x = x;
        this.y = y;
        loadImage(name);
        this.params = paramsDict.get(name);
    }
    public int height () { 
        Rectangle rect = this.params.solidArea.getBounds();
        Point point = rect.getLocation();
        return point.y + this.y; 
    }
    public void draw (Graphics2D g) {
        g.drawImage(img, x, y, params.width, params.height, null);

        /*// Debug
        g.setColor(Color.RED);
        Rectangle bounds = params.solidArea.getBounds();
        g.drawRect(bounds.x + x, bounds.y + y, bounds.width, bounds.height);*/
    }
    void loadImage (String name) {
        try {
            this.img = ImageIO.read(getClass().getResourceAsStream(path + name + ".png"));
        } catch (Exception e) {
            System.out.println("Couldn't load image: " + name);
        }
    }
    public int compareTo (Drawable e) { 
        if (this.height() > e.height())
            return 1;
        else if (this.height() == e.height())
            return 0;
        return -1;
    }
}
