package cz.cvut.fel.pjv.mashkvla.gameData;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.pjv.mashkvla.gameStates.Playing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.LOGGER;

public class SaveGameData {

    /**
     * Saves the game data to a file.
     *
     * @param gameData  the GameData object to save
     * @param filePath  the path of the file to save the game data to
     */
    public static void saveInfoToFile(GameData gameData, String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(gameData);
            Files.writeString(Paths.get(filePath), jsonData);
        } catch (IOException e) {
            logError("Failed to save game data to file: " + filePath, e);
        }
    }

    /**
     * Creates a new GameData object and populates it with information from the Playing state.
     *
     * @param playing  the Playing state
     * @return the populated GameData object
     */
    public static GameData addInfo(Playing playing) {
        GameData gameData = new GameData();

        int userLevel = playing.getPlayer().getShipLvl();
        int userHighScore = playing.getPlayer().getHighScore();
        int userMoney = playing.getPlayer().getCollectedMoney();
        int userHealth = playing.getPlayer().getMaxAmountOfHearts();
        int userFireSpeed = playing.getPlayer().getPlayerShotDelay();

        gameData.setPlayerHighScore(userHighScore);
        gameData.setPlayerLevel(userLevel);
        gameData.setPlayerMoney(userMoney);
        gameData.setPlayerHealth(userHealth);
        gameData.setPlayerFireSpeed(userFireSpeed);

        return gameData;
    }

    /**
     * Logs an error message related to saving game data.
     *
     * @param message  the error message to log
     * @param e  the exception associated with the error
     */
    private static void logError(String message, Exception e) {
        LOGGER.getLogger(SaveGameData.class.getName()).log(Level.SEVERE, message, e);
    }
}