package main.gol.controller.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;

import static java.lang.String.*;

/**
 * This class handles audio files triggered by GUI action events.
 * Sound downloaded from www.freeSound.org under Creative common licence.
 *
 */
public class Sound {

    private static double vol = 0.1;
    private static File theFile;

    // Files
    private static final File pop1 = new File("sounds/pop1.wav");
    private static final File pop2 = new File("sounds/pop2.wav");
    private static final File pop3 = new File("sounds/pop3.wav");
    private static final File popD = new File("sounds/popD.wav");
    private static final File laser = new File("sounds/laser.wav");
    private static final File rattle = new File("sounds/rattle.wav");
    private static final File fx1 = new File("sounds/fx1.wav");
    private static final File fx2 = new File("sounds/fx2.wav");
    private static final File fx3 = new File("sounds/fx3.wav");
    private static final File fx4 = new File("sounds/fx4.wav");
    private static final File fx5 = new File("sounds/fx5.wav");
    private static final File fx6 = new File("sounds/fx6.wav");
    private static final File fx7 = new File("sounds/fx7.wav");
    private static final File fx8 = new File("sounds/fx8.wav");

    /**
     * This methods plays a audio clip from MediaPlayer and takes wav/mp3 file as parameter input
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

    public File getRattle() {
        return rattle;
    }

    public File getPop1() {
        return pop1;
    }

    public File getPop2() {
        return pop2;
    }

    public File getPop3() {
        return pop3;
    }

    public File getPopD() {
        return popD;
    }

    public File getFx1() {
        return fx1;
    }

    public File getFx2() {
        return fx2;
    }

    public File getFx3() {
        return fx3;
    }

    public File getFx4() {
        return fx4;
    }

    public File getFx5() {
        return fx5;
    }

    public File getFx6() {
        return fx6;
    }

    public File getFx7() {
        return fx7;
    }

    public File getFx8() {
        return fx8;
    }
}
