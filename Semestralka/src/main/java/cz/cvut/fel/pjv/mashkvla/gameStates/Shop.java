package cz.cvut.fel.pjv.mashkvla.gameStates;

import cz.cvut.fel.pjv.mashkvla.audio.AudioPlayer;
import cz.cvut.fel.pjv.mashkvla.starter.Game;
import cz.cvut.fel.pjv.mashkvla.ui.*;
import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.SCALE;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.Buttons.*;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.URMButtons.URM_B_SIZE;
import static cz.cvut.fel.pjv.mashkvla.utils.ImageManager.s_getFont;

/**
 * Represents the shop state in the game.
 */
public class Shop extends State implements StateMethods {

    private Font font;

    private UpgradeButton upgradeHealthButton, upgradeShootingSpeedButton;

    private UrmButton goToMenuB, readyToPlayB;

    private BufferedImage shopBackground, coinImage, heartImage;

    private boolean error;
    private long errorDisplayStartTime = 0;

    /**
     * Constructs a new Shop instance.
     *
     * @param game The Game object.
     * @throws IOException           If there is an error loading the required resources.
     * @throws FontFormatException  If there is an error loading the font.
     */
    public Shop(Game game) throws IOException, FontFormatException {
        super(game);
        font = s_getFont(ImageManager.FONT);
        coinImage = ImageManager.s_getSpriteAtlas(ImageManager.COIN);
        loadButtons();
        shopBackground = ImageManager.s_getSpriteAtlas(ImageManager.SHOP_BACKGROUND);
        heartImage = ImageManager.s_getSpriteAtlas(ImageManager.HEART);
    }


    private void loadButtons() {
        int unpauseX = (int)(Game.SCALE * 10);
        int menuX = Game.GAME_WIDTH - 100;
        int buttonY = Game.GAME_HEIGHT - (int)(Game.SCALE * 70);

        readyToPlayB = new UrmButton(menuX, buttonY, URM_B_SIZE, URM_B_SIZE, 0);
        goToMenuB = new UrmButton(unpauseX, buttonY, URM_B_SIZE, URM_B_SIZE, 2);

        upgradeHealthButton = new UpgradeButton(Game.GAME_WIDTH - 145, Game.GAME_HEIGHT / 3 - 90, HEALTH_UPDATE, 20);
        upgradeShootingSpeedButton = new UpgradeButton(Game.GAME_WIDTH - 145, Game.GAME_HEIGHT / 3, SHOOTING_SPEED_UPDATE, 20);
    }

    /**
     * Updates the shop state.
     */
    @Override
    public void update() {
        upgradeHealthButton.update();
        upgradeShootingSpeedButton.update();

        goToMenuB.update();
        readyToPlayB.update();
    }

    /**
     * Renders the shop graphics.
     *
     * @param g The graphics object to draw on.
     */
    @Override
    public void draw(Graphics g) {
        drawUi(g);
        if(error){
            drawError(g);
        }

        upgradeHealthButton.draw(g);
        upgradeShootingSpeedButton.draw(g);

        goToMenuB.draw(g);
        readyToPlayB.draw(g);
    }

    /**
     * Draws the user interface elements.
     *
     * @param g The graphics object to draw on.
     */
    private void drawUi(Graphics g) {
        int heartSize = (int)(16 * SCALE);

        g.drawImage(shopBackground, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT,null);
        BufferedImage firstCoin = coinImage.getSubimage(0,0,20,20);

        g.drawImage(firstCoin, Game.GAME_WIDTH - 130, 15, null);

        g.setFont(font.deriveFont(Font.BOLD, 21F));
        g.setColor(Color.WHITE);
        g.drawString(":" + game.getPlaying().getPlayer().getCollectedMoney(),Game.GAME_WIDTH - 100, 33);
        g.drawString("Ship level: " + game.getPlaying().getPlayer().getShipLvl(), 10, 33);

        for (int i = 1; i < game.getPlaying().getPlayer().getMaxAmountOfHearts() + 1; i++)
            g.drawImage(heartImage, i * 12, 50, (int)(heartSize * 1.25), (int)(heartSize * 1.25), null);

        g.drawString("Highscore: " + game.getPlaying().getPlayer().getHighScore(),Game.GAME_WIDTH / 2 - 100, 33);
        g.drawString("+1 heart / 20 coins", (int)(Game.GAME_WIDTH - 3.25 * B_WIDTH), Game.GAME_HEIGHT / 4);
        g.drawString("+2 fire speed / 20 coins", (int)(Game.GAME_WIDTH - 3.75 * B_WIDTH), (int)(Game.GAME_HEIGHT / 2.5));
    }

