package cz.cvut.fel.pjv.mashkvla.gameStates;

/**
 * Enum representing different game states.
 */
public enum GameState {

    PLAYING, // Game is currently in the playing state
    MENU, // Game is currently in the menu state
    OPTIONS, // Game is currently in the options state
    QUIT, // Game is currently in the quit state
    SHOP; // Game is currently in the shop state

    /**
     * First game state.
     */
    public static GameState state = MENU;
}

