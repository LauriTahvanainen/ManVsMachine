package mvsm.ui;

import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Class for encapsulating playing audio files. Everytime this class is called
 * to play audio, it loads the audio file from the resources folder, and plays
 * it. Used by the StateManager. When loading audio, the resources that were
 * being used in playing the current audiofile are freed. That is to say that
 * the player can only handle one audio file at once.
 *
 * @see mvsm.statemanagement.StateManager
 */
public class SoundPlayer {

    private MediaPlayer player;
    private final String PATH = "/audio/";
    private final String[] menuSongs;
    private final String[] playingSongs;

    public SoundPlayer() {
        this.menuSongs = new String[3];
        this.player = new MediaPlayer(new Media(SoundPlayer.class.getResource(PATH + "login.wav").toString()));
        this.playingSongs = new String[3];
        for (int i = 1; i < 4; i++) {
            this.menuSongs[i - 1] = "menu" + i;
            this.playingSongs[i - 1] = "playing" + i;
        }
    }

    /**
     * Play the audio for the LoginState in an infinite loop until some other
     * audio file is called to be played.
     */
    public void playLogin() {
        loadAudio("login");
        this.player.setCycleCount(player.INDEFINITE);
        this.player.play();
    }

    /**
     * Method for loading an audiofile from the memory. Creates a new
     * MediaPlayer object.
     *
     * @param name The name of the audio file as a String. For example playing1.
     */
    public void loadAudio(String name) {
        this.player.dispose();
        this.player = new MediaPlayer(new Media(SoundPlayer.class.getResource(PATH + name + ".wav").toString()));
    }

    /**
     * Plays a random menu song. When the song ends, plays another. In other
     * words, loops until stopped.
     */
    public void playRandomMenu() {
        Random rnd = new Random();
        loadAudio(this.menuSongs[rnd.nextInt(3)]);
        this.player.setOnEndOfMedia(() -> {
            playRandomMenu();
        });
        this.player.play();
    }

    /**
     * Plays a random playing song. When the song ends, plays another. In other
     * words, loops until stopped.
     */
    public void playRandomPlaying() {
        Random rnd = new Random();
        loadAudio(this.playingSongs[rnd.nextInt(3)]);
        this.player.setOnEndOfMedia(() -> {
            playRandomPlaying();
        });
        this.player.play();
    }

    /**
     * Play the current song.
     */
    public void play() {
        this.player.play();
    }

    /**
     * Pause the current song.
     */
    public void pause() {
        this.player.pause();
    }

}
