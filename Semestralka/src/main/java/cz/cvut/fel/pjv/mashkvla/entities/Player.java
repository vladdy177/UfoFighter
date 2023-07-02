package cz.cvut.fel.pjv.mashkvla.entities;

import cz.cvut.fel.pjv.mashkvla.audio.AudioPlayer;
import cz.cvut.fel.pjv.mashkvla.gameStates.Playing;
import cz.cvut.fel.pjv.mashkvla.objects.Bullet;
import cz.cvut.fel.pjv.mashkvla.starter.Game;
import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.SCALE;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.ANIMATION_SPEED;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.Bullet.*;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.PlayerConstants.*;
import static cz.cvut.fel.pjv.mashkvla.utils.HelpMethods.s_canMoveHere;
import static cz.cvut.fel.pjv.mashkvla.utils.ImageManager.s_getFont;

/**
 * The Player class represents the player entity in the game.
 */
public class Player extends Entity {

    private BufferedImage[][] animations;
    private final Font font;

    // Player's health
    private BufferedImage heartImage;
    private final BufferedImage coinImage;

    private boolean left, up, down, right;
    private int[][] playGroundData;
    private float xDrawOffset = 2 * SCALE;
    // The higher the number, the longer the interval between shots
    private int playerShotDelay = 500;
    private int score;
    private int money;
    private int highScore;
    private int collectedMoney;
    private int maxAmountOfHearts;
    private int shipLvl = 1;

    private int flipX = 0;
    private int flipW = 1;

    private Playing playing;

    /**
     * Constructs a new Player object.
     *
     * @param x       The initial x-coordinate of the player.
     * @param y       The initial y-coordinate of the player.
     * @param width   The width of the player entity.
     * @param height  The height of the player entity.
     * @param playing The Playing game state.
     * @throws IOException           If an I/O error occurs during image or font loading.
     * @throws FontFormatException  If the font file has an invalid format.
     */
    public Player(float x, float y, int width, int height, Playing playing) throws IOException, FontFormatException {
        super(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));

        font = s_getFont(ImageManager.FONT);
        coinImage = ImageManager.s_getSpriteAtlas(ImageManager.COIN);

        this.playing = playing;
        this.state = IDLE;
        this.currentAmountOfHearts = maxAmountOfHearts;
        this.flySpeed = 1f * SCALE;

        loadAnimations();

