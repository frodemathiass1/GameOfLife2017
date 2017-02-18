package gol;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GameOfLife extends Application {

    // Stage Dimensions
    public static final int WIDTH=600;
    public static final int HEIGHT=400;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Gui.fxml"));
        primaryStage.setTitle("Game of Life");
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }



    public static void main(String[] args) {

        launch(args);

        // Constructor and method tests
        Cell cell = new Cell(10,10,false);
        System.out.println(cell.getPosX()+" "+
                cell.getPosY()+" "+
                cell.getCellColor()+" "+
                cell.isAlive()+" "+
                cell.getSize()+"\n"+
                cell.getCellShape()

        );
    }
}
