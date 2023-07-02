package cz.cvut.fel.pjv.mashkvla.ui;

import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.LOGGER;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.URMButtons.*;

/**
 * Represents a URM (Unpause, Replay, and Menu) button used in the game UI.
 */
public class UrmButton extends PauseButton{
    private BufferedImage[] images;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    /**
     * Initializes the URM button with the specified position, dimensions, and row index.
     *
     * @param x        The x-coordinate of the button.
     * @param y        The y-coordinate of the button.
     * @param width    The width of the button.
     * @param height   The height of the button.
     * @param rowIndex The row index of the button's image.
     */
    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = ImageManager.s_getSpriteAtlas(ImageManager.URM_BUTTONS);
        this.images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++) {
            this.images[i] = temp.getSubimage(i * URM_B_SIZE_DEFAULT, this.rowIndex * URM_B_SIZE_DEFAULT, URM_B_SIZE_DEFAULT, URM_B_SIZE_DEFAULT);
        }
    }

    /**
     * Updates the state of the URM button based on user interactions.
     */
    public void update(){
        this.index = 0;
        if(mouseOver)
            this.index = 1;
        if(mousePressed)
            this.index = 2;
        LOGGER.fine("URM Button updated");
    }

    /**
     * Draws the URM button on the screen.
     *
     * @param g The graphics context.
     */
    public void draw(Graphics g){
        g.drawImage(this.images[index], x, y ,URM_B_SIZE, URM_B_SIZE, null);
    }

    /**
     * Resets the mouse over and mouse pressed states of the URM button.
     */
    public void resetBooleans(){
        this.mousePressed = false;
        this.mouseOver = false;
    }

    /**
     * Checks if the mouse is currently over the URM button.
     *
     * @return True if the mouse is over the URM button, false otherwise.
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * Sets the state of mouse over for the URM button.
     *
     * @param mouseOver The state of mouse over to set.
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * Checks if the URM button is currently being pressed.
     *
     * @return True if the URM button is being pressed, false otherwise.
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Sets the state of mouse pressed for the URM button.
     *
     * @param mousePressed The state of mouse pressed to set.
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
