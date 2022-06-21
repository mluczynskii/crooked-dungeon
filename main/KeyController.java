package main;

import java.awt.event.*;
import main.GamePanel.State;

public class KeyController implements KeyListener {
    GamePanel gp;
    public boolean up, down, right, left, z, zs, attack;

    public KeyController (GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) { 
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_P:
                if (gp.gameState == State.PLAY)
                    gp.gameState = State.PAUSE;
                else if (gp.gameState == State.PAUSE)
                    gp.gameState = State.PLAY;
                break;
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_Z:
                z = true;
                zs = false;
                break;
            case KeyEvent.VK_P:
                if (gp.gameState == State.PLAY)
                    gp.gameState = State.PAUSE;
                else if (gp.gameState == State.PAUSE)
                    gp.gameState = State.PLAY;
                break;
            case KeyEvent.VK_X:
                attack = true;
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
                up = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_Z:
                z = false;
                zs= true;
                break;
            case KeyEvent.VK_X:
                attack = false;
                break;
        }
    }
    
}
