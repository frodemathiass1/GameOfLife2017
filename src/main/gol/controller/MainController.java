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


public class MainController implements Initializable {


    private Timeline timeline = new Timeline();
    private Board board;
    private int cellSize = 5;
    private int columns = 160;
    private int rows = 110;
    private GOLGraphics graphics;


    /**
     * Internal FXML objects
     */
    @FXML
    private GraphicsContext gc;
    @FXML
    private Slider speedSlider;
    @FXML
    private Button play;
    @FXML
    private ColorPicker cellColor, gridColor, backgroundColor;
    @FXML
    private Canvas canvas;
    @FXML
    private Slider zoomSlider;
    @FXML
    private MenuItem small, normal, large;


    /**
     * Override initialize from Initializable
     * @param location java..net.URL
     * @param resources java.util.ResourceBundle
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        // Initialize the GraphicsContext and the draw function
        gc = canvas.getGraphicsContext2D();
        //graphics = new GOLGraphics();
        this.board = new Board(gc, cellSize);

        // Use this board for testing, but test is a no go...
        // Problem due to dependencies, canvas graphic fxml fx:controller (????)
        byte[][] testBoard1 =  {
                { 0, 0, 0, 0 },
                { 0, 1, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };
        byte[][] testBoard2 =  {
                { 0, 0, 0, 0 },
                { 0, 1, 0, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };
        this.board.setBoard(columns, rows);
        //this.board.setBoard(testBoard1);

        System.out.println(this.board.countNeighbours(0,0));
        this.board.drawGrid();
        //graphics.drawBoard(this.board);







        // Modified KeyFrame animation from tutorial
        KeyFrame frame = new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nextGeneration();
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.zoomHandler();
        this.changeBoardSize();
        cellColor.setValue(Color.BLACK);
        backgroundColor.setValue(Color.WHITE);
        gridColor.setValue(Color.LIGHTGRAY);
    }

    @FXML
    public void nextGeneration(){
        timeline.setRate(speedSlider.getValue());
        board.nextGeneration();
        board.drawGeneration();
    }


    @FXML
    public void randomGeneration(){
        board.makeRandomGenerations();
        board.drawGeneration();
    }

    /**
     * Grid size selector
     */
    @FXML
    private void changeBoardSize(){
        small.setOnAction(e -> {
            board.setCellSize(5);
            board.setBoard(160, 110);
            zoomSlider.setValue(5); //Set slider to same cell value
            board.drawGrid();

        });

        normal.setOnAction(e -> {
            board.setCellSize(10);
            board.setBoard(80, 55);
            zoomSlider.setValue(10); //Set slider to same cell value
            board.drawGrid();


        });

        large.setOnAction(e -> {
            board.setCellSize(20);
            board.setBoard(40, 28);
            zoomSlider.setValue(20); //Set slider to same cell value
            board.drawGrid();

        });
    }

    /**
     * Zoom slider
     *
     * Change cell size from slider values to zoom on grid
     * Observable list
     */
    public void zoomHandler(){
        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            board.setCellSize(newValue.intValue());
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            board.drawGrid();// Need this to update the board live....
        });
    }

    /**
     * Find Cell coordinates from canvas mouseClick, toggle cell state
     * and draw cell to canvas
     *
     * @param event MouseEvent
     *
     */
    @FXML
    public void getCellPosition(MouseEvent event){
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
              cell.setState(toggleState);
          }

          // Double click and drag to smooth erase
          if(event.getClickCount() > 1){
              toggleState = false;
              cell.setState(toggleState);
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
    @FXML
    public void play(){
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
            play.setText("Play");
        } else {
            timeline.play();
            play.setText("Stop");
        }


    }


    /**
     * GUI Reset button
     *
     * Stop timeline, clear canvas
     * Reset grid settings to default
     * Reset slider value and buttons to default
     * Draw cleared grid to canvas with clearRect()
     */
    @FXML
    public void clearBoard(){
        timeline.stop();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        board.clearBoard(board.getGrid());
        board.setBoard(160,110);
        board.drawGrid();
        zoomSlider.setValue(cellSize);
        play.setText("Play");
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

    @FXML
    public void quitApp(){
        Platform.exit();
    }

}
