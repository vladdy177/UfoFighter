package cz.cvut.fel.pjv.mashkvla.ui;

import cz.cvut.fel.pjv.mashkvla.starter.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.PauseButtons.SOUND_B_SIZE;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.VolumeButtons.VOLUME_HEIGHT;

/**
 * Manages the audio controls in the game.
 */
public class AudioControls {

    private SoundButton musicButton, sfxButton;
    private VolumeButton volumeButton;
    private Game game;

    /**
     * Constructs a new AudioControls object.
     *
     * @param game The Game object.
     */
    public AudioControls(Game game) {
        this.game = game;
        createSoundButtons();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int volumeButtonX = (int)(309 * Game.SCALE);
        int volumeButtonY = (int)(278 * Game.SCALE);
        volumeButton = new VolumeButton(volumeButtonX, volumeButtonY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createSoundButtons() {
        int soundX = (int)(450 * Game.SCALE);
        int musicY = (int)(143 * Game.SCALE);
        int sfxY = (int)(186 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_B_SIZE, SOUND_B_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_B_SIZE, SOUND_B_SIZE);
    }

    /**
     * Updates the state of the audio controls.
     */
    public void update(){
        musicButton.update();
        sfxButton.update();

        volumeButton.update();
    }

    /**
     * Draws the audio controls on the screen.
     *
     * @param g The Graphics object.
     */
    public void draw(Graphics g){
        //Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        //Volume slider
        volumeButton.draw(g);
    }

    /**
     * Handles the mouse dragged event.
     *
     * @param e The MouseEvent object.
     */
    public void mouseDragged(MouseEvent e){
        if(volumeButton.isMousePressed()){
            float valueBefore = volumeButton.getFloatValue();
            volumeButton.changeX(e.getX());
            float valueAfter = volumeButton.getFloatValue();
            if(valueBefore != valueAfter)
                game.getAudioPlayer().setVolume(valueAfter);
        }

    }

    /**
     * Handles the mouse pressed event.
     *
     * @param e The MouseEvent object.
     */
    public void mousePressed(MouseEvent e) {
        if(isInButton(e, musicButton))
            musicButton.setMousePressed(true);
        else if(isInButton(e, sfxButton))
            sfxButton.setMousePressed(true);
        else if(isInButton(e, volumeButton))
            volumeButton.setMousePressed(true);
    }

    /**
     * Handles the mouse released event.
     *
     * @param e The MouseEvent object.
     */
    public void mouseReleased(MouseEvent e) {
        if(isInButton(e, musicButton)){
            if(musicButton.isMousePressed()){
                musicButton.setMuted(!musicButton.isMuted());
                game.getAudioPlayer().toggleMusicMute();
            }
        }
        else if(isInButton(e, sfxButton)){
            if(sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
                game.getAudioPlayer().toggleEffectMute();
            }
        }

        musicButton.resetBooleans();
        sfxButton.resetBooleans();
        volumeButton.resetBooleans();
    }

    /**
     * Handles the mouse moved event.
     *
     * @param e The MouseEvent object.
     */
    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if(isInButton(e, musicButton))
            musicButton.setMouseOver(true);
        else if(isInButton(e, sfxButton))
            sfxButton.setMouseOver(true);
        else if(isInButton(e, volumeButton))
            volumeButton.setMouseOver(true);
    }

    private boolean isInButton(MouseEvent e, PauseButton b){
        return b.getBounds().contains(e.getX(), e.getY());
    }

}
