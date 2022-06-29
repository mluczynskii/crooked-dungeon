package main;

import java.awt.event.*;
import main.GamePanel.State;
import main.UI.Command;

public class KeyController implements KeyListener {
    GamePanel gp;
    public boolean up, down, right, left, z, zs, attack;

    public KeyController (GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) { 
        // Nothing
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(gp.gameState == State.TITLE){
            int code = e.getKeyCode();
            Command comm = gp.ui.currentCommand;

            switch (code) {
                case KeyEvent.VK_W:
                    switch (comm){
                        case NEW:
                            gp.ui.currentCommand = Command.QUIT;
                            break;
                        case LOAD:
                            gp.ui.currentCommand = Command.NEW;
                            break;
                        case QUIT:
                            gp.ui.currentCommand = Command.LOAD;
                            break;
                    }
                    break;
                case KeyEvent.VK_S:
                    switch (comm){
                        case NEW:
                            gp.ui.currentCommand = Command.LOAD;
                            break;
                        case LOAD:
                             gp.ui.currentCommand = Command.QUIT;
                             break;
                        case QUIT:
                            gp.ui.currentCommand = Command.NEW;
                            break;
                    }
                    break;

                case KeyEvent.VK_Z:
                    switch (comm){
                        case NEW:
                            gp.gameState = State.PLAY;
                            break;
                        case LOAD:
                             // TODO: Add game loading option
                             break;
                        case QUIT:
                            System.exit(0);
                            break;
                    }
                    break;

            }
        }
        
        else{
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
                z= false;
                break;
            case KeyEvent.VK_X:
                attack = false;
                break;
        }
    }
    
}
