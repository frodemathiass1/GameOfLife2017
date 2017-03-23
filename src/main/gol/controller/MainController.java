package main.gol.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.gol.model.*;
import main.gol.model.Cell;

import java.util.Random;

import static main.gol.GameOfLife.WIDTH; // Access Stage dimensions from main class
import static main.gol.GameOfLife.HEIGHT;



public class MainController implements Initializable {


    private final Timeline timeline = new Timeline();
    private final double durationMillis = 500;
    private Board board;
    private final int cellSize = 5;


    /**
     * Internal FXML objects
     */
    @FXML private Button play,random;
    @FXML private ColorPicker cellColor,gridColor,backgroundColor;
    @FXML private Canvas canvas;
    @FXML private Slider sizeSlider;
    @FXML private MenuItem small, normal, large;
    @FXML private Label counter;







    /**
     * init application
     * @param location java..net.URL
     * @param resources java.util.ResourceBundle
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        board = new Board(graphics, cellSize); // this is dependency injection!
        board.drawGrid();

        this.sizeHandler();
        this.changeSizeHandler();

        cellColor.setValue(Color.BLACK);
        gridColor.setValue(Color.DARKGRAY);
        backgroundColor.setValue(Color.WHITE);
        clearBoard(); // Workaround to enable gridSize to be set "Big" as "Default"

    }



    private void setCellSize(int cellSize) {
        this.board.setCellSize(cellSize);
    }

    @FXML
    public void nextGeneration(){

        board.nextGeneration();
    }


    @FXML
    public void randomGeneration(){

        board.makeRandomGenerations();
    }


    @FXML
    public void setCellColor(){

        board.setCellColor(cellColor.getValue());
        board.drawGrid();
    }


    @FXML
    public void setGridColor(){

        board.setGridColor(gridColor.getValue());
        board.drawGrid();
    }

    @FXML
    public void setBackgroundColor(){

        board.setBcColor(backgroundColor.getValue());
        board.drawGrid();
    }

    @FXML
    public void setRandomColor(){
        Random rand = new Random();
        Color randColor = Color.rgb(rand.nextInt(175),rand.nextInt(255),rand.nextInt(175) );
        board.setCellColor(randColor);
        board.drawGrid();

    }


    /**
     * Grid size selector
     */
    @FXML
    private void changeSizeHandler(){

        small.setOnAction(e -> {
            setCellSize(5);
            board.setColumns(160);
            board.setRows(110);
            board.drawGrid();
        });

        normal.setOnAction(e -> {
            board.setCellSize(10);
            board.setColumns(80);
            board.setRows(55);
            board.drawGrid();
        });

        large.setOnAction(e -> {
            board.setCellSize(20);
            board.setColumns(40);
            board.setRows(28);
            board.drawGrid();
        });
    }






    /**
     * Size slider listener
     * Observable list. Set gridSize from slider values
     */
    public void sizeHandler(){
        sizeSlider.valueProperty().addListener((
                observable, oldValue, newValue) -> setCellSize(newValue.intValue()));
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
      try{
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

          // For smooth drawing
          if(toggleState){
              cell.setNextState(toggleState);
          }

          // Double click to toggle back
          if(event.getClickCount() > 1){
             cell.setNextState(toggleState);
          }

          this.board.drawCell(cell);


      }
      catch(NullPointerException ne){
          ne.printStackTrace();
      }

    }



    /**
     * Start animation
     * Checks if running, if running stop animation and change button text
     */
    @FXML public void play(){
        this.timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(durationMillis),
                        e-> board.nextGeneration()
                ));
        this.timeline.setRate(3.0);
        this.timeline.setCycleCount(Timeline.INDEFINITE);


        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
            play.setText("Play");
        } else {
            timeline.play();
            play.setText("Stop");
        }
    }



    /**
     * Increase animation rate
     */
    @FXML public void increaseRate(){
        timeline.setRate(timeline.getCurrentRate() + 1);
    }



    /**
     * Decrease animation rate
     */
    @FXML public void decreaseRate(){
        timeline.setRate(timeline.getCurrentRate() - 15);
    }


    /**
     * Close application
     */
    @FXML public void quitApp(){
        Platform.exit();
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
        graphics.clearRect(0,0, WIDTH, HEIGHT);
        board.clearBoard(board.getGrid());
        board.drawGrid();
        sizeSlider.setValue(cellSize);
        play.setText("Play");


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
