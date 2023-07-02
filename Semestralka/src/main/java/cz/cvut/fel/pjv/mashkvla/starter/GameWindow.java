package cz.cvut.fel.pjv.mashkvla.starter;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * The game window that contains the game panel.
 */
public class GameWindow {

    private JFrame jFrame;

    /**
     * Constructs a new GameWindow object.
     *
     * @param gamePanel The GamePanel object.
     */
    public GameWindow(GamePanel gamePanel) {
        this.jFrame = new JFrame();

        this.jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        this.jFrame.add(gamePanel);
        this.jFrame.setResizable(false);
        //With this method we are telling to JFrame to fit the whole content in preferred size
        this.jFrame.pack();
        this.jFrame.setLocationRelativeTo(null);
        this.jFrame.setVisible(true);
        this.jFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
        });
    }
}
