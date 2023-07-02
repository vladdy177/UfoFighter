package cz.cvut.fel.pjv.mashkvla.gameStates;

import cz.cvut.fel.pjv.mashkvla.starter.Game;
import cz.cvut.fel.pjv.mashkvla.ui.AudioControls;
import cz.cvut.fel.pjv.mashkvla.ui.PauseButton;
import cz.cvut.fel.pjv.mashkvla.ui.UrmButton;
import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.URMButtons.URM_B_SIZE;

public class Options extends State implements StateMethods {

    private AudioControls audioControls;
    private BufferedImage backgroundImage, optionsMenu;
    private int optionsMenuX, optionsMenuY, optionsMenuWidth, optionsMenuHeight;
    private UrmButton menuButton;

    /**
     * Creates a new Options state.
     *
     * @param game the Game object
     */
    public Options(Game game) {
        super(game);
        loadImages();
        loadButton();
        audioControls = game.getAudioControls();
    }

    private void loadButton() {
        int menuX = (int) (387 * Game.SCALE);
        int buttonY = (int) (325 * Game.SCALE);
        menuButton = new UrmButton(menuX, buttonY, URM_B_SIZE, URM_B_SIZE, 2);
    }

    private void loadImages() {
        backgroundImage = ImageManager.s_getSpriteAtlas(ImageManager.MENU_BACKGROUND);
        optionsMenu = ImageManager.s_getSpriteAtlas(ImageManager.OPTIONS_MENU);

        optionsMenuWidth = (int) (optionsMenu.getWidth() * Game.SCALE);
        optionsMenuHeight = (int) (optionsMenu.getHeight() * Game.SCALE);
        optionsMenuX = Game.GAME_WIDTH / 2 - optionsMenuWidth / 2;
        optionsMenuY = (int) (25 * Game.SCALE);
    }

    @Override
    public void update() {
        menuButton.update();
        audioControls.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(optionsMenu, optionsMenuX, optionsMenuY, optionsMenuWidth, optionsMenuHeight, null);

        menuButton.draw(g);
        audioControls.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        audioControls.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isInButton(e, menuButton)) {
            menuButton.setMousePressed(true);
        } else
            audioControls.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isInButton(e, menuButton)) {
            if (menuButton.isMousePressed()) {
                GameState.state = GameState.MENU;
            }
        } else
            audioControls.mouseReleased(e);

        menuButton.resetBooleans();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);

        if (isInButton(e, menuButton))
            menuButton.setMouseOver(true);
        else
            audioControls.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            GameState.state = GameState.MENU;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private boolean isInButton(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}