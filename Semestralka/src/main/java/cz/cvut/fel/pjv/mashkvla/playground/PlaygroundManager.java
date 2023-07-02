package cz.cvut.fel.pjv.mashkvla.playground;

import cz.cvut.fel.pjv.mashkvla.starter.Game;
import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import static cz.cvut.fel.pjv.mashkvla.starter.Game.TILES_SIZE;

/**
 * Manages the playground in the game.
 */
public class PlaygroundManager {

    private Game game;
    private BufferedImage[] playgroundSprite;
    private Playground playgroundData;

    /**
     * Constructs a PlaygroundManager with the specified game.
     *
     * @param game The game instance.
     */
    public PlaygroundManager(Game game){
        this.game = game;
        importPlaygroundSprite();
        playgroundData = new Playground(ImageManager.s_getPlaygroundData(), ImageManager.s_getSpriteAtlas(ImageManager.PLAYGROUND_DATA));
    }

    /**
     * Imports the playground sprites from the sprite atlas.
     */
    public void importPlaygroundSprite() {
        BufferedImage image = ImageManager.s_getSpriteAtlas(ImageManager.PLAYGROUND_ATLAS);
        playgroundSprite = new BufferedImage[7];
            for (int i = 0; i < 7; i++) {
                playgroundSprite[i] = image.getSubimage(i * 32, 0, 32, 32);
            }
    }

    /**
     * Draws the playground on the graphics context.
     *
     * @param g           The graphics context.
     * @param xPlayGroundOffset  The horizontal offset for scrolling.
     * @param yPlayGroundOffset  The vertical offset for scrolling.
     */
    public void draw(Graphics g, int xPlayGroundOffset, int yPlayGroundOffset){
        for(int j = 0; j < playgroundData.getPlaygroundData().length; j++)
            for(int i = 0; i < playgroundData.getPlaygroundData()[0].length; i++){
                int index = playgroundData.getSpriteIndex(i, j);
                g.drawImage(playgroundSprite[index], TILES_SIZE * i - xPlayGroundOffset,TILES_SIZE * j - yPlayGroundOffset, TILES_SIZE, TILES_SIZE, null);
            }
    }

    /**
     * Returns the playground data.
     *
     * @return The playground data.
     */
    public Playground getPlaygroundData(){
        return playgroundData;
    }

    public BufferedImage[] getPlaygroundSprite() {
        return playgroundSprite;
    }
}
