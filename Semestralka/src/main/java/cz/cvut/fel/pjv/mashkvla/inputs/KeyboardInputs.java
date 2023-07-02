package cz.cvut.fel.pjv.mashkvla.inputs;

import cz.cvut.fel.pjv.mashkvla.gameStates.GameState;
import cz.cvut.fel.pjv.mashkvla.starter.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Implemented methods to work with keyboard
 */
public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch(GameState.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getOptions().keyPressed(e);
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(GameState.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            default:
                break;
        }
    }
}
