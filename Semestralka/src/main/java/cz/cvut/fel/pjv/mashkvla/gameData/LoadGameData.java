package cz.cvut.fel.pjv.mashkvla.gameData;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.pjv.mashkvla.gameStates.Playing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.LOGGER;

public class LoadGameData {

    /**
     * Loads the game data from a file.
     *
     * @param filePath the path of the file to load the game data from
     * @return the loaded GameData object, or null if loading failed
     * @throws IOException if an error occurs while loading the game data
     */
    public static GameData loadGame(String filePath) throws IOException {
        String jsonData = Files.readString(Paths.get(filePath));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonData, GameData.class);
    }

    /**
     * Loads the information from the loaded GameData object into the Playing state.
     *
     * @param gd      the loaded GameData object
     * @param playing the Playing state
     */
    public static void loadInfoFromFile(GameData gd, Playing playing) {
        playing.getPlayer().setHighScore(gd.getPlayerHighScore());
        playing.getPlayer().setShipLvl(gd.getPlayerLevel());
        playing.getPlayer().setMaxAmountOfHearts(gd.getPlayerHealth());
        playing.getPlayer().setPlayerShotDelay(gd.getFireSpeed());
        playing.getPlayer().setCollectedMoney(gd.getPlayerMoney());
    }

    /**
     * Logs an error message related to loading game data.
     *
     * @param message the error message to log
     * @param e       the exception associated with the error
     */
    private static void logError(String message, Exception e) {
        LOGGER.getLogger(LoadGameData.class.getName()).log(Level.SEVERE, message, e);
    }
}