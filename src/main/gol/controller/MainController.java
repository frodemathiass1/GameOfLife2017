package main.gol.controller;

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
import main.gol.model.*;
import main.gol.model.Cell;
import java.util.Random;

import static main.gol.GameOfLife.WIDTH; // Access Stage dimensions from main class
import static main.gol.GameOfLife.HEIGHT;

public class MainController implements Initializable {

    // Old timeline codes (Frode)
    /*
    private final Timeline timeline = new Timeline();
    private final double durationMillis = 500;
    */

    //Timeline to control the animation
    private Timeline timeline = new Timeline();
    private Board board;
    private final int cellSize = 5;

    /**
     * Internal FXML objects
     */
    @FXML private GraphicsContext gc;
    @FXML private Slider speedSlider;
    @FXML private Button play;
    @FXML private ColorPicker cellColor, gridColor, backgroundColor;
    @FXML private Canvas canvas;
    @FXML private Slider sizeSlider;
    @FXML private MenuItem small, normal, large;

    /**
     * init application
     * @param location java..net.URL
     * @param resources java.util.ResourceBundle
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        // Initialize the GraphicsContext and the draw function
        gc = canvas.getGraphicsContext2D();
        this.board = new Board(gc, cellSize); // this is dependency injection!
        this.board.drawGrid();

        // Modified KeyFrame animation from tutorial
        KeyFrame frame = new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nextGeneration();
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.sizeHandler();
        this.changeSizeHandler();
        clearBoard(); // Workaround to enable gridSize to be set "Big" as "Default"
        cellColor.setValue(Color.BLACK);
        backgroundColor.setValue(Color.WHITE);
        gridColor.setValue(Color.LIGHTGRAY);

        // Old codes (Frode)
        /*
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        board = new Board(graphics, cellSize); // this is dependency injection!
        board.drawGrid();
        this.sizeHandler();
        this.changeSizeHandler();
        cellColor.setValue(Color.BLACK);
        gridColor.setValue(Color.DARKGRAY);
        backgroundColor.setValue(Color.WHITE);
        clearBoard(); // Workaround to enable gridSize to be set "Big" as "Default"
        */
    }

    private void setCellSize(int cellSize) {
        this.board.setCellSize(cellSize);
    }

    @FXML
    public void nextGeneration(){
        // Set the animation speed
        timeline.setRate(speedSlider.getValue());
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
    public void setBackgroundColor(){
        board.setBcColor(backgroundColor.getValue());
        board.drawGrid();
    }

    @FXML
    public void setGridColor(){
        board.setGridColor(gridColor.getValue());
        board.drawGrid();
    }

    @FXML
    public void setRandomColor(){
        Random rand = new Random();
        Color randCellColor = Color.rgb(rand.nextInt(175),rand.nextInt(255),rand.nextInt(175) );
        Color randBcColor = Color.rgb(rand.nextInt(175),rand.nextInt(255),rand.nextInt(175) );
        Color randGridColor = Color.rgb(rand.nextInt(175),rand.nextInt(255),rand.nextInt(175) );
        // Set all colors to random
        board.setCellColor(randCellColor);
        board.setBcColor(randBcColor);
        board.setGridColor(randGridColor);
        // Update the color picker to correct color
        cellColor.setValue(randCellColor);
        backgroundColor.setValue(randBcColor);
        gridColor.setValue(randGridColor);
        board.drawGrid();
    }

    @FXML
    public void resetColor(){
        // Reset all colors to original value
        board.setCellColor(Color.BLACK);
        board.setBcColor(Color.WHITE);
        board.setGridColor(Color.LIGHTGRAY);
        // Update the color picker to correct color
        cellColor.setValue(Color.BLACK);
        backgroundColor.setValue(Color.WHITE);
        gridColor.setValue(Color.LIGHTGRAY);
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
            sizeSlider.setValue(5); //Set slider to same cell value
            board.drawGrid();
        });

        normal.setOnAction(e -> {
            board.setCellSize(10);
            board.setColumns(80);
            board.setRows(55);
            sizeSlider.setValue(10); //Set slider to same cell value
            board.drawGrid();
        });

        large.setOnAction(e -> {
            board.setCellSize(20);
            board.setColumns(40);
            board.setRows(28);
            sizeSlider.setValue(20); //Set slider to same cell value
            board.drawGrid();
        });
    }

    /**
     * Size slider listener
     * Observable list. Set gridSize from slider values
     */
    public void sizeHandler(){
        sizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            setCellSize(newValue.intValue());
            board.drawGrid(); // Need this to update the board live
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

          // Double click and drag to smooth erase
          if(event.getClickCount() > 1){
             cell.setNextState(toggleState = false);
          }

          // Draw the cell
          this.board.drawCell(cell);
      }
      catch(NullPointerException ne){
          // Don´t print this exception!
          //ne.printStackTrace();
      }
    }

    /**
     * Start animation
     * Checks if running, if running stop animation and change button text
     */
    @FXML public void play(){
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
            play.setText("Play");
        } else {
            timeline.play();
            play.setText("Stop");
        }

        // Old codes (Frode)
        /*
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
        */
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

        // Is this necessary?
        //this.setCellSize(10);
        //this.board.setRows(80);
        //this.board.setColumns(55);

        // I have already declared gc
        //GraphicsContext graphics = canvas.getGraphicsContext2D();

        gc.clearRect(0,0, WIDTH, HEIGHT);
        board.clearBoard(board.getGrid());
        board.drawGrid();
        sizeSlider.setValue(cellSize);
        play.setText("Play");
    }

}
