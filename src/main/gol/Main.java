package main.gol;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    // Stage Dimensions
    private static final int WIDTH =  800;
    private static final int HEIGHT = 625;

    @Override
    public void start(Stage primaryStage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("view/view.fxml"));
            primaryStage.setTitle("Game Of Life");
            primaryStage.setOnCloseRequest(e -> Platform.exit());
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
            primaryStage.show();
            primaryStage.setResizable(false);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}
