package cz.cvut.fel.pjv.mashkvla.entities;

import cz.cvut.fel.pjv.mashkvla.utils.Constants;

import static cz.cvut.fel.pjv.mashkvla.utils.Constants.EnemyConstants.*;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.Directions.*;

public class Ufo extends Enemy {

    private final int scoreValue = 25;

    public Ufo(int x, int y) {
        // Call the superclass constructor and initialize hitbox
        super(x, y, Constants.EnemyConstants.ENEMY_SIZE, ENEMY_SIZE, UFO);
        initHitBox(32, 16);
    }

    /**
     * Updates the UFO's position and animation based on the playground data and player.
     *
     * @param playgroundData the playground data
     * @param player  the player object
     */
    public void update(int[][] playgroundData, Player player) {
        updateRole(playgroundData, player, this.enemyType);
        updateAnimationTick();
    }

    /**
     * Returns the X-coordinate to flip the sprite based on the flying direction.
     *
     * @return the X-coordinate to flip the sprite
     */
    public int flipX() {
        if (flyDirection == RIGHT)
            return width;
        else
            return 0;
    }

    /**
     * Returns the width factor to flip the sprite based on the flying direction.
     *
     * @return the width factor to flip the sprite
     */
    public int flipW() {
        if (flyDirection == RIGHT)
            return -1;
        else
            return 1;
    }

    /**
     * Returns the score value of the UFO.
     *
     * @return the score value
     */
    public int getScoreValue() {
        return scoreValue;
    }
}
