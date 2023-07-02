package cz.cvut.fel.pjv.mashkvla.objects;

import cz.cvut.fel.pjv.mashkvla.audio.AudioPlayer;
import cz.cvut.fel.pjv.mashkvla.entities.*;
import cz.cvut.fel.pjv.mashkvla.gameStates.Playing;
import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.logging.Level;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.LOGGER;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.Bullet.*;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.ObjectConstants.*;
import static cz.cvut.fel.pjv.mashkvla.utils.HelpMethods.*;
/**
 * The ObjectManager class manages game objects such as coins and bullets.
 */
public class ObjectManager {

    private Playing playing;
    private EnemyManager enemyManager;
    private BufferedImage[] coinImage;
    private BufferedImage playerBulletImage, enemyBulletImage;
    private ArrayList<Coin> coins;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private boolean ufoDestroyed = false;
    private boolean alienShipDestroyed = false;

    private long lastCoinSpawnTime;
    private long coinSpawnDelay = 12000;

    /**
     * Constructs an ObjectManager instance.
     *
     * @param playing       The Playing game state.
     * @param enemyManager  The EnemyManager instance.
     */
    public ObjectManager(Playing playing, EnemyManager enemyManager){
        this.playing = playing;
        this.enemyManager = enemyManager;
        loadImages();
        createCoins();
    }

    /**
     * Loads images used by the ObjectManager.
     */
    private void loadImages() {
        try {
            BufferedImage coinSprite = ImageManager.s_getSpriteAtlas(ImageManager.COIN);
            playerBulletImage = ImageManager.s_getSpriteAtlas(ImageManager.PLAYER_BULLET);
            enemyBulletImage = ImageManager.s_getSpriteAtlas(ImageManager.ENEMY_BULLET);
            this.coinImage = new BufferedImage[9];
            for (int i = 0; i < coinImage.length; i++)
                coinImage[i] = coinSprite.getSubimage(20 * i, 0, 20, 20);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading images", e);
        }
    }

    /**
     * Creates coins and adds them to the list.
     */
    private void createCoins() {
        coins = new ArrayList<>();
        int coinX = (int) (Math.random() * COIN_SPAWN_WIDTH);
        int coinY = COIN_SIZE;
        Coin coin = new Coin(coinX, coinY);
        coins.add(coin);
    }

    /**
     * Draws game objects.
     *
     * @param g                The Graphics object to draw on.
     * @param xPlayGroundOffset The X offset of the playground.
     * @param yPlayGroundOffset The Y offset of the playground.
     */
    public void draw(Graphics g, int xPlayGroundOffset, int yPlayGroundOffset){
        drawCoins(g, xPlayGroundOffset, yPlayGroundOffset);
        drawBullets(g, xPlayGroundOffset, yPlayGroundOffset);
    }

    /**
     * Updates game objects.
     */
    public void update() {
        try {
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastCoinSpawnTime >= coinSpawnDelay) {
                spawnCoin();
                lastCoinSpawnTime = currentTime;
            }
            updateCoins();
            updateBullet(
                    playing.getPlayer(),
                    playing.getPlayer().getPlayGroundData(),
                    playing.getEnemyManager().getAlienShips(),
                    playing.getEnemyManager().getUfoShips()
            );
            if (ufoDestroyed) {
                respawnUfoShip();
                ufoDestroyed = false;
            }

            if (alienShipDestroyed) {
                respawnAlienShip();
                alienShipDestroyed = false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating game objects", e);
        }
    }

