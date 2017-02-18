package gol;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;


public class MainController implements Initializable {


    GraphicsContext gc;
    private Board board;

    // Internal GUI objects
    @FXML private Button startBtn;
    @FXML private Button resetBtn;
    @FXML private ColorPicker colorPick;
    @FXML private Canvas canvasControl;
    @FXML private Slider sizeSlider;


    // Initialize at application startup
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        draw();
    }


    // Draw method render to Canvas
    public void draw(){
        gc=canvasControl.getGraphicsContext2D();
        board=new Board(gc);
                                                //gc.setFill(Color.GREEN);
                                                //gc.fillRect(0,0,100,100);
                                                //gc.fillRect(100,100,100,100);
                                                //gc.fillRect(200,200,100,100);
                                                //gc.fillRect(200,0,100,100);
                                                //gc.fillRect(0,200,100,100);
    }



    // Button & Slider Event handling
    public void startPause(){
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
