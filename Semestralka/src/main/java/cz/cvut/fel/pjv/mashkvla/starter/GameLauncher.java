package cz.cvut.fel.pjv.mashkvla.starter;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.LOGGER;

/**
 * The main class to launch the game.
 */
public class GameLauncher {

    /**
     * The main entry point of the game.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        try {
            new Game();
        } catch (IOException | FontFormatException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error launching the game", e);
        }
    }
}

