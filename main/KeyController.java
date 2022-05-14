package main;

import java.awt.event.*;

public class KeyController implements KeyListener {
    public boolean up, down, right, left;

    @Override
    public void keyTyped(KeyEvent e) { }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
                this.up = true;
                break;
            case KeyEvent.VK_S:
                this.down = true;
                break;
            case KeyEvent.VK_A:
                this.left = true;
                break;
            case KeyEvent.VK_D:
                this.right = true;
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
                this.up = false;
                break;
            case KeyEvent.VK_S:
                this.down = false;
                break;
            case KeyEvent.VK_A:
                this.left = false;
                break;
            case KeyEvent.VK_D:
                this.right = false;
                break;
        }
    }
    
}
