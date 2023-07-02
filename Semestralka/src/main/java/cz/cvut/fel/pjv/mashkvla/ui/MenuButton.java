package cz.cvut.fel.pjv.mashkvla.ui;

import cz.cvut.fel.pjv.mashkvla.gameStates.GameState;
import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.LOGGER;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.Buttons.*;

/**
 * Class for Initialization buttons in menu
 */
public class MenuButton {
    private int xPos, yPos, rowIndex, index;
    private int xOffsetCenter = B_WIDTH / 2;
    private GameState state;
    private BufferedImage[] images;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;

    /**
     * Constructs a new MenuButton instance.
     *
     * @param xPos     the x-position of the button
     * @param yPos     the y-position of the button
     * @param rowIndex the row index of the button's image in the sprite atlas
     * @param state    the GameState associated with the button
     */
    public MenuButton(int xPos, int yPos, int rowIndex, GameState state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImages();
        initBounds();
    }

    /**
     * Initializes the bounds of the button.
     */
    private void initBounds() {
        this.bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }


    /**
     * Loads the images for the button from the sprite atlas.
     */
    private void loadImages() {
        images = new BufferedImage[3];
        BufferedImage temp = ImageManager.s_getSpriteAtlas(ImageManager.MENU_BUTTONS);
        for(int i = 0; i < images.length; i++)
            images[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
    }

    /**
     * Draws the button on the screen.
     *
     * @param g the Graphics object to draw on
     */
    public void draw(Graphics g){
        g.drawImage(images[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
    }

    /**
     * Updates the button's state.
     */
    public void update(){
        this.index = 0;
        if(this.mouseOver)
            this.index = 1;
        if(this.mousePressed)
            this.index = 2;
    }

    public boolean isMouseOver() {
        return this.mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return this.mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void applyGameState(){
        LOGGER.info("Applying game state: " + state);
        GameState.state = state;
    }

    public void resetBooleans(){
        this.mouseOver = false;
        this.mousePressed = false;
    }

    public GameState getState() {
        return state;
    }

    public GameState gameState() {
        return state;
    }

    public int getIndex() {
        return index;
    }
}
