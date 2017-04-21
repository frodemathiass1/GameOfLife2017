package main.gol.controller.util;

import javafx.scene.control.Alert;

/**
 * This class contains a collection of Dialog boxes with messages presented to the user on GUI
 */
public class Dialogs {


    /**
     * This method show info dialogBox about the creators of this application
     */
    public void showInfo() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("About this application");
        alert.setHeaderText("WARNING!!!!\nPlay at your own risk!");
        alert.setContentText("Coded by:\nMagnus, Frode & Tommy\nHiOA, Spring 2017");
        alert.showAndWait();
    }


    /**
     * This method show dialogBox if File cannot be loaded
     */
    public void notFound() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information message");
        alert.setHeaderText("File not found");
        alert.setContentText("Sorry, can't open file. Try other file\nSupported formats: .txt / .cells");
        alert.showAndWait();
    }


    /**
     * This method show a warning dialogBox if URL does not start with a http string
     */
    public void httpError() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Open URL");
        alert.setHeaderText("URL must start with http!");
        alert.showAndWait();
    }


    /**
     * This method show a warning dialogBox if something went wrong with loading URL
     */
    public void urlError() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Open URL");
        alert.setHeaderText("Something went wrong.\nPlease try again, or enter another URL.");
        alert.showAndWait();
    }
}
