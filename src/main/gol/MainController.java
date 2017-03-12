package main.gol;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

import static main.gol.GameOfLife.WIDTH; // Access Stage dimensions from main class
import static main.gol.GameOfLife.HEIGHT;



public class MainController implements Initializable {
    //private Timeline timeline;
    private Timeline timeline = new Timeline();
    private int durationMillis = 250;
    private Board board;
    private int cellSize = 10;


    // Internal GUI objects
    @FXML private Button startBtn,random, stopBtn,resetBtn, exitApp;
    @FXML private ColorPicker colorPick;
    @FXML private Canvas canvas;
    @FXML private Slider sizeSlider;



    public void setCellSize(int cellSize) {
        this.board.setCellSize(cellSize);
        this.board.drawGrid();
    }




   public void setAnimation(){
        //timeline = new Timeline();
        this.timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(durationMillis),e-> board.nextGeneration()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        //return timeline;
    }






    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        this.board=new Board(graphics, this.cellSize); // this is dependency injection!
        colorPick.setValue(Color.WHITE);
        this.board.drawGrid();
        this.sliderHandler();
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

        // Get cell
        Cell cell = this.board.getCell(cellPosX, cellPosY);

        // Toggle alive
        boolean toggleAlive = !cell.isAlive();
        cell.setAlive(toggleAlive);

        // Update canvas
        this.board.drawCell(cell);
    }




    // Button & Slider Event handling
    public void sliderHandler(){
        sizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            setCellSize(newValue.intValue());
            //System.out.println(board.getColor());
            //System.out.println(newValue.intValue() );
        });
    }





    @FXML
    public void startPause(){
        setAnimation();
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
            startBtn.setText("Start");
        } else {
            timeline.play();
            startBtn.setText("Stopp");
        }
    }


    private  int min = 1;
    private int max = 100;
    private int mid = 50;

    @FXML public void randomize(){
        Random rand = new Random();
        int result1 = rand.nextInt(max-min)+min;
        int result2 = rand.nextInt(max-min)+min;
        int result3 = rand.nextInt(max-min)+min;
                   //System.out.println(result+""+result2);


         board.setColumns(result1);
         board.setRows(result2);
         board.setCellSize(result3);

        colorPick.setValue(Color.BLACK);
        this.board.drawGrid();






    }



    @FXML
    public void clearBoard(){
        timeline.stop();
        this.setCellSize(10);
        this.board.setRows(35);
        this.board.setColumns(60);

        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.clearRect(0,0,WIDTH,HEIGHT);
        board.clearBoard(board.getGrid());
        board.drawGrid();
        System.out.println("Board is cleared");
        sizeSlider.setValue(cellSize);
    }




    @FXML
    public void pickColor(){
        //colorPick.setOnAction(e->{
            board.setPickedColor(colorPick.getValue());
            board.drawGrid();
        //});

    }


    @FXML
    public void exitApp(){
        Platform.exit();
    }
}
