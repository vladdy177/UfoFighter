package cz.cvut.fel.pjv.mashkvla.starter;

import cz.cvut.fel.pjv.mashkvla.audio.AudioPlayer;
import cz.cvut.fel.pjv.mashkvla.gameStates.*;
import cz.cvut.fel.pjv.mashkvla.gameStates.Menu;
import cz.cvut.fel.pjv.mashkvla.ui.AudioControls;
import cz.cvut.fel.pjv.mashkvla.gameData.GameData;
import cz.cvut.fel.pjv.mashkvla.gameData.LoadGameData;
import cz.cvut.fel.pjv.mashkvla.gameData.SaveGameData;

import java.awt.*;
import java.io.*;
import java.util.logging.Logger;

/**
 * I will put and add everything together in this class
 */
public class Game implements Runnable{

    public static final Logger LOGGER = Logger.getLogger(Game.class.getName());

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Options options;
    private Shop shop;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;
    private AudioControls audioControls;
    private AudioPlayer audioPlayer;

    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 1.5f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = (TILES_SIZE * TILES_IN_WIDTH);
    public static final int GAME_HEIGHT = (TILES_SIZE * TILES_IN_HEIGHT);

    /**
     * The main class that puts everything together and runs the game.
     */
    public Game() throws IOException, FontFormatException, ClassNotFoundException {

        initClasses();

        this.gamePanel = new GamePanel(this);
        this.gameWindow = new GameWindow(gamePanel);
        this.gamePanel.setFocusable(true);
        this.gamePanel.requestFocus();

        startGameLoop();
    }

    /**
     * Constructs a new Game instance.
     *
     * @throws IOException          If an I/O error occurs.
     * @throws FontFormatException If the font format is invalid.
     * @throws ClassNotFoundException If the class cannot be found during deserialization.
     */
    private void initClasses() throws IOException, FontFormatException, ClassNotFoundException {
        audioControls = new AudioControls(this);
        audioPlayer = new AudioPlayer();
        menu = new Menu(this);
        playing = new Playing(this);
        options = new Options(this);
        shop = new Shop(this);
    }

    /**
     * Updates the game logic.
     *
     * @throws IOException          If an I/O error occurs.
     * @throws ClassNotFoundException If the class cannot be found during deserialization.
     */
    public void update() throws IOException, ClassNotFoundException {
        switch (GameState.state){
            case MENU:
//                LOGGER.log(Level.INFO, "In the main menu");
                menu.update();
                break;
            case PLAYING:
//                LOGGER.log(Level.INFO, "Playing the game");
                playing.update();
                break;
            case OPTIONS:
//                LOGGER.log(Level.INFO, "In the options menu");
                options.update();
                break;
            case SHOP:
//                LOGGER.log(Level.INFO, "In the shop");
                shop.update();
                break;
            case QUIT:
//                LOGGER.log(Level.INFO, "Exiting the game");
            default:
                System.exit(0);
                break;
        }
    }

    /**
     * Saves the game data to a file.
     */
    public void saveGame(){
        GameData gameData = SaveGameData.addInfo(getPlaying());
        SaveGameData.saveInfoToFile(gameData, "save.json");
    }

    /**
     * Loads the game data from a file.
     */
    public void loadGame() throws IOException {
        GameData gameData = LoadGameData.loadGame("save.json");
            LoadGameData.loadInfoFromFile(gameData, getPlaying());
    }

    /**
     * Renders the game graphics.
     *
     * @param g The graphics context.
     */
    public void render(Graphics g){
        switch (GameState.state){
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            case OPTIONS:
                options.draw(g);
                break;
            case SHOP:
                shop.draw(g);
                break;
            default:
                break;
        }
    }

    private void startGameLoop(){
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    @Override
    public void run() {
        //How long each frame will last in nanosecond
        double timePerFrame = 1000000000.0 / this.FPS_SET;
        double timePerUpdate = 1000000000.0 / this.UPS_SET;

        long previousTime = System.nanoTime();

        int framesPerSecond = 0;
        int updatesPerSecond = 0;
        long lastCheckedFrame = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while(true){
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if(deltaU >= 1){
                try {
                    update();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                updatesPerSecond++;
                deltaU--;
            }
            if(deltaF >= 1){
                gamePanel.repaint();
                framesPerSecond++;
                deltaF--;
            }
             //Creating FPS counter check
            if(System.currentTimeMillis() - lastCheckedFrame >= 1000){
                lastCheckedFrame = System.currentTimeMillis();
//                System.out.println("FPS: " + framesPerSecond + " | UPS: " + updatesPerSecond);
                framesPerSecond = 0;
                updatesPerSecond = 0;
            }

        }
    }

    /**
     * Called when the game window loses focus.
     */
    public void windowFocusLost() {
        if(GameState.state == GameState.PLAYING){
            playing.getPlayer().resetDirectionBooleans();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Options getOptions() {
        return options;
    }

    public AudioControls getAudioControls() {
        return audioControls;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public Shop getShop() {
        return shop;
    }
}