    /**
     * Draws the error message if there is not enough money.
     *
     * @param g The graphics object to draw on.
     */
    private void drawError(Graphics g) {
        if (System.currentTimeMillis() - errorDisplayStartTime < 1000) {
            g.setFont(font.deriveFont(Font.BOLD, 30F));
            g.setColor(Color.WHITE);
            g.drawString("NOT ENOUGH MONEY!", Game.GAME_WIDTH / 3, 66);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(isInUpgradeButton(e, upgradeHealthButton))
            upgradeHealthButton.setMousePressed(true);
        else if (isInUpgradeButton(e, upgradeShootingSpeedButton))
            upgradeShootingSpeedButton.setMousePressed(true);
        else if(isInPauseButton(e, goToMenuB))
            goToMenuB.setMousePressed(true);
        else if(isInPauseButton(e, readyToPlayB))
            readyToPlayB.setMousePressed(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) throws IOException {
        if(isInUpgradeButton(e, upgradeHealthButton)){
            upgradeHealthButton.checkUpgrades(game.getPlaying().getPlayer());
            if (!upgradeHealthButton.isEnoughMoney()) {
                this.error = true;
                this.errorDisplayStartTime = System.currentTimeMillis();
            }
        }
        else if(isInUpgradeButton(e, upgradeShootingSpeedButton)){
            upgradeShootingSpeedButton.checkUpgrades(game.getPlaying().getPlayer());
            if (!upgradeShootingSpeedButton.isEnoughMoney()) {
                this.error = true;
                this.errorDisplayStartTime = System.currentTimeMillis();
            }
        }
        else if(isInPauseButton(e, goToMenuB)){
            if(goToMenuB.isMousePressed()){
                game.saveGame();
                game.getPlaying().setGameState(GameState.MENU);
            }
        }
        else if(isInPauseButton(e, readyToPlayB)){
            if(readyToPlayB.isMousePressed()){
                game.saveGame();
                game.loadGame();
                game.getPlaying().resetAll();
                game.getPlaying().setGameState(GameState.PLAYING);
                game.getPlaying().getGame().getAudioPlayer().playMusic(AudioPlayer.PLAYING);
            }
        }

        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        upgradeHealthButton.setMouseOver(false);
        upgradeShootingSpeedButton.setMouseOver(false);
        goToMenuB.setMouseOver(false);
        readyToPlayB.setMouseOver(false);

        if(isInUpgradeButton(e, upgradeHealthButton))
            upgradeHealthButton.setMouseOver(true);
        else if (isInUpgradeButton(e, upgradeShootingSpeedButton))
            upgradeShootingSpeedButton.setMouseOver(true);
        else if(isInPauseButton(e, goToMenuB))
            goToMenuB.setMouseOver(true);
        else if(isInPauseButton(e, readyToPlayB))
            readyToPlayB.setMouseOver(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {    }

    @Override
    public void keyReleased(KeyEvent e) {    }

    private void resetButtons() {
        upgradeHealthButton.resetBooleans();
        upgradeShootingSpeedButton.resetBooleans();

        goToMenuB.resetBooleans();
        readyToPlayB.resetBooleans();
    }
    /**
     * Checks if the given mouse event is within the boundaries of the specified pause button.
     *
     * @param e The MouseEvent object.
     * @param b The PauseButton object to check.
     * @return true if the mouse event is within the button boundaries, false otherwise.
     */
    public boolean isInPauseButton(MouseEvent e, PauseButton b){
        return b.getBounds().contains(e.getX(), e.getY());
    }

    /**
     * Checks if the given mouse event is within the boundaries of the specified upgrade button.
     *
     * @param e The MouseEvent object.
     * @param b The UpgradeButton object to check.
     * @return true if the mouse event is within the button boundaries, false otherwise.
     */
    public boolean isInUpgradeButton(MouseEvent e, UpgradeButton b){
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public UpgradeButton getUpgradeHealthButton() {
        return upgradeHealthButton;
    }

    public UrmButton getReadyToPlayB() {
        return readyToPlayB;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setUpgradeHealthButton(UpgradeButton upgradeHealthButton) {
        this.upgradeHealthButton = upgradeHealthButton;
    }

    public UpgradeButton getUpgradeShootingSpeedButton() {
        return upgradeShootingSpeedButton;
    }

    public void setUpgradeShootingSpeedButton(UpgradeButton upgradeShootingSpeedButton) {
        this.upgradeShootingSpeedButton = upgradeShootingSpeedButton;
    }

    public UrmButton getGoToMenuB() {
        return goToMenuB;
    }

    public void setGoToMenuB(UrmButton goToMenuB) {
        this.goToMenuB = goToMenuB;
    }

    public void setReadyToPlayB(UrmButton readyToPlayB) {
        this.readyToPlayB = readyToPlayB;
    }

    public BufferedImage getShopBackground() {
        return shopBackground;
    }

    public void setShopBackground(BufferedImage shopBackground) {
        this.shopBackground = shopBackground;
    }

    public BufferedImage getCoinImage() {
        return coinImage;
    }

    public void setCoinImage(BufferedImage coinImage) {
        this.coinImage = coinImage;
    }

    public BufferedImage getHeartImage() {
        return heartImage;
    }

    public void setHeartImage(BufferedImage heartImage) {
        this.heartImage = heartImage;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public long getErrorDisplayStartTime() {
        return errorDisplayStartTime;
    }

    public void setErrorDisplayStartTime(long errorDisplayStartTime) {
        this.errorDisplayStartTime = errorDisplayStartTime;
    }
}
