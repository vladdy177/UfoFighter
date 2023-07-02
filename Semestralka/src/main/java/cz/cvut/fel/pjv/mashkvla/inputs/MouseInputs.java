package cz.cvut.fel.pjv.mashkvla.inputs;

import cz.cvut.fel.pjv.mashkvla.gameStates.GameState;
import cz.cvut.fel.pjv.mashkvla.starter.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class MouseInputs implements MouseListener, MouseMotionListener {
    /**
     * Implemented methods to work with mouse
     */
    private GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().mousePressed(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getOptions().mousePressed(e);
                break;
            case SHOP:
                gamePanel.getGame().getShop().mousePressed(e);
                break;
            case QUIT:
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseReleased(e);
                break;
            case MENU:
                try {
                    gamePanel.getGame().getMenu().mouseReleased(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case OPTIONS:
                gamePanel.getGame().getOptions().mouseReleased(e);
                break;
            case SHOP:
                try {
                    gamePanel.getGame().getShop().mouseReleased(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case QUIT:
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //Mouse motion methods

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameState.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseDragged(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getOptions().mouseDragged(e);
                break;
            default:
                break;}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseMoved(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getOptions().mouseMoved(e);
                break;
            case SHOP:
                gamePanel.getGame().getShop().mouseMoved(e);
                break;
            default:
                break;
        }

    }
}
