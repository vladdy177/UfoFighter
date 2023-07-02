package cz.cvut.fel.pjv.mashkvla.ui;

import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.VolumeButtons.*;

/**
 * Represents a volume button used in the game UI.
 */
public class VolumeButton extends PauseButton {

    private BufferedImage[] images;
    private BufferedImage slider;
    private int index;
    private boolean mouseOver, mousePressed;
    private int buttonX, minVolumeX, maxVolumeX;
    private float floatValue = 0;

    /**
     * Initializes the volume button with the specified position, dimensions, and slider image.
     *
     * @param x      The x-coordinate of the button.
     * @param y      The y-coordinate of the button.
     * @param width  The width of the button.
     * @param height The height of the button.
     */
    public VolumeButton(int x, int y, int width, int height) {
        super(x + width / 2, y, VOLUME_WIDTH, height);
        bounds.x -= VOLUME_WIDTH / 2;
        this.buttonX = x + width / 2;
        this.x = x;
        this.width = width;
        this.minVolumeX = x + VOLUME_WIDTH / 2;
        this.maxVolumeX = x + this.width - VOLUME_WIDTH / 2;
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = ImageManager.s_getSpriteAtlas(ImageManager.VOLUME);
        images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * VOLUME_WIDTH_DEFAULT, 0, VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
        }

        slider = temp.getSubimage(3 * VOLUME_WIDTH_DEFAULT, 0, SLIDER_DEFAULT_WIDTH, VOLUME_HEIGHT_DEFAULT);

    }

    /**
     * Updates the state of the volume button based on user interactions.
     */
    public void update() {
        this.index = 0;
        if (mouseOver)
            this.index = 1;
        if (mousePressed)
            this.index = 2;

    }

    /**
     * Draws the volume button on the screen.
     *
     * @param g The graphics context.
     */
    public void draw(Graphics g) {
        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(this.images[index], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height, null);
    }

    /**
     * Changes the x-coordinate of the volume dragged button and updates the float value accordingly.
     *
     * @param x The new x-coordinate.
     */
    public void changeX(int x){
        if(x < minVolumeX)
            buttonX = minVolumeX;
        else if(x > maxVolumeX)
            buttonX = maxVolumeX;
        else
            buttonX = x;
        updateFloatValue();
        bounds.x = buttonX - VOLUME_WIDTH / 2;
    }

    private void updateFloatValue() {
        float range = maxVolumeX - minVolumeX;
        float value = buttonX - minVolumeX;
        floatValue = value / range;
    }

    /**
     * Resets the mouse over and mouse pressed states of the volume button.
     */
    public void resetBooleans() {
        this.mousePressed = false;
        this.mouseOver = false;
    }

    /**
     * Checks if the mouse is currently over the volume button.
     *
     * @return {@code true} if the mouse is over the button, {@code false} otherwise.
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * Sets the mouse over state of the volume button.
     *
     * @param mouseOver The state of mouse over to set.
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * Checks if the volume button is currently pressed by the mouse.
     *
     * @return {@code true} if the button is pressed, {@code false} otherwise.
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Sets the mouse pressed state of the volume button.
     *
     * @param mousePressed The state of mouse pressed to set.
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    /**
     * Gets the float value representing the current position of the volume button.
     *
     * @return The float value.
     */
    public float getFloatValue() {
        return floatValue;
    }
}
