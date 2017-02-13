package gol;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class Controller implements Initializable {

    //@FXML private Canvas canvas;
    @FXML private Button startBtn;
    //@FXML private Button resetBtn;
    //@FXML private ColorPicker colorPicker;
    //@FXML private Slider sizeSlider;


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        //colorPicker.setValue(Color.BLUE);
        // draw();

    }

    public void startBtn(ActionEvent event){

        startBtn.setText("Pause");
    }







}
