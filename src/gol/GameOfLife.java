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



public class GameOfLife extends Application {

    // Stage Dimensions
    public static final int WIDTH=100;
    public static final int HEIGHT=100;






    @Override
    public void start(Stage primaryStage) throws Exception{



        Parent root = FXMLLoader.load(getClass().getResource("Gui.fxml"));
        primaryStage.setTitle("Game of Life");
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.setScene(new Scene(root, 600, 400));
        //root.setStyle("-fx-background-color:darkgray");
        primaryStage.show();



    }



    public static void main(String[] args) {

        // Constructor and method tests
        Cell cell = new Cell(10,10,false);
        System.out.println(cell.getPosX()+" "+
                cell.getPosY()+" "+
                cell.getCellColor()+" "+
                cell.isAlive()+" "+
                cell.getSize()+"\n"+
                cell.getCellShape()

        );


        // Launch application
        launch(args);

    }
}
