package gol;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;


import java.lang.management.PlatformLoggingMXBean;

public class Controller implements Initializable {

    //@FXML private Canvas canvas;
    @FXML private Button startBtn;
    @FXML private Button resetBtn;
    @FXML private ColorPicker pickColor;
    //@FXML private Slider sizeSlider;


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

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

    }

    public void pickColor(ActionEvent e){

        ColorPicker cp = new ColorPicker();
        Color picked = cp.getValue();

        System.out.println(picked);
        resetBtn.setTextFill(picked);
        // Funker ikke.......

    }












}