        initHitBox(28, 31);
    }

    /**
     * Updates the player's state and position.
     */
    public void update() {
        if (currentAmountOfHearts <= 0) {
            if (state != BOOM) {
                state = BOOM;
                animationIndex = 0;
                animationTick = 0;
                playing.setPlayerExploding(true);
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.PLAYER_BOOM);
            } else if (animationIndex == s_getSpriteAmount(BOOM) - 1 && animationTick >= ANIMATION_SPEED - 1) {
                playing.setGameOver(true);
                playing.getGame().getAudioPlayer().stopMusic();
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
            } else {
                updateAnimationTick();
            }
            return;
        }
        if (shooting) {
            shoot();
        }
        updatePosition();
        checkCoinTouched();
        updateAnimationTick();
        setAnimations();
    }

    /**
     * Renders the player on the screen.
     *
     * @param g                The graphics object to draw on.
     * @param xPlaygroundOffset The x-coordinate offset of the playground.
     * @param yPlaygroundOffset The y-coordinate offset of the playground.
     */
    public void render(Graphics g, int xPlaygroundOffset, int yPlaygroundOffset) {
        drawUI(g);
        g.drawImage(animations[state][animationIndex], (int) (hitBox.x - xDrawOffset) - xPlaygroundOffset + flipX,
                (int) (hitBox.y) - yPlaygroundOffset, width * flipW, height, null);
        // Debugging player hit box
        // drawHitBox(g, xPlaygroundOffset, yPlaygroundOffset);
    }

    /**
     * Makes the player shoot a bullet.
     */
    private void shoot() {
        int bulletX = (int) (hitBox.x + hitBox.width / 2);
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= this.playerShotDelay) {
            int direction = -1;
            playing.getGame().getAudioPlayer().playPlayerShotSound();
            playing.getObjectManager().getBullets().add(new Bullet(bulletX, (int) (hitBox.y), direction, PLAYER_SHOOTER));
            lastShotTime = currentTime;
        }
    }

    /**
     * Checks if the player touches a coin.
     */
    private void checkCoinTouched() {
        this.playing.checkCoinTouched(hitBox);
    }

    /**
     * Changes the player's health.
     *
     * @param value The amount by which to change the health.
     */
    public void changeHealth(int value) {
        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.PLAYER_HIT);
        currentAmountOfHearts += value;
        if (currentAmountOfHearts <= 0) {
            currentAmountOfHearts = 0;
        } else if (currentAmountOfHearts >= maxAmountOfHearts) {
            currentAmountOfHearts = maxAmountOfHearts;
        }
    }

    /**
     * Draws the user interface (UI) elements on the screen.
     *
     * @param g The graphics object to draw on.
     */
    private void drawUI(Graphics g) {
        int heartSize = (int) (16 * SCALE);
        int moneyStringX = Game.GAME_WIDTH - 80;
        int scoreStringX = Game.GAME_WIDTH / 2 - 50;
        for (int i = 1; i < currentAmountOfHearts + 1; i++) {
            g.drawImage(heartImage, i * 12, 15, (int) (heartSize * 1.25), (int) (heartSize * 1.25), null);
        }

        BufferedImage firstCoin = coinImage.getSubimage(0, 0, 20, 20);

        g.drawImage(firstCoin, Game.GAME_WIDTH - 100, 15, null);
        g.setFont(font.deriveFont(Font.BOLD, 20F));
        g.setColor(Color.WHITE);
        g.drawString(":" + getMoney(), moneyStringX, 33);
        g.drawString("Score:" + getScore(), scoreStringX, 28);
    }

    /**
     * Updates the animation tick, which determines the current frame of the animation.
     * Also updates the animation index accordingly.
     */
    public void updateAnimationTick() {
        animationTick++;
        if (animationTick >= ANIMATION_SPEED) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= s_getSpriteAmount(state)) {
                animationIndex = 0;
            }
        }
    }

    /**
     * Sets the appropriate animation state based on the player's movement.
     */
    private void setAnimations() {
        int startAnimation = this.state;
        if (this.up) {
            this.state = BOOST;
        }
        if (this.down) {
            this.state = SLOWING_DOWN;
        }
        if (this.left || this.right) {
            this.state = TURNING;
        }
        if (!this.up && !this.down && !this.left && !this.right) {
            this.state = IDLE;
        }
        if (startAnimation != state) {
            resetAnimationTick();
        }
    }

    /**
     * Updates the player's position based on its movement.
     */
    private void updatePosition() {
        float xSpeed = 0, ySpeed = 0;
        if (left && !right) {
            xSpeed = -flySpeed;
            flipX = 0;
            flipW = 1;
        } else if (right && !left) {
            xSpeed = flySpeed;
            flipX = width;
            flipW = -1;
        }
        if (up && !down) {
            ySpeed = -flySpeed;
        } else if (down && !up) {
            ySpeed = flySpeed;
        }
        if (s_canMoveHere(hitBox.x + xSpeed, hitBox.y + ySpeed, hitBox.width, hitBox.height, playGroundData)) {
            hitBox.x += xSpeed;
            hitBox.y += ySpeed;
        }
    }

    /**
     * Loads the animations for the player from sprite sheets.
     */
    private void loadAnimations() {
        BufferedImage image = ImageManager.s_getSpriteAtlas(ImageManager.PLAYER_ATLAS);
        animations = new BufferedImage[6][10];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = image.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
        heartImage = ImageManager.s_getSpriteAtlas(ImageManager.HEART);
    }

    /**
     * Sets the player's spawn point.
     *
     * @param spawn The spawn point coordinates.
     */
    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitBox.x = spawn.x;
        hitBox.y = spawn.y;
    }

    /**
     * Resets the animation tick and index to their initial values.
     */
    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    /**
     * Resets the direction booleans to their initial values.
     */
    public void resetDirectionBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    /**
     * Resets all player attributes to their initial values.
     */
    public void resetAll() {
        resetDirectionBooleans();
        state = IDLE;
        currentAmountOfHearts = maxAmountOfHearts;
        score = 0;
        money = 0;
        x = 0;
        y = 0;
        resetAnimationTick();
    }

    /**
     * Sets the player's high score.
     *
     * @param highScore The high score value.
     */
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    /**
     * Increases the player's score.
     *
     * @param value The amount by which to increase the score.
     */
    public void increaseScore(int value) {
        this.score += value;
        if (this.score > this.highScore) {
            this.highScore = this.score;
        }
    }

    /**
     * Increases the player's money.
     *
     * @param value The amount by which to increase the money.
     */
    public void increaseMoney(int value) {
        this.money += value;
        this.collectedMoney += value;
    }

    // Getters and Setters


    public void setPlayGroundData(int[][] playGroundData) {
        this.playGroundData = playGroundData;
    }

    public int[][] getPlayGroundData() {
        return playGroundData;
    }

    public int getScore() {
        return score;
    }

    public int getMoney() {
        return money;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getCollectedMoney() {
        return collectedMoney;
    }

    public void setCollectedMoney(int collectedMoney) {
        this.collectedMoney = collectedMoney;
    }

    public int getMaxAmountOfHearts() {
        return maxAmountOfHearts;
    }

    public void setMaxAmountOfHearts(int maxAmountOfHearts) {
        this.maxAmountOfHearts = maxAmountOfHearts;
    }

    public int getShipLvl() {
        return shipLvl;
    }

    public void setShipLvl(int shipLvl) {
        this.shipLvl = shipLvl;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public int getPlayerShotDelay() {
        return playerShotDelay;
    }

    public void setPlayerShotDelay(int playerShotDelay) {
        this.playerShotDelay = playerShotDelay;
    }



}