package cz.cvut.fel.pjv.mashkvla.objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.SCALE;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.ANIMATION_SPEED;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.ObjectConstants.COIN_SPRITE_AMOUNT;

/**
 * Represents a game object in the game world.
 */
public class GameObject {

    protected int x,y;
    protected Rectangle2D.Float hitBox;
    protected int animationTick, animationIndex;
    protected int objectType;
    protected int drawOffsetX, drawOffsetY;
    protected boolean doAnimation, isActive = true;
    protected int value;

    /**
     * Constructs a new GameObject with the specified coordinates.
     *
     * @param x The x-coordinate of the game object's position.
     * @param y The y-coordinate of the game object's position.
     */
    public GameObject(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Updates the animation tick and advances the animation index if necessary.
     */
    protected void updateAnimationTick(){
        animationTick++;
        if(animationTick >= ANIMATION_SPEED){
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= COIN_SPRITE_AMOUNT)
                animationIndex = 0;
        }
    }

    /**
     * Resets the game object's state.
     */
    public void reset(){
        animationTick = 0;
        animationIndex = 0;
        isActive = true;
    }

    /**
     * Draws the hit box of the game object for debugging purposes.
     *
     * @param g                The Graphics object to draw on.
     * @param xPlayGroundOffset The x-coordinate offset of the game world.
     * @param yPlayGroundOffset The y-coordinate offset of the game world.
     */
    protected void drawHitBox(Graphics g, int xPlayGroundOffset, int yPlayGroundOffset){
        g.setColor(Color.GREEN);
        g.drawRect((int)(hitBox.x - xPlayGroundOffset),
                (int)(hitBox.y - yPlayGroundOffset),
                (int)(hitBox.width),
                (int)(hitBox.height));
    }

    /**
     * Initializes the hit box of the game object with the specified width and height.
     *
     * @param width  The width of the hit box.
     * @param height The height of the hit box.
     */
    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, (int)(width * SCALE), (int)(height * SCALE));
    }

    /**
     * Returns the value of the game object.
     *
     * @return The value of the game object.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the hit box of the game object.
     *
     * @return The hit box of the game object.
     */
    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    /**
     * Returns the type of the game object.
     *
     * @return The type of the game object.
     */
    public int getObjectType() {
        return objectType;
    }

    /**
     * Sets the type of the game object.
     *
     * @param objectType The type of the game object.
     */
    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    /**
     * Returns the draw offset on the x-axis of the game object.
     *
     * @return The draw offset on the x-axis.
     */
    public int getDrawOffsetX() {
        return drawOffsetX;
    }

    /**
     * Returns the draw offset on the y-axis of the game object.
     *
     * @return The draw offset on the y-axis.
     */
    public int getDrawOffsetY() {
        return drawOffsetY;
    }

    /**
     * Returns whether the game object is active.
     *
     * @return true if the game object is active, false otherwise.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the activity state of the game object.
     *
     * @param active The activity state of the game object.
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Returns the animation index of the game object.
     *
     * @return The animation index of the game object.
     */
    public int getAnimationIndex() {
        return animationIndex;
    }
}