    /**
     * Updates the positions and states of coins.
     */
    private void updateCoins() {
        synchronized (coins) {
            Iterator<Coin> iterator = coins.iterator();
            while (iterator.hasNext()) {
                Coin coin = iterator.next();
                if (coin.isActive) {
                    coin.update();
                    moveCoin(ImageManager.s_getPlaygroundData(), coin);
                } else {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Moves the coin downwards on the playground.
     *
     * @param playgroundData The playground data.
     * @param coin           The coin to move.
     */
    private void moveCoin(int[][] playgroundData, Coin coin){
        try {
            float speed = COIN_FALL_SPEED;
            if (s_canMoveHere(coin.hitBox.x, coin.hitBox.y, coin.hitBox.width, coin.hitBox.height, playgroundData))
                coin.hitBox.y += speed;
            else
                coin.setActive(false);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error moving coin", e);
        }
    }

    /**
     * Spawns a new coin.
     */
    private void spawnCoin() {
        int coinX = (int) (Math.random() * COIN_SPAWN_WIDTH);
        int coinY = 0;
        Coin coin = new Coin(coinX, coinY);
        coins.add(coin);
    }

    /**
     * Draws coins on the playground.
     *
     * @param g                  The Graphics object to draw on.
     * @param xPlayGroundOffset  The X offset of the playground.
     * @param yPlayGroundOffset  The Y offset of the playground.
     */
    private void drawCoins(Graphics g, int xPlayGroundOffset, int yPlayGroundOffset){
        try {
            Iterator<Coin> iterator = coins.iterator();
            while (iterator.hasNext()) {
                Coin coin = iterator.next();
                if (coin.isActive) {
                    g.drawImage(coinImage[coin.getAnimationIndex()], (int) (coin.getHitBox().x - xPlayGroundOffset),
                            (int) (coin.getHitBox().y - yPlayGroundOffset), COIN_SIZE, COIN_SIZE, null);
//                coin.drawHitBox(g, xPlayGroundOffset, yPlayGroundOffset);
                } else
                    iterator.remove();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error drawing coins", e);
        }
    }

    /**
     * Increases the player's money when a coin is collected.
     *
     * @param coin The collected coin.
     */
    public void increaseMoney(Coin coin){
            playing.getPlayer().increaseMoney(coin.value);
    }

    /**
     * Updates the positions and states of bullets. Also checks bullet collision,
     * and delete enemies, if the got hit.
     *
     * @param player      The player object.
     * @param playgroundData The playground data.
     * @param alienShips  The list of alien ships.
     * @param ufoShips    The list of UFO ships.
     */
    private void updateBullet(Player player, int[][] playgroundData, ArrayList<AlienShip> alienShips, ArrayList<Ufo> ufoShips) {
        try {
            ListIterator<Bullet> iterator = bullets.listIterator();
            while (iterator.hasNext()) {
                Bullet b = iterator.next();
                if (b.isActive()) {
                    b.updatePosition();
                    if (s_bulletHitPlaygroundBorder(b, playgroundData)) {
                        iterator.remove();
                    } else if (b.getHitBox().intersects(player.getHitBox()) && b.getShooterType() == ENEMY_SHOOTER) {
                        player.changeHealth(-1);
                        iterator.remove();
                    } else {
                        Iterator<AlienShip> alienShipIterator = alienShips.iterator();
                        while (alienShipIterator.hasNext()) {
                            AlienShip as = alienShipIterator.next();
                            if (checkEnemyHitByPlayer(as, b)) {
                                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.ENEMY_HIT);
                                as.setAlive(false);
                                player.increaseScore(as.getScoreValue());
                                alienShipIterator.remove();
                                iterator.remove();
                                alienShipDestroyed = true;
                                break;
                            }
                        }
                        Iterator<Ufo> ufoShipsIterator = ufoShips.iterator();
                        while (ufoShipsIterator.hasNext()) {
                            Ufo u = ufoShipsIterator.next();
                            if (checkEnemyHitByPlayer(u, b)) {
                                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.ENEMY_HIT);
                                u.setAlive(false);
                                player.increaseScore(u.getScoreValue());
                                ufoShipsIterator.remove();
                                iterator.remove();
                                ufoDestroyed = true;
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating bullets", e);
        }
    }

    /**
     * Checks if a bullet hit an enemy.
     *
     * @param enemy The enemy object.
     * @param b     The bullet object.
     * @return True if the bullet hit the enemy, false otherwise.
     */
    private boolean checkEnemyHitByPlayer(Enemy enemy, Bullet b){
        return enemy.isAlive() && b.getHitBox().intersects(enemy.getHitBox()) && b.getShooterType() == PLAYER_SHOOTER;
    }

    /**
     * Draws bullets on the playground.
     *
     * @param g                  The Graphics object to draw on.
     * @param xPlayGroundOffset  The X offset of the playground.
     * @param yPlayGroundOffset  The Y offset of the playground.
     */
    private void drawBullets(Graphics g, int xPlayGroundOffset, int yPlayGroundOffset) {
        try {
            ArrayList<Bullet> bulletsCopy = new ArrayList<>(bullets);

            for (Bullet b : bulletsCopy) {
                if (b.isActive()) {
                    if (b.getShooterType() == ENEMY_SHOOTER)
                        drawEnemyBullets(g, xPlayGroundOffset, yPlayGroundOffset, b);
                    if (b.getShooterType() == PLAYER_SHOOTER)
                        drawPlayerBullets(g, xPlayGroundOffset, yPlayGroundOffset, b);
                } else
                    bullets.remove(b);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error drawing bullets", e);
        }
    }

    /**
     * Draws player bullets on the playground.
     *
     * @param g                  The Graphics object to draw on.
     * @param xPlayGroundOffset  The X offset of the playground.
     * @param yPlayGroundOffset  The Y offset of the playground.
     * @param b                  The bullet to draw.
     */
    private void drawPlayerBullets(Graphics g, int xPlayGroundOffset, int yPlayGroundOffset, Bullet b) {
        g.drawImage(playerBulletImage, (int) (b.getHitBox().x - xPlayGroundOffset),
                (int) (b.getHitBox().y - yPlayGroundOffset), BULLET_WIDTH, BULLET_HEIGHT, null);
//        b.drawHitBox(g, xPlayGroundOffset, yPlayGroundOffset);
    }

    /**
     * Draws enemy bullets on the playground.
     *
     * @param g                  The Graphics object to draw on.
     * @param xPlayGroundOffset  The X offset of the playground.
     * @param yPlayGroundOffset  The Y offset of the playground.
     * @param b                  The bullet to draw.
     */
    private void drawEnemyBullets(Graphics g, int xPlayGroundOffset, int yPlayGroundOffset, Bullet b){
        g.drawImage(enemyBulletImage, (int) (b.getHitBox().x  - xPlayGroundOffset),
                (int) (b.getHitBox().y  - yPlayGroundOffset), BULLET_WIDTH, BULLET_HEIGHT, null);
//        b.drawHitBox(g, xPlayGroundOffset, yPlayGroundOffset);
    }

    /**
     * Checks if active coin hitBox intersects with player.
     *
     * @param hitBox                  Object hitBox, to check coin collision.
     */
    public void checkCoinTouched(Rectangle2D.Float hitBox){
        for(Coin coin: coins)
            if(coin.isActive)
                if(hitBox.intersects(coin.getHitBox())){
                    playing.getGame().getAudioPlayer().playEffect(AudioPlayer.COIN);
                    coin.setActive(false);
                    coins.remove(coin);
                    increaseMoney(coin);
                    return;
                }
    }

    /**
     * Respawns the alien ship.
     */
    private void respawnAlienShip(){
        enemyManager.generateAlienShip();
    }

    /**
     * Respawns the UFO ship.
     */
    private void respawnUfoShip(){
        enemyManager.generateUfoShip();
    }

    /**
     * Resets every game object (coins, bullets).
     */
    public void resetAll() {
        for(Coin coin: coins)
            coin.reset();

        for(Bullet b: bullets)
            b.reset();

        createCoins();
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
