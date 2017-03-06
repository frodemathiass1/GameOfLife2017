package gol;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import static gol.GameOfLife.WIDTH; // Access Stage dimensions from main class
import static gol.GameOfLife.HEIGHT;


public class MainController implements Initializable {

    public Board board;
    public int cellSize = 10;

    // Internal GUI objects
    @FXML private Button startBtn, resetBtn, exitApp;
    @FXML private ColorPicker colorPick;
    @FXML private Canvas canvas;
    @FXML private Slider sizeSlider;



    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        this.board=new Board(graphics, this.cellSize); // this is dependency injection!
        colorPick.setValue(Color.BLACK);
        this.board.drawGrid();
        sliderEventHandler();

    }

    public void setCellSize(int cellSize) {
        this.board.setCellSize(cellSize);
        this.board.drawGrid();
    }


    @FXML
    public void getCellPosition(MouseEvent event){

        // Get mouseClick coordinates
        double x = event.getX(); // mouse x pos
        double y = event.getY(); // mouse y pos

        // Find cell position in board cells array
        // Rounds down event coordinates to integer and divides it with cellSize to get exact canvas position
        int cellPosX = (int) Math.floor(x / board.getCellSize());
        int cellPosY = (int) Math.floor(y / board.getCellSize());
        System.out.println(cellPosX+" "+cellPosY);
        //System.out.println(cellSize);

        // Get cell
        Cell cell = this.board.getCellCoordinates(cellPosX, cellPosY);

        // Toggle alive
        boolean toggleAlive = !cell.isAlive();
        cell.setAlive(toggleAlive);

        // Update canvas
        this.board.drawCell(cell);
    }




    // Button & Slider Event handling
    public void sliderEventHandler(){
        sizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                setCellSize(newValue.intValue());
                //System.out.println(board.getColor());
                //System.out.println(newValue.intValue() );
            }
        });

    }
    @FXML
    public void startPause(){
        board.checkNeighbors();
        clearBoard();
        board.resetCounter();


    }
    @FXML
    public void clearBoard(){
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.clearRect(0,0,WIDTH,HEIGHT);
        board.clearBoard(board.getGrid());
        board.drawGrid();
        System.out.println("Board is cleared");
        sizeSlider.setValue(10); // reset slider when reset
    }

    @FXML
    public void pickColor(){
         //System.out.println(colorPick.getValue().toString());
         board.setPickedColor(colorPick.getValue());
         board.drawGrid();
    }

    @FXML
    public void exitApp(){
        Platform.exit();
    }
}
