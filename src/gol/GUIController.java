package gol;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.lang.management.PlatformLoggingMXBean;



public class GUIController implements Initializable {

    // Interne objekter GUI
    @FXML private Button startBtn;
    @FXML private Button resetBtn;
    @FXML private ColorPicker colorPick;
    @FXML private Canvas canvasControl;
    //@FXML private Slider sizeSlider;

    public static final int SIZE = 25;
    private GraphicsContext gc;
    private Board board;



    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        gc=canvasControl.getGraphicsContext2D();
        board = new Board(gc);
        board.setBoard(board.getBoard());
        draw(gc);
    }

    public void draw(GraphicsContext gc){
        //gc.setFill(Color.GREEN);
        //gc.fillRect(0,0,100,100);
        //gc.fillRect(100,100,100,100);
        //gc.fillRect(200,200,100,100);
        //gc.fillRect(200,0,100,100);
        //gc.fillRect(0,200,100,100);
    }


    // Event handling
    public void startPause(ActionEvent e){
        String st = startBtn.getText();
        startBtn.setText("Pause");
        startBtn.setTextFill(Color.RED);
        if(st=="Pause"){
            startBtn.setText("Start");
            startBtn.setTextFill(Color.BLACK);
        }
        System.out.println(st);

    }

    public void resetBoard(){
        String rB = resetBtn.getText();
        resetBtn.setText("Foo");
        resetBtn.setTextFill(Color.GREEN);
        if(rB=="Foo"){
            resetBtn.setText("Reset");
            resetBtn.setTextFill(Color.BLACK);
        }
        System.out.println(rB);
    }

    public void pickColor(){
        colorPick.getValue().toString();
        System.out.println(colorPick.getValue().toString());
    }

}
