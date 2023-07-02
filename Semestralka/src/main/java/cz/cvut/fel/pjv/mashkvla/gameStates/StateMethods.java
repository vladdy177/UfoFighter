package cz.cvut.fel.pjv.mashkvla.gameStates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * The interface defining the methods required for a game state.
 */
public interface StateMethods {

    /**
     * Updates the state of the game.
     */
    public void update();

    /**
     * Draws the state on the graphics object.
     *
     * @param g The graphics object to draw on.
     */
    public void draw(Graphics g);

    /**
     * Handles the mouse pressed event.
     *
     * @param e The MouseEvent object representing the event.
     */
    public void mousePressed(MouseEvent e);

    /**
     * Handles the mouse released event.
     *
     * @param e The MouseEvent object representing the event.
     * @throws IOException              If there is an error during the event handling.
     * @throws ClassNotFoundException If a required class is not found during event handling.
     */
    public void mouseReleased(MouseEvent e) throws IOException, ClassNotFoundException;

    /**
     * Handles the mouse moved event.
     *
     * @param e The MouseEvent object representing the event.
     */
    public void mouseMoved(MouseEvent e);

    /**
     * Handles the key pressed event.
     *
     * @param e The KeyEvent object representing the event.
     */
    public void keyPressed(KeyEvent e);

    /**
     * Handles the key released event.
     *
     * @param e The KeyEvent object representing the event.
     */
    public void keyReleased(KeyEvent e);
}

