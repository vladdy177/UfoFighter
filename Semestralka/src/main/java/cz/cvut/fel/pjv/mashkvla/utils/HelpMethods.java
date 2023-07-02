package cz.cvut.fel.pjv.mashkvla.utils;

import cz.cvut.fel.pjv.mashkvla.entities.AlienShip;
import cz.cvut.fel.pjv.mashkvla.entities.Ufo;
import cz.cvut.fel.pjv.mashkvla.objects.Bullet;
import cz.cvut.fel.pjv.mashkvla.objects.Coin;
import cz.cvut.fel.pjv.mashkvla.starter.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.LOGGER;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.EnemyConstants.ALIEN;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.EnemyConstants.UFO;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.ObjectConstants.COIN;

/**
 * Contains helper methods used in the game.
 */
public class HelpMethods {

    /**
     * Checks if an object can move to the specified position without collision.
     *
     * @param x        The x-coordinate of the position.
     * @param y        The y-coordinate of the position.
     * @param width    The width of the object.
     * @param height   The height of the object.
     * @param playgroundData  The playground data array representing the playground layout.
     * @return True if the object can move to the position without collision, false otherwise.
     */
    public static Boolean s_canMoveHere(float x, float y, float width, float height, int[][] playgroundData){
        // Check if the corners of the object do not collide with solid tiles
        if(!s_isSolid(x,y, playgroundData))
            if(!s_isSolid(x + width,y + height, playgroundData))
                if(!s_isSolid(x + width,y, playgroundData))
                    if(!s_isSolid(x,y + height, playgroundData))
                        return true;
        return false;

    }

    /**
     * Checks if the specified position is a solid tile.
     *
     * @param x        The x-coordinate of the position.
     * @param y        The y-coordinate of the position.
     * @param playgroundData  The playground data array representing the playground layout.
     * @return True if the position is a solid tile, false otherwise.
     */
    private static Boolean s_isSolid(float x, float y, int[][] playgroundData){
        int maxWidth = playgroundData[0].length * Game.TILES_SIZE;
        int maxHeight = playgroundData.length * Game.TILES_SIZE;

        // Check if the position is outside the playground boundaries
        if(x < 0 || x >= maxWidth)
            return true;
        if(y < 0 || y >= maxHeight)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        // Check if the tile is a solid tile
        int value;
        try {
            value = playgroundData[(int) yIndex][(int) xIndex];
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, "Error accessing playground data: ", e);
            return true;
        }

        // Check if the tile is a solid tile
        if (value >= 48 || value < 2) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the bullet has hit the borders of the playground.
     *
     * @param bullet         The bullet to check.
     * @param playgroundData The playground data array representing the playground layout.
     * @return True if the bullet has hit the playground border, false otherwise.
     */
    public static boolean s_bulletHitPlaygroundBorder(Bullet bullet, int[][] playgroundData){
        return s_isSolid((int)(bullet.getHitBox().x + bullet.getHitBox().width / 2), (int)(bullet.getHitBox().y + bullet.getHitBox().height / 2), playgroundData);
    }

    /**
     * Finds the spawn point of the player in the playground.
     *
     * @param image The image representing the playground.
     * @return The spawn point of the player as a Point object.
     */
    public static Point s_getPlayerSpawn(BufferedImage image){
        for (int i = 0; i < image.getHeight(); i++)
            for (int j = 0; j < image.getWidth(); j++){
                {Color color = new Color(image.getRGB(j, i));
                    int value = color.getGreen();
                    if(value == 255)
                        return new Point(j * Game.TILES_SIZE, i * Game.TILES_SIZE);
            }
        }
        return new Point(Game.TILES_SIZE, Game.TILES_SIZE);
    }

    /**
     * Retrieves the positions of alien ships in the playground.
     *
     * @param image The image representing the playground.
     * @return The list of AlienShip objects representing the positions of alien ships.
     */
    public static ArrayList<AlienShip> s_getAlienShips(BufferedImage image){
        ArrayList<AlienShip> list = new ArrayList<>();
        for(int j = 0; j < image.getHeight(); j++)
            for(int i = 0; i < image.getWidth(); i++){
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if(value == ALIEN)
                    list.add(new AlienShip(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        return list;
    }

    /**
     * Retrieves the positions of UFO ships in the playground.
     *
     * @param image The image representing the playground.
     * @return The list of Ufo objects representing the positions of UFO ships.
     */
    public static ArrayList<Ufo> s_getUfoShips(BufferedImage image){
        ArrayList<Ufo> list = new ArrayList<>();
        for(int j = 0; j < image.getHeight(); j++)
            for(int i = 0; i < image.getWidth(); i++){
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if(value == UFO)
                    list.add(new Ufo(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        return list;
    }

    /**
     * Retrieves the positions of coins in the playground.
     *
     * @param image The image representing the playground.
     * @return The list of Coin objects representing the positions of coins.
     */
    public static ArrayList<Coin> s_getCoins(BufferedImage image){
        ArrayList<Coin> list = new ArrayList<>();
        for(int j = 0; j < image.getHeight(); j++)
            for(int i = 0; i < image.getWidth(); i++){
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if(value == COIN)
                    list.add(new Coin(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        return list;
    }

}
