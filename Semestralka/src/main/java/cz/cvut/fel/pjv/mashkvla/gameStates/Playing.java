package cz.cvut.fel.pjv.mashkvla.gameStates;

import cz.cvut.fel.pjv.mashkvla.entities.EnemyManager;
import cz.cvut.fel.pjv.mashkvla.entities.Player;
import cz.cvut.fel.pjv.mashkvla.objects.ObjectManager;
import cz.cvut.fel.pjv.mashkvla.playground.PlaygroundManager;
import cz.cvut.fel.pjv.mashkvla.starter.Game;
import cz.cvut.fel.pjv.mashkvla.ui.GameOverOverlay;
import cz.cvut.fel.pjv.mashkvla.ui.PauseOverlay;
import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.SCALE;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.Environment.*;

/**
 * The Playing state represents the state of the game when the player is actively playing.
 */
public class Playing extends State implements StateMethods{

    private Player player;
    private PlaygroundManager playgroundManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private boolean paused = false;
    private boolean gameOver;
    private boolean playerIsExploding;

    private int xPlaygroundOffset;
    private int yPlaygroundOffset;
    private int leftBorder = (int)(0.3 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.7 * Game.GAME_WIDTH);
    private int PlaygroundTilesWide = ImageManager.s_getPlaygroundData()[0].length;
    private int maxHorTilesOffset = PlaygroundTilesWide - Game.TILES_IN_WIDTH;
    private int maxPlaygroundOffsetX = maxHorTilesOffset * Game.TILES_SIZE;
    private int topBorder = (int)(0.3 * Game.GAME_HEIGHT);
    private int bottomBorder = (int)(0.7 * Game.GAME_HEIGHT);
    private int playgroundVertInTiles = ImageManager.s_getPlaygroundData().length;
    private int maxVertTilesOffset = playgroundVertInTiles - Game.TILES_IN_HEIGHT;
    private int maxPlaygroundOffsetY = maxVertTilesOffset * Game.TILES_SIZE;

    private BufferedImage movingBackground, stars, ringPlanet, farPlanets, bigPlanet;
    private int[] farPlanetsPosition;
    private Random random = new Random();


    /**
     * Constructs the Playing state.
     *
     * @param game the game instance
     * @throws IOException           if an I/O error occurs
     * @throws FontFormatException  if an error occurs during font loading
     * @throws ClassNotFoundException if a class is not found during loading
     */
    public Playing(Game game) throws IOException, FontFormatException, ClassNotFoundException {
        super(game);
        initClasses();
        movingBackground = ImageManager.s_getSpriteAtlas(ImageManager.PLAYING_BACKGROUND);
        stars = ImageManager.s_getSpriteAtlas(ImageManager.PLAYING_BACKGROUND_STARS);
        ringPlanet = ImageManager.s_getSpriteAtlas(ImageManager.PLAYING_BACKGROUND_RING_PLANET);
        farPlanets = ImageManager.s_getSpriteAtlas(ImageManager.PLAYING_BACKGROUND_FAR_PLANETS);
        bigPlanet = ImageManager.s_getSpriteAtlas(ImageManager.PLAYING_BACKGROUND_BIG_PLANET);
        farPlanetsPosition = new int[6];
        for (int i = 0; i < farPlanetsPosition.length; i++) {
            farPlanetsPosition[i] = (int)(10 * SCALE) + random.nextInt(Game.GAME_HEIGHT);
        }
    }


