package main.gol;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the Main class to fire off the application with its stage and scenegraph.
 * Sets a primary stage with the fxml  document as the root node which contains all the nodes
 * for the GUI components.
 */
public class Main extends Application {

    // Stage Dimensions
    private static final int WIDTH =  800;
    private static final int HEIGHT = 600;

    /**
     * This method maps the fxml file to the scenegraph root, and sets it to the stage,
     * then shows it. Resizable is set to false to deactivate the scalable window.
     *
     * @param primaryStage Stage
     */
    @Override
    public void start(Stage primaryStage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("view/view.fxml"));
            primaryStage.setTitle("Game Of Life");
            primaryStage.setOnCloseRequest(e -> Platform.exit());
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
            primaryStage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * This method launches the application.
     * @param args arguments
     */
    public static void main(String[] args) {

        launch(args);
    }
}
