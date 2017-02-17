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

    // Interne objekter til GUI
    @FXML private Canvas canvasControl;
    @FXML private Button startBtn;
    @FXML private Button resetBtn;
    @FXML private ColorPicker colorPick;
    //@FXML private Slider sizeSlider;


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        //Canvas canvas = new Canvas(200,200);
        //GraphicsContext gc;
        //colorPicker.setValue(Color.BLUE);
        // draw();

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


        //ColorPicker cp = new ColorPicker();
        //String picked = cp.getValue().toString();  // Gets the Hex code
       colorPick.getValue().toString();
        //System.out.println(c.getRed() +"\n"+ c.getGreen() +"\n"+ c.getBlue() +"\n");
        System.out.println(colorPick.getValue().toString());

    }

















}
