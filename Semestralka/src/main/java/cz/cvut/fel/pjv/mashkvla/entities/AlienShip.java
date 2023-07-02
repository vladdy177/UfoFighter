package cz.cvut.fel.pjv.mashkvla.entities;

import cz.cvut.fel.pjv.mashkvla.gameStates.Playing;
import cz.cvut.fel.pjv.mashkvla.objects.Bullet;
import cz.cvut.fel.pjv.mashkvla.utils.Constants;

import java.util.Random;

import static cz.cvut.fel.pjv.mashkvla.utils.Constants.Bullet.ENEMY_SHOOTER;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.Directions.RIGHT;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.EnemyConstants.*;

public class AlienShip extends Enemy {
    private static final Random rand = new Random();
    private static final int shootDelay = 600;
    private static final float shotChance = 0.15f;

    /**
     * Constructs a new AlienShip object.
     *
     * @param x the x-coordinate of the AlienShip's position
     * @param y the y-coordinate of the AlienShip's position
     */
    public AlienShip(float x, float y) {
        super(x, y, Constants.EnemyConstants.ENEMY_SIZE, ENEMY_SIZE, ALIEN);
        initHitBox(32, 25);
    }

    /**
     * Updates the AlienShip's state based on the playground data and player information.
     *
     * @param playgroundData the playground data array
     * @param player  the Player object
     */
    public void update(int[][] playgroundData, Player player) {
        updateRole(playgroundData, player, this.enemyType);
        updateAnimationTick();
    }

    /**
     * Makes the AlienShip shoot bullets.
     *
     * @param playing the Playing state object
     */
    public void shoot(Playing playing) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastShotTime >= shootDelay) {
            int direction = 1;
            if (isAlive()) {
                if (rand.nextDouble() < shotChance) {
                    playing.getObjectManager().getBullets().add(new Bullet((int) (hitBox.x + hitBox.width / 2), (int) (hitBox.y), direction, ENEMY_SHOOTER));
                }
            }
            lastShotTime = currentTime;
        }
    }

    /**
     * Flips the x-coordinate of the AlienShip's sprite based on its fly direction.
     *
     * @return the flipped x-coordinate
     */
    public int flipX() {
        if (flyDirection == RIGHT) {
            return width;
        } else {
            return 0;
        }
    }

    /**
     * Flips the width of the AlienShip's sprite based on its fly direction.
     *
     * @return the flipped width
     */
    public int flipW() {
        if (flyDirection == RIGHT) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Retrieves the score value of the AlienShip.
     *
     * @return the score value
     */
    public int getScoreValue() {
        return 50;
    }
}
