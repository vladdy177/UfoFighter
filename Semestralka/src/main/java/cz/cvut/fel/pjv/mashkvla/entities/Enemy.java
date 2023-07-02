package cz.cvut.fel.pjv.mashkvla.entities;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.LOGGER;
import static cz.cvut.fel.pjv.mashkvla.starter.Game.SCALE;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.ANIMATION_SPEED;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.Directions.*;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.EnemyConstants.*;
import static cz.cvut.fel.pjv.mashkvla.utils.HelpMethods.*;

import java.util.logging.Level;

public abstract class Enemy extends Entity {

    protected int enemyType;
    protected int flyDirection = TURNING;
    protected boolean alive = true;
    protected boolean attackChecked;

    /**
     * Constructs a new Enemy object.
     *
     * @param x         the x-coordinate of the Enemy's position
     * @param y         the y-coordinate of the Enemy's position
     * @param width     the width of the Enemy's sprite
     * @param height    the height of the Enemy's sprite
     * @param enemyType the type of the Enemy
     */
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        this.flySpeed = 0.25f * SCALE;
        initHitBox(width, height);
        this.maxAmountOfHearts = s_getHealth();
        this.currentAmountOfHearts = this.maxAmountOfHearts;
        LOGGER.log(Level.INFO, "Enemy created");
    }

    /**
     * Updates the role of the Enemy based on the playground data and player information.
     *
     * @param playgroundData   the playground data array
     * @param player    the Player object
     * @param enemyType the type of the Enemy
     */
    protected void updateRole(int[][] playgroundData, Player player, int enemyType) {
        switch (enemyType) {
            case ALIEN:
                switch (state) {
                    case IDLE -> newState(TURNING);
                    case TURNING -> {
                        if (animationIndex == 0)
                            attackChecked = false;
                        if (animationIndex == 1 && !attackChecked)
                            checkPlayerHit(player);
                        move(playgroundData);
                    }
                    case BOOM -> {
                        if (animationIndex == 7)
                            flySpeed = 0;
                    }
                }
            case UFO:
                switch (state) {
                    case IDLE -> newState(TURNING);
                    case TURNING -> {
                        if (animationIndex == 0)
                            attackChecked = false;
                        if (animationIndex == 1 && !attackChecked)
                            checkPlayerHit(player);
                        move(playgroundData);
                    }
                    case BOOM -> {
                        flySpeed = 0;
                        if (animationIndex == 9) {
                            setAlive(false);
                        }
                    }
                }
        }
    }

    /**
     * Updates the animation tick of the Enemy.
     */
    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= ANIMATION_SPEED) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= s_getSpriteAmount(enemyType, state))
                animationIndex = 0;
            else if (state == BOOM)
                setAlive(false);
        }
    }

    /**
     * Moves the Enemy based on the playground data.
     *
     * @param playgroundData the level data array
     */
    protected void move(int[][] playgroundData) {
        float speed;
        if (flyDirection == RIGHT)
            speed = -flySpeed;
        else
            speed = flySpeed;
        if (s_canMoveHere(hitBox.x + speed, hitBox.y, hitBox.width, hitBox.height, playgroundData)) {
            hitBox.x += speed;
            return;
        }
        changeFlyDirection();
    }

    /**
     * Changes the fly direction of the Enemy.
     */
    protected void changeFlyDirection() {
        float yChange = hitBox.height;
        if (flyDirection == RIGHT) {
            flyDirection = LEFT;
            hitBox.y += yChange;
        } else {
            flyDirection = RIGHT;
            hitBox.y += yChange;
        }
    }

    /**
     * Sets a new state for the Enemy.
     *
     * @param enemyState the new state of the Enemy
     */
    protected void newState(int enemyState) {
        this.state = enemyState;
        animationIndex = 0;
        animationTick = 0;
    }

    /**
     * Checks if the Enemy hits the Player and applies damage if true.
     *
     * @param player the Player object
     */
    protected void checkPlayerHit(Player player) {
        if (hitBox.intersects(player.hitBox))
            player.changeHealth(-s_getDamage());
        attackChecked = true;
    }

    /**
     * Checks if the Enemy is alive.
     *
     * @return true if the Enemy is alive, false otherwise
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets the alive status of the Enemy.
     *
     * @param alive the alive status to set
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Resets the Enemy to its initial state.
     */
    public void resetEnemy() {
        hitBox.x = x;
        hitBox.y = y;
        alive = true;
        newState(IDLE);
        currentAmountOfHearts = maxAmountOfHearts;
    }
}