package gol;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;


public class MainController implements Initializable {


    GraphicsContext gc;
    private Board board; // why grey ?


    // Internal GUI objects
    @FXML private Button startBtn;
    @FXML private Button resetBtn;
    @FXML private ColorPicker colorPick;
    @FXML private Canvas canvasControl;
    @FXML private Slider sizeSlider;





    // Initialize at application startup
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        //draw();
        sliderSize();
    }


    // Draw method renders to Canvas
    public void draw(){
        gc=canvasControl.getGraphicsContext2D();
        board=new Board(gc);
    }


    // Button & Slider Event handling
    public void startPause(){
        gc=canvasControl.getGraphicsContext2D();
        board=new Board(gc);

        String st = startBtn.getText();
        startBtn.setText("Pause");
        if(st=="Pause"){
            startBtn.setText("Start");
        }
        draw();
    }


    public void resetBoard(){
            gc.clearRect(0,0,600,354);
            System.out.println("Canvas reset");
            resetDialog();
    }

    public void resetDialog(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reset");
        alert.setHeaderText(null);
        alert.setContentText("<< The Board is Cleared! >>");
        alert.showAndWait();
    }

    @FXML
    public void pickColor(){
        System.out.println(colorPick.getValue().toString());
    }

    @FXML
    public void exitApp(){
        Platform.exit();
        System.out.println(" <<  Good Bye! >> ");
    }


    @FXML
    public void sliderSize(){
        //return ((int)sizeSlider.getValue());
        //cellSize.setSize();
        sizeSlider.valueProperty().addListener((observable, oldValue, newValue)
                -> System.out.println("Value: " + newValue.intValue()));
    }

}
