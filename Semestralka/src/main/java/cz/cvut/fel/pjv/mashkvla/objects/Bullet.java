package cz.cvut.fel.pjv.mashkvla.objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static cz.cvut.fel.pjv.mashkvla.utils.Constants.Bullet.*;

/**
 * Represents a bullet in the game.
 */
public class Bullet {

    private Rectangle2D.Float hitBox;
    private int direction;
    private boolean isActive = true;
    private int shooterType;

    /**
     * Constructs a new Bullet object.
     *
     * @param x           The x-coordinate of the bullet's initial position.
     * @param y           The y-coordinate of the bullet's initial position.
     * @param direction   The direction of the bullet (1 for up, -1 for down).
     * @param shooterType The type of the shooter (player, enemy, etc.).
     */
    public Bullet(int x, int y, int direction, int shooterType) {
        this.hitBox = new Rectangle2D.Float(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        this.direction = direction;
        this.shooterType = shooterType;
    }

    /**
     * Draws the hit box of the bullet.
     *
     * @param g           The Graphics object to draw on.
     * @param xPlayGroundOffset  The x-level offset.
     * @param yPlayGroundOffset  The y-level offset.
     */
    public void drawHitBox(Graphics g, int xPlayGroundOffset, int yPlayGroundOffset){
        g.setColor(Color.PINK);
        g.drawRect((int)hitBox.x - xPlayGroundOffset, (int)hitBox.y - yPlayGroundOffset, (int)hitBox.width, (int)hitBox.height);
    }

    /**
     * Updates the position of the bullet based on its direction and speed.
     */
    public void updatePosition(){
        this.hitBox.y += this.direction * BULLET_SPEED;
    }


    /**
     * Resets the bullet, making it inactive.
     */
    public void reset(){
        isActive = false;
    }

    /**
     * Checks if the bullet is active.
     *
     * @return true if the bullet is active, false otherwise.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the activity status of the bullet.
     *
     * @param active The activity status to set.
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Returns the hit box of the bullet.
     *
     * @return The hit box.
     */
    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    /**
     * Returns the type of the shooter.
     *
     * @return The shooter type.
     */
    public int getShooterType() {
        return shooterType;
    }

    /**
     * Sets the position of the bullet.
     *
     * @param x The x-coordinate of the new position.
     * @param y The y-coordinate of the new position.
     */
    public void setPosition(int x, int y){
        this.hitBox.x = x;
        this.hitBox.y = y;
    }
}

