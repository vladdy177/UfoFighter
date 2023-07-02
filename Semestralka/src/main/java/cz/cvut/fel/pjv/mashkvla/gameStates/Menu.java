package cz.cvut.fel.pjv.mashkvla.gameStates;

import cz.cvut.fel.pjv.mashkvla.audio.AudioPlayer;
import cz.cvut.fel.pjv.mashkvla.starter.Game;
import cz.cvut.fel.pjv.mashkvla.ui.MenuButton;
import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static cz.cvut.fel.pjv.mashkvla.utils.ImageManager.s_getFont;

public class Menu extends State implements StateMethods {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage menuImage, menuBackground;
    private int menuX, menuY, menuWidth, menuHeight;
    private Font font;

    /**
     * Constructs a new Menu object.
     *
     * @param game the Game object
     * @throws IOException           if an I/O error occurs
     * @throws FontFormatException  if an error occurs during font formatting
     */
    public Menu(Game game) throws IOException, FontFormatException {
        super(game);
        font = s_getFont(ImageManager.FONT);
        loadButtons();
        loadMenuImage();
        menuBackground = ImageManager.s_getSpriteAtlas(ImageManager.MENU_BACKGROUND);
    }

    private void loadMenuImage() {
        menuImage = ImageManager.s_getSpriteAtlas(ImageManager.MENU);
        menuWidth = (int)(menuImage.getWidth() * Game.SCALE);
        menuHeight = (int)(menuImage.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int)(90 * Game.SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (180 *Game.SCALE), 0, GameState.SHOP);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (250 *Game.SCALE), 1, GameState.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (320 *Game.SCALE), 2, GameState.QUIT);
    }

    @Override
    public void update() {
        for(MenuButton mb : buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        int gameNameY = (int)(70 * Game.SCALE);
        int gameNameX = (Game.GAME_WIDTH / 5);

        g.drawImage(menuBackground,0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        g.setFont(font.deriveFont(Font.BOLD, 65F));
        g.setColor(Color.WHITE);
        g.drawString("UFO FIGHTER", gameNameX, gameNameY);

        g.drawImage(menuImage, menuX, menuY, menuWidth, menuHeight, null);

        for(MenuButton mb : buttons)
            mb.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb : buttons) {
            if(isInButton(e, mb))
                mb.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) throws IOException {
        for (MenuButton mb : buttons) {
            if (isInButton(e, mb)) {
                if(mb.getState() == GameState.SHOP)
                    game.loadGame();
                if (mb.isMousePressed())
                    mb.applyGameState();
                if(mb.gameState() == GameState.PLAYING)
                    game.getAudioPlayer().playMusic(AudioPlayer.PLAYING);
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for(MenuButton mb : buttons)
            mb.resetBooleans();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton mb : buttons)
            mb.setMouseOver(false);

        for(MenuButton mb : buttons)
            if(isInButton(e, mb)){
                mb.setMouseOver(true);
                break;
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
