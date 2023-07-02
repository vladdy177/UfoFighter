package cz.cvut.fel.pjv.mashkvla.ui;

import cz.cvut.fel.pjv.mashkvla.gameStates.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.Buttons.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

class MenuButtonTest {
    private MenuButton menuButton;

    @BeforeEach
    void setUp() {
        int xPos = 100;
        int yPos = 200;
        int rowIndex = 0;
        GameState state = GameState.MENU;

        menuButton = new MenuButton(xPos, yPos, rowIndex, state);
    }

    @Test
    void initBounds() {
        Rectangle expectedBounds = new Rectangle(100 - B_WIDTH / 2, 200, B_WIDTH, B_HEIGHT);
        Rectangle actualBounds = menuButton.getBounds();

        assertEquals(expectedBounds, actualBounds);
    }

    @Test
    void update_mouseOver() {
        menuButton.setMouseOver(true);
        menuButton.update();

        assertTrue(menuButton.isMouseOver());
        assertFalse(menuButton.isMousePressed());
        assertEquals(1, menuButton.getIndex());
    }

    @Test
    void update_mousePressed() {
        menuButton.setMousePressed(true);
        menuButton.update();

        assertFalse(menuButton.isMouseOver());
        assertTrue(menuButton.isMousePressed());
        assertEquals(2, menuButton.getIndex());
    }

    @Test
    void update_default() {
        menuButton.update();

        assertFalse(menuButton.isMouseOver());
        assertFalse(menuButton.isMousePressed());
        assertEquals(0, menuButton.getIndex());
    }

    @Test
    void applyGameState() {
        menuButton.applyGameState();

        assertEquals(GameState.MENU, GameState.state);
    }

    @Test
    void resetBooleans() {
        menuButton.setMouseOver(true);
        menuButton.setMousePressed(true);

        menuButton.resetBooleans();

        assertFalse(menuButton.isMouseOver());
        assertFalse(menuButton.isMousePressed());
    }

    @Test
    void getState() {
        GameState expectedState = GameState.MENU;
        GameState actualState = menuButton.getState();

        assertEquals(expectedState, actualState);
    }

    @Test
    void gameState() {
        GameState expectedState = GameState.MENU;
        GameState actualState = menuButton.gameState();

        assertEquals(expectedState, actualState);
    }
}
