package cz.cvut.fel.pjv.mashkvla.ui;

import cz.cvut.fel.pjv.mashkvla.audio.AudioPlayer;
import cz.cvut.fel.pjv.mashkvla.gameStates.GameState;
import cz.cvut.fel.pjv.mashkvla.gameStates.Playing;
import cz.cvut.fel.pjv.mashkvla.starter.Game;
import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.URMButtons.*;
import static cz.cvut.fel.pjv.mashkvla.utils.ImageManager.s_getFont;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameOverOverlay {

    private Font font;
    private Playing playing;
    private BufferedImage deadScreen;
    private int deadScreenWidth, deadScreenHeight, deadScreenX, deadScreenY;
    private int menuX, replayX, buttonsY;
    private UrmButton menuB, replayB;


    /**
     * Constructs a new GameOverOverlay instance.
     *
     * @param playing the Playing game state instance
     * @throws IOException          if there is an error loading resources
     * @throws FontFormatException if there is an error loading the font
     */
    public GameOverOverlay(Playing playing) throws IOException, FontFormatException {
        this.playing = playing;
        font = s_getFont(ImageManager.FONT);
        createDeadScreen();
        createButtons();
    }

    /**
     * Creates the buttons for the game over overlay.
     */
    private void createButtons() {
        menuX = (Game.GAME_WIDTH / 2 - deadScreenWidth / 2 );
        replayX = (Game.GAME_WIDTH / 2 + deadScreenWidth / 3 );
        buttonsY = (int)(Game.GAME_HEIGHT / 1.2);
        menuB = new UrmButton(menuX, buttonsY, URM_B_SIZE, URM_B_SIZE, 2);
        replayB = new UrmButton(replayX, buttonsY, URM_B_SIZE, URM_B_SIZE, 1);
    }

    /**
     * Creates the dead screen image for the game over overlay.
     */
    private void createDeadScreen() {
    deadScreen = ImageManager.s_getSpriteAtlas(ImageManager.DEAD_BACKGROUND_IMG);
        deadScreenWidth = (int) (deadScreen.getWidth() * Game.SCALE / 2);
        deadScreenHeight = (int)(deadScreen.getHeight() * Game.SCALE / 2);
        deadScreenX = (Game.GAME_WIDTH / 2 - deadScreenWidth / 2);
        deadScreenY = (int)(30 * Game.SCALE);
    }

    /**
     * Draws the game over overlay on the screen.
     *
     * @param g the Graphics object to draw on
     */
    public void draw(Graphics g){
        g.setColor(new Color(0,0,0,150));
        g.fillRect(0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setFont(font.deriveFont(Font.BOLD, 30F));
        g.setColor(Color.WHITE);
        g.drawString("MENU", menuX - menuX / 15, buttonsY - 30);
        g.drawString("REPLAY", replayX - replayX / 15, buttonsY - 30);

        g.drawImage(deadScreen, deadScreenX,deadScreenY, deadScreenWidth, deadScreenHeight, null);

       menuB.draw(g);
       replayB.draw(g);
    }

    /**
     * Updates the game over overlay.
     */
    public void update(){
        menuB.update();
        replayB.update();
    }

    /**
     * Handles the key pressed event.
     *
     * @param e the KeyEvent object
     */
    public void keyPressed(KeyEvent e){
        // No key press handling in this method
    }

    /**
     * Handles the mouse pressed event.
     *
     * @param e the MouseEvent object
     */
    public void mousePressed(MouseEvent e) {
        if(isInButton(e, menuB))
            menuB.setMousePressed(true);
        else if(isInButton(e, replayB))
            replayB.setMousePressed(true);
    }

    /**
     * Handles the mouse released event.
     *
     * @param e the MouseEvent object
     */
    public void mouseReleased(MouseEvent e) {
        if(isInButton(e, menuB)){
            if(menuB.isMousePressed()){
                playing.getGame().saveGame();
                playing.setGameState(GameState.MENU);
                playing.resetAll();
            }
        }
        else if(isInButton(e, replayB)){
            if(replayB.isMousePressed()) {
                playing.getGame().saveGame();
                playing.resetAll();
                playing.getGame().getAudioPlayer().playMusic(AudioPlayer.PLAYING);
            }
        }
        menuB.resetBooleans();
        replayB.resetBooleans();
    }

    /**
     * Handles the mouse moved event.
     *
     * @param e the MouseEvent object
     */
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);

        if(isInButton(e, menuB))
            menuB.setMouseOver(true);
        else if(isInButton(e, replayB))
            replayB.setMouseOver(true);
    }

    /**
     * Checks if the given MouseEvent is within the bounds of the given PauseButton.
     *
     * @param e the MouseEvent object
     * @param b the PauseButton to check
     * @return true if the MouseEvent is within the bounds of the PauseButton, false otherwise
     */
    private boolean isInButton(MouseEvent e, PauseButton b){
        return b.getBounds().contains(e.getX(), e.getY());
    }

}
