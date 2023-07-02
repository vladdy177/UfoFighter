package cz.cvut.fel.pjv.mashkvla.gameStates;

import cz.cvut.fel.pjv.mashkvla.audio.AudioPlayer;
import cz.cvut.fel.pjv.mashkvla.starter.Game;
import cz.cvut.fel.pjv.mashkvla.ui.MenuButton;

import java.awt.event.MouseEvent;

/**
 * The base class for game states.
 */
public class State {
    protected Game game;

    /**
     * Constructs a new State object.
     *
     * @param game The Game object associated with the state.
     */
    public State(Game game) {
        this.game = game;
    }

    /**
     * Checks if the mouse is in a specific button.
     *
     * @param e  The MouseEvent object representing the mouse event.
     * @param mb The MenuButton to check against.
     * @return true if the mouse is in the button, false otherwise.
     */
    public boolean isInButton(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    /**
     * Sets the game state to the specified value.
     *
     * @param gameState The new game state.
     */
    public void setGameState(GameState gameState) {
        switch (gameState) {
            case MENU:
                game.getAudioPlayer().playMusic(AudioPlayer.MENU);
                break;
            case PLAYING:
                game.getAudioPlayer().playMusic(AudioPlayer.PLAYING);
                break;
        }
        GameState.state = gameState;
    }

    /**
     * Returns the Game object associated with the state.
     *
     * @return The Game object.
     */
    public Game getGame() {
        return game;
    }
}
