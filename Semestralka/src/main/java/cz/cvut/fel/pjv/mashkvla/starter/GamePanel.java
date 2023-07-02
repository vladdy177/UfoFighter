package cz.cvut.fel.pjv.mashkvla.starter;

import cz.cvut.fel.pjv.mashkvla.inputs.KeyboardInputs;
import cz.cvut.fel.pjv.mashkvla.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.GAME_HEIGHT;
import static cz.cvut.fel.pjv.mashkvla.starter.Game.GAME_WIDTH;

/**
 * The game panel where the game is rendered.
 */
public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;


    /**
     * Constructs a new GamePanel object.
     *
     * @param game The Game object.
     */
    public GamePanel(Game game) {
        this.game = game;
        mouseInputs = new MouseInputs(this);
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    /**
     * Sets the size of the game panel.
     */
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
//        System.out.println("Size: " + GAME_WIDTH + " : " + GAME_HEIGHT );
    }

    /**
     * Overrides the paintComponent method to render the game.
     *
     * @param g The Graphics object.
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);
    }

    /**
     * Gets the Game object associated with the GamePanel.
     *
     * @return The Game object.
     */
    public Game getGame(){
        return this.game;
    }
}
