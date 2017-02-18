package gol;



import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.paint.Color;



public class Main extends Application {

    // Stage Dimensions
    public static final int WIDTH=100;
    public static final int HEIGHT=100;






    @Override
    public void start(Stage primaryStage) throws Exception{



        Parent root = FXMLLoader.load(getClass().getResource("Gui.fxml"));
        primaryStage.setTitle("Game of Life");
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();



    }



    public static void main(String[] args) {

        // Constructor and method tests
        Cell cell = new Cell(10,10,false,10);
        System.out.println(cell.getPosX()+" "+
                cell.getPosY()+" "+
                cell.getCellColor()+" "+
                cell.isAlive());


        // Launch application
        launch(args);

    }
}
