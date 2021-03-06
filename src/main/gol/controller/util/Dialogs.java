package main.gol.controller.util;

import javafx.scene.control.Alert;
import main.gol.model.filemanager.Decoder;

/**
 * This class contains a collection of Dialog boxes with information presented to the user on GUI.
 *
 * @version 1.1
 */
public class Dialogs {

    /**
     * This method show info dialogBox about the creators of this application.
     */
    public void showInfo() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("About this application");
        alert.setHeaderText("WARNING!!!!\nPlay at your own risk!");
        alert.setContentText("Coded by:\nMagnus, Frode & Tommy\nHiOA, Spring 2017");
        alert.showAndWait();
    }

    /**
     * This method show info dialogBox when errors occur.
     */
    public void oops() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ooops... Something went wrong");
        alert.setContentText("Try again later...");
        alert.showAndWait();
    }

    /**
     * This method show dialogBox if File cannot be loaded.
     */
    public void notFoundException() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information message");
        alert.setHeaderText("File not found");
        alert.setContentText("Sorry, can't open file. Please try another file\nSupported formats: .txt / .cells / .rle");
        alert.showAndWait();
    }

    /**
     * This method show a warning dialogBox if URL does not start with a http string.
     */
    public void httpError() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Open URL");
        alert.setHeaderText("URL must start with http");
        alert.showAndWait();
    }

    /**
     * This method show a warning dialogBox if something went wrong when loading the URL.
     */
    public void urlError() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Open URL");
        alert.setHeaderText("Something went wrong.\nPlease try again, or enter another URL.");
        alert.showAndWait();
    }

    /**
     * This method show a warning dialogBox if something went wrong when loading the file.
     */
    public void fileError() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Open File");
        alert.setHeaderText("Something went wrong.\nPlease try again, or select another file.");
        alert.showAndWait();
    }

    /**
     * This method show a warning dialogBox if something went wrong with the audio output.
     */
    public void audioError() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Problems with playing audio");
        alert.setHeaderText("Oops... Something went wrong with the audio file.\nSome files tends to cause exceptions...");
        alert.showAndWait();
    }

    /**
     * This method show a information dialogBox with general Game of Life description.
     */
    public void aboutGol() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Game of Life");
        alert.setHeaderText("Game of life rules");
        alert.setContentText("From Wikipedia:\n\n" +
                "The universe of the Game of Life is an infinite two-dimensional orthogonal drawBoard of square cells, " +
                "each of which is in one of two possible states, alive or dead, or populated or unpopulated. " +
                "Every drawCell interacts with its eight neighbours, which are the cells that are horizontally, vertically, " +
                "or diagonally adjacent. At each step in time, the following transitions occur:\n\n" +
                "1: Any live drawCell with fewer than two live neighbours dies, as if caused by underpopulation.\n\n" +
                "2: Any live drawCell with two or three live neighbours lives on to the nextGeneration.\n\n" +
                "3: Any live drawCell with more than three live neighbours dies, as if by overpopulation.\n\n" +
                "4: Any dead drawCell with exactly three live neighbours becomes a live Cell, as if by reproduction.\n\n");
        alert.showAndWait();
    }

    /**
     * This method contains information and descriptions on how to play the game.
     */
    public void howToPlay() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instructions");
        alert.setHeaderText("How to use this application/game");
        alert.setContentText("Draw cells to canvas with mouse by clicking and dragging. Double click to enable drag-erase.\n\n" +
                "Explore the menus for board manipulation with, zoom, colors, animation speed and so on.\n\n" +
                "Predefined patterns can be loaded from the web or by files from the 'Patterns' menu-selection.");
        alert.showAndWait();
    }

    /**
     * This method shows metadata about the loaded file.
     */
    public void fileInfo() {

        Decoder info = new Decoder();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("File info");
        alert.setHeaderText(info.getTheName());
        alert.setContentText("By: " + info.getOrigin() + "\n\n" + info.getContent() + "\n\n" + info.getLink() + "\n\n");
        alert.showAndWait();
    }
}