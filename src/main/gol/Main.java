package main.gol;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
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
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args) {


        // Testing.............
//        DynamicBoard dbCells = new DynamicBoard(gc, 10);
//        dbCells.addCellRows(10);
//        dbCells.fillCellRows(10);
//        //dbCells.newBoard();
//        dbCells.printCellGrid();
//
//        DynamicBoard dbBytes = new DynamicBoard(gc, 10);
//        dbBytes.addByteRows(10);
//        dbBytes.fillByteRows(10);
//        //dbBytes.newBoard();
//        dbBytes.printByteGrid();

        launch(args);
    }

    // Temp for testing
    private static GraphicsContext gc;
}
