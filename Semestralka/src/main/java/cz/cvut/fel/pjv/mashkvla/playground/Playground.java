package cz.cvut.fel.pjv.mashkvla.playground;

import cz.cvut.fel.pjv.mashkvla.entities.AlienShip;
import cz.cvut.fel.pjv.mashkvla.entities.Ufo;
import cz.cvut.fel.pjv.mashkvla.objects.Coin;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static cz.cvut.fel.pjv.mashkvla.utils.HelpMethods.*;

/**
 * Represents the game playground where the game objects are placed.
 */
public class Playground {

    private BufferedImage playgroungImage;
    private int[][] playgroundData;
    private Point playerSpawn;

    /**
     * Constructs a Playground with the specified playground data and image.
     *
     * @param playgroundData The 2D array representing the playground data.
     * @param image          The image representing the playground.
     */
    public Playground(int[][] playgroundData, BufferedImage image){
        this.playgroundData = playgroundData;
        this.playgroungImage = image;
        createPLayerSpawn();

    }

    /**
     * Creates the player spawn point by searching for the player's starting position in the playground image.
     */
    private void createPLayerSpawn() {
        playerSpawn = s_getPlayerSpawn(playgroungImage);
    }

    /**
     * Returns the sprite index at the specified position in the playground.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return The sprite index at the specified position.
     */
    public int getSpriteIndex(int x, int y){
        return playgroundData[y][x];
    }

    /**
     * Returns the 2D array representing the playground data.
     *
     * @return The playground data.
     */
    public int[][] getPlaygroundData() {
        return playgroundData;
    }

    /**
     * Returns the player spawn point.
     *
     * @return The player spawn point.
     */
    public Point getPlayerSpawn(){
        return playerSpawn;
    }

}
