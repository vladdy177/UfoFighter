package cz.cvut.fel.pjv.mashkvla.entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.SCALE;

/**
 * The base class for player and enemies in the game.
 */
public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitBox;
    protected int animationTick, animationIndex, state;
    protected int maxAmountOfHearts;
    protected int currentAmountOfHearts;
    protected float flySpeed;
    protected boolean shooting;
    protected long lastShotTime;

    /**
     * Creates a new Entity object with the specified position and dimensions.
     *
     * @param x      the x-coordinate of the entity
     * @param y      the y-coordinate of the entity
     * @param width  the width of the entity
     * @param height the height of the entity
     */
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Draws the hitbox of the entity.
     *
     * @param g             the Graphics object
     * @param xPlayGroundOffset    the x offset of the level
     * @param yPlayGroundOffset    the y offset of the level
     */
    protected void drawHitBox(Graphics g, int xPlayGroundOffset, int yPlayGroundOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x - xPlayGroundOffset, (int) hitBox.y - yPlayGroundOffset, (int) hitBox.width, (int) hitBox.height);
    }

    /**
     * Initializes the hitbox of the entity.
     *
     * @param width  the width of the hitbox
     * @param height the height of the hitbox
     */
    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, (int) (width * SCALE), (int) (height * SCALE));
    }

    /**
     * Returns the hitbox of the entity.
     *
     * @return the hitbox as a Rectangle2D.Float object
     */
    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    /**
     * Returns the state of the entity.
     *
     * @return the state as an integer value
     */
    public int getState() {
        return state;
    }

    /**
     * Returns the animation index of the entity.
     *
     * @return the animation index as an integer value
     */
    public int getAnimationIndex() {
        return animationIndex;
    }
}
