package cz.cvut.fel.pjv.mashkvla.objects;

/**
 * Represents a coin object in the game.
 */
public class Coin extends GameObject {

    /**
     * Constructs a new Coin object.
     *
     * @param x The x-coordinate of the coin's initial position.
     * @param y The y-coordinate of the coin's initial position.
     */
    public Coin(int x, int y) {
        super(x, y);
        doAnimation = true;
        this.value = 1;
        initHitBox(20, 20);
    }

    /**
     * Updates the coin's state.
     */
    public void update() {
        updateAnimationTick();
    }
}
