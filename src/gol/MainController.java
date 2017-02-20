package gol;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;


public class MainController implements Initializable {


    //private GraphicsContext gc;
    private Board board;
    private int cellSize;
    private Cell[][] cells;
    private int speed;



    // Internal GUI objects
    @FXML private Button startBtn, resetBtn, exitApp;
    @FXML private ColorPicker colorPick;
    @FXML private Canvas canvas;
    @FXML private Slider sizeSlider;
    @FXML private GridPane grid;


    // Initialize at application startup
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        //draw();
        sliderSize();
    }


    // Draw method renders to Canvas
    public void draw(){
        GraphicsContext gc=canvas.getGraphicsContext2D();
        board=new Board(gc);
    }


    // Button & Slider Event handling
    public void startPause(){
        GraphicsContext gc=canvas.getGraphicsContext2D();
        board=new Board(gc);

        String st = startBtn.getText();
        startBtn.setText("Pause");
        if(st=="Pause"){
            startBtn.setText("Start");
        }
       draw();
    }


    public void resetBoard(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
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

        //@Override
        sizeSlider.valueProperty().addListener((observable, oldSize, newSize)
                -> System.out.println("Value: " + newSize.intValue()));
    }


}
