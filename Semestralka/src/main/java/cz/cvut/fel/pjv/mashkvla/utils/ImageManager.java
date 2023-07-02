package cz.cvut.fel.pjv.mashkvla.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.LOGGER;

public class ImageManager {

    public static final String PLAYER_ATLAS = "player/player_sprite_atlas.png";
    public static final String HEART = "player/heart.png";
    public static final String COIN = "player/coin.png";
    public static final String PLAYER_BULLET = "player/fire_player.png";

    public static final String MENU_BUTTONS = "ui/button_atlas.png";
    public static final String MENU = "ui/menu.png";
    public static final String PAUSE_MENU = "ui/pause_menu.png";
    public static final String OPTIONS_MENU = "ui/options_menu.png";
    public static final String SOUND_BUTTONS = "ui/sound_button.png";
    public static final String URM_BUTTONS = "ui/urm_buttons.png";
    public static final String VOLUME = "ui/volume_buttons.png";
    public static final String DEAD_BACKGROUND_IMG = "ui/deadScreen.png";

    public static final String PLAYING_BACKGROUND = "backgrounds/playing_background.png";
    public static final String PLAYING_BACKGROUND_STARS = "backgrounds/playing_background_stars.png";
    public static final String PLAYING_BACKGROUND_RING_PLANET = "backgrounds/playing_background_ring_planet.png";
    public static final String PLAYING_BACKGROUND_BIG_PLANET = "backgrounds/playing_background_big_planet.png";
    public static final String PLAYING_BACKGROUND_FAR_PLANETS = "backgrounds/playing_background_far_planets.png";
    public static final String MENU_BACKGROUND = "backgrounds/menu_background.png";
    public static final String SHOP_BACKGROUND = "backgrounds/shop_background.png";

    public static final String PLAYGROUND_ATLAS = "playground/spaceBorder2.png";
    public static final String PLAYGROUND_DATA = "playground/playground_data.png";

    public static final String ALIEN_SHIP = "enemies/alien_sprite_atlas.png";
    public static final String UFO = "enemies/ufo_sprite_atlas.png";
    public static final String ENEMY_BULLET = "enemies/fire_enemy.png";

    public static final String FONT = "kongtext.ttf";

    /**
     * Retrieves a font from the specified file.
     *
     * @param fileName the name of the font file
     * @return the Font object created from the file
     * @throws IOException           if there is an error reading the font file
     * @throws FontFormatException  if the font file has an invalid format
     */
    public static Font s_getFont(String fileName) throws IOException, FontFormatException {
        InputStream is = ImageManager.class.getResourceAsStream("/" + fileName);
        try {
            return Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            LOGGER.log(Level.SEVERE, "Error loading font: " + fileName, e);
            throw e;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Error closing font file input stream.", e);
            }
        }
    }

    /**
     * Retrieves a sprite atlas image from the specified file.
     *
     * @param fileName the name of the sprite atlas file
     * @return the BufferedImage representing the sprite atlas
     */
    public static BufferedImage s_getSpriteAtlas(String fileName) {
        BufferedImage image = null;
        InputStream is = ImageManager.class.getResourceAsStream("/" + fileName);
        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading sprite atlas: " + fileName, e);
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Error closing sprite atlas file input stream.", e);
            }
        }
        return image;
    }

    /**
     * Retrieves the playground data as a two-dimensional array from the playground data image.
     *
     * @return the two-dimensional array representing the playground data
     */
    public static int[][] s_getPlaygroundData() {
        BufferedImage image = s_getSpriteAtlas(PLAYGROUND_DATA);
        int[][] playGroundData = new int[image.getHeight()][image.getWidth()];

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getRed();
                if (value >= 7) {
                    value = 2;
                }
                playGroundData[j][i] = value;
            }
        }
        return playGroundData;
    }
}

