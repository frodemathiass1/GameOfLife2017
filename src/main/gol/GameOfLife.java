package main.gol;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.gol.model.Board;

import java.io.IOException;


public class GameOfLife extends Application {

    // Stage Dimensions
    public static final int WIDTH = 800;
    public static final int HEIGHT = 625;


    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("view/Gui.fxml"));
            primaryStage.setTitle("Game Of Life");
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
