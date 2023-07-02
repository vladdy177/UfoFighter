package cz.cvut.fel.pjv.mashkvla.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;

import static cz.cvut.fel.pjv.mashkvla.starter.Game.LOGGER;

/**
 * The AudioPlayer class handles the playback of music and sound effects in the game.
 */
public class AudioPlayer {

    // MUSIC
    public static int MENU = 0;
    public static int PLAYING = 1;
    // SOUND EFFECTS
    public static int PLAYER_SHOT_1 = 0;
    public static int PLAYER_SHOT_2 = 1;
    public static int PLAYER_BOOM = 2;
    public static int PLAYER_HIT = 3;
    public static int ENEMY_HIT = 4;
    public static int COIN = 5;
    public static int GAMEOVER = 6;

    private Clip[] music;
    private Clip[] effects;
    private int currentMusicId;
    private float volume = 0.8f;
    private boolean musicMute;
    private boolean sfxMute;
    private Random random = new Random();

    /**
     * Constructs an AudioPlayer object and initializes the music and sound effects.
     * It also starts playing the menu music by default.
     */
    public AudioPlayer() {
        loadMusic();
        loadEffects();
        playMusic(MENU);
    }

    private void loadMusic() {
        String[] names = {"mainMenu", "playing"};
        this.music = new Clip[names.length];
        for (int i = 0; i < this.music.length; i++) {
            this.music[i] = getClip(names[i]);
        }
    }

    private void loadEffects() {
        String[] effectNames = {"playerShot", "playerShot2", "playerBoom", "playerHit", "enemyHit", "coin", "gameover"};
        this.effects = new Clip[effectNames.length];
        for (int i = 0; i < this.effects.length; i++) {
            this.effects[i] = getClip(effectNames[i]);
        }

        updateEffectsVolume();
    }

    /**
     * Loads an audio clip from the given file path.
     *
     * @param path the file path of the audio clip
     * @return the loaded audio clip
     * @throws RuntimeException if an error occurs while loading the clip
     */
    private Clip getClip(String path) {
        URL url = getClass().getResource("/audio/" + path + ".wav");
        AudioInputStream audioInputStream;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (UnsupportedAudioFileException e) {
            LOGGER.log(Level.SEVERE, "Unsupported audio file format: " + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading audio file: " + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            LOGGER.log(Level.SEVERE, "Line unavailable for audio playback: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the volume for both music and sound effects.
     *
     * @param volume the volume value (ranging from 0.0 to 1.0)
     */
    public void setVolume(float volume) {
        this.volume = volume;
        updateMusicVolume();
        updateEffectsVolume();
    }

    /**
     * Stops the currently playing music.
     */
    public void stopMusic() {
        if (this.music[currentMusicId].isActive())
            this.music[currentMusicId].stop();
    }

    /**
     * Plays the random sound effect for the player shot.
     */
    public void playPlayerShotSound() {
        int firstPosition = 0;
        firstPosition += random.nextInt(2);
        playEffect(firstPosition);
    }

    /**
     * Plays the specified sound effect.
     *
     * @param effect the index of the sound effect to be played
     */
    public void playEffect(int effect) {
        this.effects[effect].setMicrosecondPosition(0);
        this.effects[effect].start();
    }

    /**
     * Plays the specified music.
     *
     * @param music the index of the music to be played
     */
    public void playMusic(int music) {
        stopMusic();

        currentMusicId = music;
        updateMusicVolume();
        this.music[currentMusicId].setMicrosecondPosition(0);
        this.music[currentMusicId].loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Toggles the mute state of the music.
     */
    public void toggleMusicMute() {
        this.musicMute = !musicMute;
        for (Clip c : this.music) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(musicMute);
        }
    }

    /**
     * Toggles the mute state of the sound effects.
     */
    public void toggleEffectMute() {
        this.sfxMute = !sfxMute;
        for (Clip c : effects) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(sfxMute);
        }
        if (!sfxMute)
            playEffect(COIN);
    }

    private void updateMusicVolume() {
        for (Clip c : this.music) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * this.volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

    private void updateEffectsVolume() {
        for (Clip c : this.effects) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * this.volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
}
