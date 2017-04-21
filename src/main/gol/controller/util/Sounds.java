package main.gol.controller.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * This class handles audio files to be triggered by GUI action events.
 * Sounds downloaded from www.freeSound.org
 *
 * @version 1.0
 */
public class Sounds {

    private final File laser = new File("resources/laser.wav");
    private final File beep1 = new File("resources/beep1.wav");
    private final File beep2 = new File("resources/beep2.wav");
    private final File rattle = new File("resources/rattle.wav");

    private double vol = 0.2;


    /**
     * This methods plays a audio clip from MediaPlayer and takes a file as parameter
     *
     * @param sound File
     */
    public void play(File sound) {

        try {
            MediaPlayer audio = new MediaPlayer(
                    new Media(
                            new File(String.valueOf(sound)).toURI().toString()));
            audio.setVolume(vol);
            audio.play();

        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    /**
     * This method sets the volume of the file playing through mediaPlayer
     *
     * @param v double
     */
    public void setVol(double v) {

        this.vol = v;
    }

    // Getters
    public File getLaser() {
        return laser;
    }

    public File getBeep2() {
        return beep2;
    }

    public File getBeep1() {
        return beep1;
    }

    public File getRattle() {
        return rattle;
    }
}
