package cz.cvut.fel.pjv.mashkvla.ui;

import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.PauseButtons.*;

/**
 * Represents a sound button used in the pause overlay.
 */
public class SoundButton extends PauseButton{

    private BufferedImage[][] soundButtonImages;
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private int rowIndex, colIndex;

    /**
     * Initializes the SoundButton with the specified position and size.
     *
     * @param x      The x-coordinate of the button.
     * @param y      The y-coordinate of the button.
     * @param width  The width of the button.
     * @param height The height of the button.
     */
    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        
        loadSoundButtonImages();
    }

    private void loadSoundButtonImages() {
        BufferedImage temp = ImageManager.s_getSpriteAtlas(ImageManager.SOUND_BUTTONS);
        soundButtonImages = new BufferedImage[2][3];
        for(int j = 0; j < soundButtonImages.length; j++)
            for(int i = 0; i < soundButtonImages[j].length; i++)
                soundButtonImages[j][i] = temp.getSubimage(i * SOUND_B_SIZE_DEFAULT, j * SOUND_B_SIZE_DEFAULT, SOUND_B_SIZE_DEFAULT, SOUND_B_SIZE_DEFAULT);
    }

    /**
     * Updates the state of the sound button based on user interactions.
     */
    public void update(){
        if(muted)
            rowIndex = 1;
        else
            rowIndex = 0;

        colIndex = 0;
        if(mouseOver)
            colIndex = 1;
        if(mousePressed)
            colIndex = 2;
    }

    /**
     * Resets the mouse interaction booleans.
     */
    public void resetBooleans(){
        mousePressed = false;
        mouseOver = false;
    }

    /**
     * Draws the sound button on the screen.
     *
     * @param g The graphics context.
     */
    public void draw(Graphics g){
        g.drawImage(soundButtonImages[rowIndex][colIndex], x, y, width, height, null);
    }

    /**
     * Checks if the mouse is currently over the sound button.
     *
     * @return True if the mouse is over the sound button, false otherwise.
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * Sets the state of mouse over for the sound button.
     *
     * @param mouseOver The state of mouse over to set.
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * Checks if the sound button is currently being pressed.
     *
     * @return True if the sound button is being pressed, false otherwise.
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Sets the state of mouse pressed for the sound button.
     *
     * @param mousePressed The state of mouse pressed to set.
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    /**
     * Checks if the sound button is muted.
     *
     * @return True if the sound button is muted, false otherwise.
     */
    public boolean isMuted() {
        return muted;
    }

    /**
     * Sets the mute state of the sound button.
     *
     * @param muted The mute state to set.
     */
    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
