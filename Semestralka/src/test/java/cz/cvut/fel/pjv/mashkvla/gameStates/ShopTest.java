package cz.cvut.fel.pjv.mashkvla.gameStates;

import cz.cvut.fel.pjv.mashkvla.starter.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopTest {
    private Game game;
    private Shop shop;

    @BeforeEach
    void setUp() {
        try {
            Game mockGame = mock(Game.class);
            shop = new Shop(mockGame);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testIsInPauseButton() {
        MouseEvent event = mock(MouseEvent.class);
        int x = Game.GAME_WIDTH - 100;
        int y = Game.GAME_HEIGHT - (int)(Game.SCALE * 70);
        when(event.getY()).thenReturn(y);

        boolean result = shop.isInPauseButton(event, shop.getReadyToPlayB());

        assertTrue(result);
    }

    @Test
    public void testIsInUpgradeButton() {
        MouseEvent event = mock(MouseEvent.class);
        int x = Game.GAME_WIDTH - 145;
        int y = Game.GAME_HEIGHT / 3 - 90;
        when(event.getX()).thenReturn(x);
        when(event.getY()).thenReturn(y);

        boolean result = shop.isInUpgradeButton(event, shop.getUpgradeHealthButton());

        assertTrue(result);
    }
}

