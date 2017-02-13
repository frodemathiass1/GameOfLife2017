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

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("Gui.fxml"));
        primaryStage.setTitle("Game of Life");
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        //gc = canvas.getGraphicsContext2D();
        //gc.setStroke(Color.BLUE);
        //gc.setLineWidth(1);

    }

    //Canvas canvas = new Canvas(200,200);
    //GraphicsContext gc;








    public static void main(String[] args) {

        launch(args);

    }
}
