package main.gol;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class GameOfLife extends Application {

    // Stage Dimensions
    public static final int WIDTH=600;
    public static final int HEIGHT=400;


    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Gui.fxml"));
            primaryStage.setTitle("Game of Life");
            primaryStage.setOnCloseRequest(e -> Platform.exit());
            primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
            primaryStage.show();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
