package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;

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
        try {
            GraphicsEnvironment ge = 
            GraphicsEnvironment.getLocalGraphicsEnvironment();
            if (ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/graphic_assets/fonts/haxorville.ttf"))))
                System.out.println("Success");
       } catch (Exception e) {
            System.out.print("Failed to import");
       }
    }
}