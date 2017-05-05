package main.gol.controller.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import static java.lang.String.*;

/**
 * This class handles audio files triggered by GUI action events.
 * Contains filepath for each audio clip used in this application and
 * method for playing the audio files.
 * Sound downloaded from www.freeSound.org under Creative common licence.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.2
 */
public class Sound {

    // Volume
    private double vol = 0.1;

    // Audio files
    private final File pop1 = new File("resources/sounds/pop1.wav");
    private final File pop2 = new File("resources/sounds/pop2.wav");
    private final File pop3 = new File("resources/sounds/pop3.wav");
    private final File fx2 = new File("resources/sounds/fx2.wav");
    private final File fx3 = new File("resources/sounds/fx3.wav");
    private final File fx8 = new File("resources/sounds/fx8.wav");
    private final File laser = new File("resources/sounds/laser.wav");

    /**
     * This methods plays a audio clip from MediaPlayer and takes wav/mp3 file as parameter input.
     * Parses the value of the audio File to to a String, then plays it when triggered by events.
     *
     * @param sound File
     */
    public void play(File sound) {

            try {
                MediaPlayer audio = new MediaPlayer(
                        new Media(
                                new File(valueOf(sound)).toURI().toString()));
                audio.setVolume(vol);
                audio.play();

            } catch (MediaException me) {
                Dialogs dialogs = new Dialogs();
                dialogs.audioError();
                System.err.println("Cant load the damn file.. ");
                System.err.println(me.getMessage());

            } catch (RuntimeException re) {
                System.err.println(re.getMessage());
            }
    }

    /**
     * This method sets the volume of the object instance in this class.
     *
     * @param vol double
     */
    public void setVol(double vol) {
        this.vol = vol;
    }

    /**
     * This method returns the laser clip.
     *
     * @return File
     */
    public File getLaser() {
        return laser;
    }

    /**
     * This method returns the pop1 clip.
     *
     * @return File
     */
    public File getPop1() {
        return pop1;
    }

    /**
     * This method returns the pop2 clip.
     *
     * @return File
     */
    public File getPop2() {
        return pop2;
    }

    /**
     * This method returns the pop3 clip.
     *
     * @return File
     */
    public File getPop3() {
        return pop3;
    }

    /**
     * This method returns the fx2 clip.
     *
     * @return File
     */
    public File getFx2() {
        return fx2;
    }

    /**
     * This method returns the fx3 clip.
     *
     * @return File
     */
    public File getFx3() {
        return fx3;
    }

    /**
     * This method returns the fx8 clip.
     *
     * @return File
     */
    public File getFx8() {
        return fx8;
    }

}
