package cz.cvut.fel.pjv.mashkvla.ui;

import cz.cvut.fel.pjv.mashkvla.audio.AudioPlayer;
import cz.cvut.fel.pjv.mashkvla.gameStates.GameState;
import cz.cvut.fel.pjv.mashkvla.gameStates.Playing;
import cz.cvut.fel.pjv.mashkvla.starter.Game;
import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.URMButtons.*;

/**
 * Represents the pause overlay displayed during the game's pause state.
 */
public class PauseOverlay {

    private Playing playing;
    private BufferedImage pauseMenu;
    private int pauseMenuX, pauseMenuY, pauseMenuWidth, pauseMenuHeight;
    private UrmButton menuB, replayB, unpauseB;
    private AudioControls audioControls;

    /**
     * Initializes the PauseOverlay with the given Playing state.
     *
     * @param playing The Playing state associated with the overlay.
     */
    public PauseOverlay(Playing playing){
        this.playing = playing;
        loadBackground();
        audioControls = playing.getGame().getAudioControls();
        createUrmButtons();
    }

    private void createUrmButtons() {
        int menuX = (int)(313 * Game.SCALE);
        int replayX = (int)(387 * Game.SCALE);
        int unpauseX = (int)(462 * Game.SCALE);
        int buttonY = (int)(325 * Game.SCALE);

        unpauseB = new UrmButton(unpauseX, buttonY, URM_B_SIZE, URM_B_SIZE, 0);
        replayB = new UrmButton(replayX, buttonY, URM_B_SIZE, URM_B_SIZE, 1);
        menuB = new UrmButton(menuX, buttonY, URM_B_SIZE, URM_B_SIZE, 2);
    }

    private void loadBackground() {
        pauseMenu = ImageManager.s_getSpriteAtlas(ImageManager.PAUSE_MENU);
        pauseMenuWidth = (int)(pauseMenu.getWidth() * Game.SCALE);
        pauseMenuHeight = (int)(pauseMenu.getHeight() * Game.SCALE);
        pauseMenuX = Game.GAME_WIDTH / 2 - pauseMenuWidth / 2;
        pauseMenuY = (int)(25 * Game.SCALE);
    }

    /**
     * Updates the state of the PauseOverlay.
     */
    public void update(){
        menuB.update();
        unpauseB.update();
        replayB.update();

        audioControls.update();
    }

    /**
     * Draws the PauseOverlay on the screen.
     *
     * @param g The graphics context.
     */
    public void draw(Graphics g){
        //Background
        g.drawImage(pauseMenu, pauseMenuX, pauseMenuY, pauseMenuWidth, pauseMenuHeight, null);

        //URM Buttons
        menuB.draw(g);
        unpauseB.draw(g);
        replayB.draw(g);

        audioControls.draw(g);
    }

    /**
     * Handles the mouse dragged event.
     *
     * @param e The mouse event.
     */
    public void mouseDragged(MouseEvent e){
        audioControls.mouseDragged(e);
    }

    /**
     * Handles the mouse pressed event.
     *
     * @param e The mouse event.
     */
    public void mousePressed(MouseEvent e) {
       if(isInButton(e, menuB))
            menuB.setMousePressed(true);
        else if(isInButton(e, replayB))
            replayB.setMousePressed(true);
        else if(isInButton(e, unpauseB))
            unpauseB.setMousePressed(true);
        else
            audioControls.mousePressed(e);
    }

    /**
     * Handles the mouse released event.
     *
     * @param e The mouse event.
     */
    public void mouseReleased(MouseEvent e) {
        if(isInButton(e, menuB)){
            if(menuB.isMousePressed()){
                playing.setGameState(GameState.MENU);
                playing.resetAll();
                playing.unpauseGame();
            }
        }
        else if(isInButton(e, replayB)){
            if(replayB.isMousePressed()) {
                playing.resetAll();
                playing.unpauseGame();
                playing.getGame().getAudioPlayer().playMusic(AudioPlayer.PLAYING);
            }
        }
        else if(isInButton(e, unpauseB)){
            if(unpauseB.isMousePressed()){
                playing.unpauseGame();
            }
        } else
            audioControls.mouseReleased(e);

        menuB.resetBooleans();
        replayB.resetBooleans();
        unpauseB.resetBooleans();
    }

    /**
     * Handles the mouse moved event.
     *
     * @param e The mouse event.
     */
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);

        if(isInButton(e, menuB))
            menuB.setMouseOver(true);
        else if(isInButton(e, replayB))
            replayB.setMouseOver(true);
        else if(isInButton(e, unpauseB))
            unpauseB.setMouseOver(true);
        else
            audioControls.mouseMoved(e);
    }

    private boolean isInButton(MouseEvent e, PauseButton b){
        return b.getBounds().contains(e.getX(), e.getY());
    }

}
