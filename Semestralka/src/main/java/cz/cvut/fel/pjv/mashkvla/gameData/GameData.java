package cz.cvut.fel.pjv.mashkvla.gameData;

import java.io.Serial;
import java.io.Serializable;

public class GameData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int playerMoney, playerHighScore, playerLevel, playerHealth, fireSpeed;

    /**
     * Constructs a new GameData object.
     */
    public GameData() {}

    /**
     * Returns the player's current money.
     *
     * @return the player's money
     */
    public int getPlayerMoney() {
        return playerMoney;
    }

    /**
     * Sets the player's money.
     *
     * @param playerMoney the player's money
     */
    public void setPlayerMoney(int playerMoney) {
        this.playerMoney = playerMoney;
    }

    /**
     * Returns the player's high score.
     *
     * @return the player's high score
     */
    public int getPlayerHighScore() {
        return playerHighScore;
    }

    /**
     * Sets the player's high score.
     *
     * @param playerHighScore the player's high score
     */
    public void setPlayerHighScore(int playerHighScore) {
        this.playerHighScore = playerHighScore;
    }

    /**
     * Returns the player's current level.
     *
     * @return the player's level
     */
    public int getPlayerLevel() {
        return playerLevel;
    }

    /**
     * Sets the player's level.
     *
     * @param playerLevel the player's level
     */
    public void setPlayerLevel(int playerLevel) {
        this.playerLevel = playerLevel;
    }

    /**
     * Returns the player's current health.
     *
     * @return the player's health
     */
    public int getPlayerHealth() {
        return playerHealth;
    }

    /**
     * Sets the player's health.
     *
     * @param playerHealth the player's health
     */
    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    /**
     * Returns the player's fire speed.
     *
     * @return the player's fire speed
     */
    public int getFireSpeed() {
        return fireSpeed;
    }

    /**
     * Sets the player's fire speed.
     *
     * @param fireSpeed the player's fire speed
     */
    public void setPlayerFireSpeed(int fireSpeed) {
        this.fireSpeed = fireSpeed;
    }
}