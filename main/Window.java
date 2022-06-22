package main;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class Window {
    public static void main (String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Crooked Dungeon");
        get_font();

        GamePanel gp = new GamePanel();
        window.add(gp);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true); 
        
        gp.startGameThread();
    }
    static void get_font(){
        // https://stackoverflow.com/questions/16570523/getresourceasstream-returns-null
        // https://stackoverflow.com/questions/16621750/using-custom-fonts-java-io-ioexception-error-reading-font-data
        try {
            InputStream is = Window.class.getResourceAsStream("/graphic_assets/fonts/haxorville.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            if (ge.registerFont(font))
                System.out.println("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}