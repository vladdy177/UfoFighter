package cz.cvut.fel.pjv.mashkvla.entities;

import cz.cvut.fel.pjv.mashkvla.gameStates.Playing;
import cz.cvut.fel.pjv.mashkvla.starter.Game;
import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static cz.cvut.fel.pjv.mashkvla.utils.Constants.EnemyConstants.*;
import static cz.cvut.fel.pjv.mashkvla.utils.HelpMethods.*;
import static cz.cvut.fel.pjv.mashkvla.utils.ImageManager.PLAYGROUND_DATA;

public class EnemyManager {

    private Random random = new Random();
    private Playing playing;
    private BufferedImage[][] ufoArray;
    private BufferedImage[][] aliensArray;
    private ArrayList<Ufo> ufoShips = new ArrayList<>();
    private ArrayList<AlienShip> alienShips = new ArrayList<>();

    /**
     * Constructs a new EnemyManager object.
     *
     * @param playing the Playing object
     */
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
        addEnemies();
    }

    /**
     * Adds initial enemy ships to the game.
     */
    private void addEnemies() {
        addAlienShips();
        addUfoShips();
        if (alienShips.size() == 0) {
            generateAlienShip();
        }
        if (ufoShips.size() == 0) {
            generateUfoShip();
        }
    }

    /**
     * Generates a new UFO ship at a random position.
     */
    public void generateUfoShip() {
        ufoShips.add(new Ufo(random.nextInt(Game.GAME_WIDTH - 50), random.nextInt((Game.GAME_HEIGHT))));
    }

    /**
     * Generates a new Alien ship at a random position.
     */
    public void generateAlienShip() {
        alienShips.add(new AlienShip(random.nextInt((Game.GAME_WIDTH - 50)), random.nextInt(Game.GAME_HEIGHT)));
    }

    /**
     * Loads UFO and Alien ship images from sprite atlas.
     */
    private void loadEnemyImages() {
        ufoArray = new BufferedImage[3][9];
        BufferedImage temp = ImageManager.s_getSpriteAtlas(ImageManager.UFO);
        for (int j = 0; j < ufoArray.length; j++) {
            for (int i = 0; i < ufoArray[j].length; i++) {
                ufoArray[j][i] = temp.getSubimage(i * ENEMY_SIZE_DEFAULT, j * ENEMY_SIZE_DEFAULT, ENEMY_SIZE_DEFAULT, ENEMY_SIZE_DEFAULT);
            }
        }

        aliensArray = new BufferedImage[3][7];
        BufferedImage temp2 = ImageManager.s_getSpriteAtlas(ImageManager.ALIEN_SHIP);
        for (int j = 0; j < aliensArray.length; j++) {
            for (int i = 0; i < aliensArray[j].length; i++) {
                aliensArray[j][i] = temp2.getSubimage(i * ENEMY_SIZE_DEFAULT, j * ENEMY_SIZE_DEFAULT, ENEMY_SIZE_DEFAULT, ENEMY_SIZE_DEFAULT);
            }
        }
    }

    /**
     * Adds UFO ships from saved data.
     */
    public void addUfoShips() {
        ufoShips = s_getUfoShips(ImageManager.s_getSpriteAtlas(PLAYGROUND_DATA));
    }

    /**
     * Adds Alien ships from saved data.
     */
    public void addAlienShips() {
        alienShips = s_getAlienShips(ImageManager.s_getSpriteAtlas(PLAYGROUND_DATA));
    }

    /**
     * Updates the enemy ships.
     *
     * @param playgroundData the playground data array
     * @param player         the Player object
     */
    public void update(int[][] playgroundData, Player player) {
        Iterator<Ufo> ufoIterator = ufoShips.iterator();
        while (ufoIterator.hasNext()) {
            Ufo u = ufoIterator.next();
            if (u.isAlive()) {
                u.update(playgroundData, player);
            } else {
                ufoIterator.remove();
            }
        }

        Iterator<AlienShip> alienIterator = alienShips.iterator();
        while (alienIterator.hasNext()) {
            AlienShip as = alienIterator.next();
            if (as.isAlive()) {
                as.update(playgroundData, player);
                as.shoot(playing);
            } else {
                alienIterator.remove();
            }
        }
    }

    /**
     * Draws the enemy ships.
     *
     * @param g                the Graphics object
     * @param xPlayGroundOffset the x offset of the playground
     * @param yPlayGroundOffset the y offset of the playground
     */
    public void draw(Graphics g, int xPlayGroundOffset, int yPlayGroundOffset) {
        drawEnemies(g, xPlayGroundOffset, yPlayGroundOffset);
    }

    /**
     * Draws the UFO and Alien ships.
     *
     * @param g             the Graphics object
     * @param xPlayGroundOffset    the x offset of the level
     * @param yPlayGroundOffset    the y offset of the level
     */
    private void drawEnemies(Graphics g, int xPlayGroundOffset, int yPlayGroundOffset) {
        for (int i = ufoShips.size() - 1; i >= 0; i--) {
            Ufo u = ufoShips.get(i);
            if (u.isAlive()) {
                g.drawImage(ufoArray[u.getState()][u.getAnimationIndex()], (int) (u.getHitBox().x - xPlayGroundOffset + u.flipX()), (int) (u.getHitBox().y - yPlayGroundOffset - UFO_DRAW_OFFSET_Y), ENEMY_SIZE * u.flipW(), ENEMY_SIZE, null);
            } else {
                ufoShips.remove(i);
            }
        }

        for (int i = alienShips.size() - 1; i >= 0; i--) {
            AlienShip as = alienShips.get(i);
            if (as.isAlive()) {
                g.drawImage(aliensArray[as.getState()][as.getAnimationIndex()], (int) (as.getHitBox().x - xPlayGroundOffset + as.flipX() - ALIEN_DRAW_OFFSET_X), (int) (as.getHitBox().y - yPlayGroundOffset - ALIEN_DRAW_OFFSET_Y), ENEMY_SIZE * as.flipW(), ENEMY_SIZE, null);
            } else {
                alienShips.remove(i);
            }
        }
    }

    /**
     * Resets all enemy ships to their initial state.
     */
    public void resetAll() {
        resetAlienShips();
        resetUfoShips();
        addEnemies();
    }

    /**
     * Resets the Alien ships to their initial state.
     */
    private void resetAlienShips() {
        for (AlienShip as : alienShips) {
            as.resetEnemy();
        }
    }

    /**
     * Resets the UFO ships to their initial state.
     */
    private void resetUfoShips() {
        for (Ufo u : ufoShips) {
            u.resetEnemy();
        }
    }

    /**
     * Returns the list of Alien ships.
     *
     * @return the list of AlienShip objects
     */
    public ArrayList<AlienShip> getAlienShips() {
        return alienShips;
    }

    /**
     * Returns the list of UFO ships.
     *
     * @return the list of Ufo objects
     */
    public ArrayList<Ufo> getUfoShips() {
        return ufoShips;
    }
}