    /**
     * Initializes the game classes and objects.
     *
     * @throws IOException           If there is an error loading the required resources.
     * @throws FontFormatException  If there is an error loading the font.
     */
    private void initClasses() throws IOException, FontFormatException {
        playgroundManager = new PlaygroundManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this, enemyManager);
        player = new Player(10, 10, 32, 32, this);
        player.setSpawn(playgroundManager.getPlaygroundData().getPlayerSpawn());
        player.setPlayGroundData(playgroundManager.getPlaygroundData().getPlaygroundData());
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
    }

    /**
     * Updates the game state.
     */
    @Override
    public void update() {
        if(paused)
            pauseOverlay.update();
        else if (gameOver){
            gameOverOverlay.update();
        } else if(playerIsExploding){
            player.update();
        } else {
            player.update();
            enemyManager.update(playgroundManager.getPlaygroundData().getPlaygroundData(), player);
            objectManager.update();
            checkCloseToBorder();
        }
    }

    /**
     * Renders the game graphics.
     *
     * @param g The graphics object to draw on.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(movingBackground, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        drawSpaceObjects(g);

        playgroundManager.draw(g, xPlaygroundOffset, yPlaygroundOffset);
        objectManager.draw(g, xPlaygroundOffset, yPlaygroundOffset);
        player.render(g, xPlaygroundOffset, yPlaygroundOffset);
        enemyManager.draw(g, xPlaygroundOffset, yPlaygroundOffset);
        if(paused) {
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        }
    }

    /**
     * Draws the space objects (stars, planets) in the background.
     *
     * @param g The graphics object to draw on.
     */
    private void drawSpaceObjects(Graphics g) {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 10; j++) {
                g.drawImage(stars,i * STARS_WIDTH - (int)(xPlaygroundOffset * 0.5),j * STARS_HEIGHT - (int)(yPlaygroundOffset * 0.7), STARS_WIDTH, STARS_HEIGHT, null);
            }

        for (int i = 0; i < farPlanetsPosition.length; i++) {
            g.drawImage(farPlanets,FAR_PLANETS_WIDTH * i - (int)(xPlaygroundOffset * 0.4), farPlanetsPosition[i] * 2 - (int)(yPlaygroundOffset * 0.4),
                    FAR_PLANETS_WIDTH, FAR_PLANETS_HEIGHT, null);
        }

        g.drawImage(ringPlanet, RING_PLANET_WIDTH * 3 - (int)(xPlaygroundOffset * 0.2), RING_PLANET_HEIGHT - (int)(yPlaygroundOffset * 0.2),
                RING_PLANET_WIDTH * 3, RING_PLANET_HEIGHT * 3, null);

        g.drawImage(bigPlanet, BIG_PLANET_WIDTH * 7 - (int)(xPlaygroundOffset * 0.1), BIG_PLANET_HEIGHT - (int)(yPlaygroundOffset * 0.1),
                BIG_PLANET_WIDTH * 2, BIG_PLANET_HEIGHT * 2, null);
    }

    /**
     * Checks if the player's hitbox is touching a coin object and performs the necessary actions.
     * @param hitBox the hitbox of the player
     */
    public void checkCoinTouched(Rectangle2D.Float hitBox) {
        objectManager.checkCoinTouched(hitBox);
    }

    /**
     * Checks if the player is close to the borders of the visible playground area and adjusts the playground offset if necessary.
     */
    private void checkCloseToBorder() {
        int playerX = (int) player.getHitBox().x;
        int playerY = (int) player.getHitBox().y;
        int differenceX = playerX - xPlaygroundOffset;
        int differenceY = playerY - yPlaygroundOffset;

        if(differenceX > rightBorder)
            xPlaygroundOffset += differenceX - rightBorder;
        else if (differenceX < leftBorder) {
            xPlaygroundOffset += differenceX - leftBorder;
        }

        if(differenceY > bottomBorder)
            yPlaygroundOffset += differenceY - bottomBorder;
        else if (differenceY < topBorder) {
            yPlaygroundOffset += differenceY - topBorder;
        }

        if(xPlaygroundOffset > maxPlaygroundOffsetX)
            xPlaygroundOffset = maxPlaygroundOffsetX;
        else if (xPlaygroundOffset < 0) {
            xPlaygroundOffset = 0;
        }

        if(yPlaygroundOffset > maxPlaygroundOffsetY)
            yPlaygroundOffset = maxPlaygroundOffsetY;
        else if (yPlaygroundOffset < 0) {
            yPlaygroundOffset = 0;
        }
    }

    /**
     * Resets all game components to their initial state.
     */
    public void resetAll() {
        this.gameOver = false;
        this.paused = false;
        this.playerIsExploding = false;
        this.player.resetAll();
        player.setSpawn(playgroundManager.getPlaygroundData().getPlayerSpawn());
        this.enemyManager.resetAll();
        this.objectManager.resetAll();
    }

    /**
     * Sets the game over state.
     * @param gameOver true if the game is over, false otherwise
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Returns the object manager responsible for managing game objects.
     * @return the object manager
     */
    public ObjectManager getObjectManager() {
        return objectManager;
    }

    /**
     * Handles the mouse press event.
     *
     * @param e The MouseEvent object containing the event details.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver){
            if(paused)
                pauseOverlay.mousePressed(e);
        } else {
            gameOverOverlay.mousePressed(e);
        }
    }

    /**
     * Handles the mouse release event.
     *
     * @param e The MouseEvent object containing the event details.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver){
            if(paused)
                pauseOverlay.mouseReleased(e);
        } else {
            gameOverOverlay.mouseReleased(e);
        }

    }

    /**
     * Handles the mouse movement event.
     *
     * @param e The MouseEvent object containing the event details.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver){
            if(paused)
                pauseOverlay.mouseMoved(e);
        } else {
            gameOverOverlay.mouseMoved(e);
        }

    }

    /**
     * Handles the key press event.
     *
     * @param e The KeyEvent object containing the event details.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            gameOverOverlay.keyPressed(e);
        else
            switch (e.getKeyCode()){
                case KeyEvent.VK_W:
                    player.setUp(true);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setShooting(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
            }
    }


    /**
     * Handles the key release event.
     *
     * @param e The KeyEvent object containing the event details.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver)
            switch (e.getKeyCode()){
                case KeyEvent.VK_W:
                    player.setUp(false);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setShooting(false);
                    break;
            }
    }

    /**
     * Handles the mouse drag event.
     *
     * @param e The MouseEvent object containing the event details.
     */
    public void mouseDragged(MouseEvent e) {
        if(paused)
            pauseOverlay.mouseDragged(e);
    }

    /**
     * Returns the paused state of the game.
     * @return true if the game is paused, false otherwise
     */
    public void unpauseGame() {
        paused = false;
    }

    /**
     * Returns the player instance.
     * @return the player instance
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * Returns the enemy manager.
     * @return the enemy manager
     */
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    /**
     * Sets the player explosion state.
     * @param playerIsExploding true if the player is exploding, false otherwise
     */
    public void setPlayerExploding(boolean playerIsExploding) {
        this.playerIsExploding = playerIsExploding;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setPauseOverlay(PauseOverlay pauseOverlay) {
        this.pauseOverlay = pauseOverlay;
    }

    public void setGameOverOverlay(GameOverOverlay gameOverOverlay) {
        this.gameOverOverlay = gameOverOverlay;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setEnemyManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }

    public void setObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    public Object getPauseOverlay() {
        return pauseOverlay;
    }


}


