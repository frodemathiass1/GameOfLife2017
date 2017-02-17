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
    @FXML private Slider sizeSlider;

    public static final int SIZE = 25;
    private GraphicsContext gc;

    public void draw(GraphicsContext gc){
        //gc.setFill(Color.GREEN);
        //gc.fillRect(0,0,100,100);
        //gc.fillRect(100,100,100,100);
        //gc.fillRect(200,200,100,100);
        //gc.fillRect(200,0,100,100);
        //gc.fillRect(0,200,100,100);
    }

    private byte[][] board = {
            { 1, 0, 0, 1 },
            { 0, 1, 1, 0 },
            { 0, 1, 1, 0 },
            { 1, 0, 0, 1 }
    };




    public void setBoard(byte[][] board){
        for(int i=0; i <board.length; i++ ){
            for(int j = 0; j < board[0].length; j++ ){
                System.out.print(board[i][j]);
                if(board[i][j]==1){
                    gc.fillRect(i*SIZE,j*SIZE,SIZE,SIZE);
                }
            }
            System.out.println();
        }
    }




    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        gc=canvasControl.getGraphicsContext2D();
        setBoard(board);
        draw(gc);

    }


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

    public void resetBoard(ActionEvent e){

        String rB = resetBtn.getText();
        resetBtn.setText("Foo");
        resetBtn.setTextFill(Color.GREEN);
        if(rB=="Foo"){
            resetBtn.setText("Reset");
            resetBtn.setTextFill(Color.BLACK);

        }
        System.out.println(rB);

    }

    public void pickColor(ActionEvent e){

        colorPick.getValue().toString();
        System.out.println(colorPick.getValue().toString());

    }

















}
