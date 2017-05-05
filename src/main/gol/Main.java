package main.gol;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the Main class to fire off the standalone application.
 * Sets a primary stage and scene with the fxml document as the root node containing all its children
 * in the overridden start method.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 2.0
 */
public class Main extends Application {

    // Stage Dimensions
    private static final int WIDTH =  800;
    private static final int HEIGHT = 600;

    /**
     * Main method and entry point to start the JavaFX application.
     * Map the fxml file to the scenegraph root, and set it to the stage, then shows it in a stage window.
     *
     * @param primaryStage Stage
     */
    @Override
    public void start(Stage primaryStage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("view/view.fxml"));
            primaryStage.setTitle("Game Of Life");
            primaryStage.setOnCloseRequest(e -> Platform.exit());
            primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
            //primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * This method launches the standalone application.
     *
     * @param args null
     */
    public static void main(String[] args) {

        launch(args);
    }
}