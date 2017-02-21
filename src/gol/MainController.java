package gol;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;

// test
public class MainController implements Initializable {

    private Board board;
    private int cellSize = 20;
    //private Cell[][] cells;


    // Internal GUI objects
    @FXML private Button startBtn, resetBtn, exitApp;
    @FXML private ColorPicker colorPick;
    @FXML private Canvas canvas;
    @FXML private Slider sizeSlider;


    // Initialize at application startup
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        this.board=new Board(gc, this.cellSize); // this is dependency injection!
    }

    @FXML
    public void getCellPosition(MouseEvent event){

        // Get mouseClick coordinates
        double x = event.getX(); // mouse x pos
        double y = event.getY(); // mouse y pos

        // Find cell position in board cells array
        // Rounds down event coordinates to integer and divides it with cellsize to get canvas position
        int cellPosX = (int) Math.floor(x / this.cellSize);
        int cellPosY = (int) Math.floor(y / this.cellSize);
        System.out.println(cellPosX+" "+cellPosY);

        // Get cell
        Cell cell = this.board.getCell(cellPosX, cellPosY);

        // Toggle alive
        boolean toggleAlive = !cell.isAlive();
        cell.setAlive(toggleAlive);

        // update canvas
        this.board.drawCell(cell);
    }



    // Button & Slider Event handling
    @FXML
    public void startPause(){

    }
    @FXML
    public void resetBoard(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0,600,354);
    }
    @FXML
    public void pickColor(){
         System.out.println(colorPick.getValue().toString());
    }

    @FXML
    public void exitApp(){
        Platform.exit();
    }

    @FXML
    public void sliderSize(){
        //@Override
        sizeSlider.valueProperty().addListener((observable, oldSize, newSize)
                -> System.out.println("Value: " + newSize.intValue()));
    }


}
