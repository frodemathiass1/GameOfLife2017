package main.gol;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

import static main.gol.GameOfLife.WIDTH; // Access Stage dimensions from main class
import static main.gol.GameOfLife.HEIGHT;



public class MainController implements Initializable {


    private Timeline timeline = new Timeline();
    private double durationMillis =500;
    private Board board;
    private int cellSize = 5;


    /**
     * Internal FXML objects
     */
    @FXML private Button startBtn,random, stopBtn,resetBtn,fasterRate,slowerRate;
    @FXML private ColorPicker colorPick;
    @FXML private Canvas canvas;
    @FXML private Slider sizeSlider;
    @FXML private MenuBar menuBar;
    @FXML private MenuButton menuButton;
    @FXML private MenuItem slower,slow,normal,fast,faster,defaultSize,big,biggest;




    /**
     * init application
     * @param location
     * @param resources
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        this.board=new Board(graphics, this.cellSize); // this is dependency injection!
        colorPick.setValue(Color.WHITE);
        this.board.drawGrid();
        this.sizeHandler();
        this.changeSizeHandler();
    }


    /**
     * Grid size handler
     */
    public void changeSizeHandler(){

        defaultSize.setOnAction(e -> {
            setCellSize(5);
            board.setColumns(160);
            board.setRows(110);
            board.drawGrid();
        });

        big.setOnAction(e -> {
            board.setCellSize(10);
            board.setColumns(80);
            board.setRows(55);
            board.drawGrid();
        });

        biggest.setOnAction(e -> {
            board.setCellSize(20);
            board.setColumns(40);
            board.setRows(28);
            board.drawGrid();
        });
    }




    /**
     * Alternative rate menu button
     */
    @FXML public void slower(){
        timeline.setRate(timeline.getCurrentRate()-0.5);
    }



    /**
     * Alternative rate menu button
     */
    @FXML public void faster(){
        timeline.setRate(timeline.getCurrentRate()+0.5);
    }



    /**
     * Increase animation rate
     */
    @FXML public void increaseRate(){
            timeline.setRate(timeline.getCurrentRate()+0.5);
    }



    /**
     * Decrease animation rate
     */
    @FXML public void decreaseRate(){
            timeline.setRate(timeline.getCurrentRate()-0.5);
    }



    /**
     *
     * @param cellSize int
     */
    public void setCellSize(int cellSize) {
        this.board.setCellSize(cellSize);
        this.board.drawGrid();
    }



    /**
     *
     * @param millis double
     */
    private void setDurationMillis(double millis){
        this.durationMillis=millis;
    }



    /**
     *
     * @return double durationMillis
     */
    private double getDurationMillis(){
        return this.durationMillis;
    }



    /**
     * Set animation timeline and call nextGeneration in keyframe
     */
   public void setAnimation(){
        this.timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(durationMillis),
                        e-> board.nextGeneration()
                ));
        //this.timeline.setRate(1.0);
        this.timeline.setCycleCount(Timeline.INDEFINITE);

    }



    /**
     * Size slider listener
     * Observable list. Set gridSize from slider values
     */
    public void sizeHandler(){
        sizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            setCellSize(newValue.intValue());
        });
    }




    /**
     * Find Cell coordinates from canvas mouseClick, toggle cell state
     * and draw cell to canvas
     *
     * @param event MouseEvent
     *
     */
    @FXML public void getCellPosition(MouseEvent event){

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
        boolean toggleState = !cell.getState();
        cell.setNextState(toggleState);

        // Update canvas
        this.board.drawCell(cell);
    }



    /**
     * Start animation
     * Checks if running, if running stop animation and change button text
     */
    @FXML public void startPause(){
        setAnimation();
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
            startBtn.setText("Play");
        } else {
            timeline.play();
            startBtn.setText("Stopp");
        }
    }



    /**
     * Toggle all cells state to false and clear canvas
     * Reset grid settings to default
     * Reset slider value and buttons to default
     * Draw cleared grid to canvas with clearRect()
     */
    @FXML public void clearBoard(){
        timeline.stop();
        this.setCellSize(10);
        this.board.setRows(80);
        this.board.setColumns(55);
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.clearRect(0,0,WIDTH,HEIGHT);
        board.clearBoard(board.getGrid());
        board.drawGrid();
        sizeSlider.setValue(cellSize);
        startBtn.setText("Play");
    }




    /**
     * Select color from colorPicker and set selected color
     */
    @FXML public void pickColor(){
            board.setPickedColor(colorPick.getValue());
            board.drawGrid();
    }



    /**
     * Close application
     */
    @FXML public void quitApp(){
        Platform.exit();
    }

        /*
    private int min = 1;
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

        colorPick.setValue(Color.BLACK);  // want random color here
        //this.canvas.setTranslateX(result1);
        //this.canvas.setTranslateY(result2);
        this.board.drawGrid();
    }
    */


}
