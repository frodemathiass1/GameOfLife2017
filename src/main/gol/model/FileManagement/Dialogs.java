package main.gol.model.FileManagement;

import javafx.scene.control.Alert;

/**
 * Created by Frode Mathiassen on 19.04.2017
 * Added more to by Tommy Pedersen 19.04.2017
 */
public class Dialogs {

    public void showInfo(){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("About this application");
        alert.setHeaderText("WARNING!!!!\nPlay at your own risk!");
        alert.setContentText("Coded by:\nMagnus, Frode & Tommy\nHiOA, Spring 2017");
        alert.showAndWait();
    }

    public void notFound(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information message");
        alert.setHeaderText("File not found");
        alert.setContentText("Sorry, can't open file. Try other file\nSupported formats: .txt / .cells");
        alert.showAndWait();

    }

    public void httpError(){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Open URL");
        alert.setHeaderText("URL must start with http!");
        alert.showAndWait();
    }

    public void urlError(){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Open URL");
        alert.setHeaderText("Something went wrong.\nPlease try again, or enter another URL.");
        alert.showAndWait();
    }

}
