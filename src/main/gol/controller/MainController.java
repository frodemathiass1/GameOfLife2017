package main.gol.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import main.gol.model.*;
import main.gol.model.Cell;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController implements Initializable {


    private Timeline timeline = new Timeline();
    private Board board;
    private int cellSize = 5;
    private int columns = 160;
    private int rows = 110;
    private GraphicsContext gc;
    private GOLGraphics graphics;

    // Internal GUI Objects

    @FXML private Slider speedSlider;
    @FXML private Button play;
    @FXML private ColorPicker cellColor, gridColor, backgroundColor;
    @FXML private Canvas canvas;
    @FXML private Slider zoomSlider;
    @FXML private MenuItem small, normal, large, fileSelect;
    @FXML private MenuBar menuBar;


    // File
    private Desktop desktop = Desktop.getDesktop();
    private final FileChooser fileChooser = new FileChooser();


    // Open File
    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    FileChooser.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }


    // Choose file
    @FXML private void selectFile() {

        fileSelect.setOnAction(e -> {
            fileChooser.setTitle("Open Pattern File");
            File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());

            if (file != null) {
                openFile(file);
            }
        });
    }


    // Glider
    byte[][] testBoard4 =  {
            { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
    };

    /**
     * Init method for the gui. Draws a empty grid to canvas and initialize
     * timeline with keyframe animation, sets default color settings
     * and initializes the observable sliders
     *
     * @param location java..net.URL
     * @param resources java.util.ResourceBundle
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        // Initialize the GraphicsContext and the draw function
        gc = canvas.getGraphicsContext2D();

        this.board = new Board(gc, cellSize);

        this.board.setBoard(columns, rows);
        //this.board.setBoard(testBoard4);

        System.out.println(this.board.countNeighbours(0,0));


        graphics = new GOLGraphics();

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
        //graphics.drawGeneration(this.board.getGenerationList());
    }

    @FXML
    public void randomGeneration(){
        board.makeRandomGenerations();
        board.drawGeneration();
    }

    public void stopAnimation(){
        if (timeline.getStatus() == Animation.Status.RUNNING){
            timeline.stop();
            play.setText("Play");
        }
    }

    /**
     * MenuButton handler for grid and cellSize settings.
     */
    @FXML
    private void changeBoardSize(){
        small.setOnAction(e -> {
            stopAnimation();
            board.setCellSize(5);
            board.setBoard(160, 110);
            zoomSlider.setValue(5); //Set slider to same cell value
            board.drawGrid();
            //graphics.drawBoard(this.board);
        });

        normal.setOnAction(e -> {
            stopAnimation();
            board.setCellSize(10);
            board.setBoard(80, 55);
            zoomSlider.setValue(10); //Set slider to same cell value
            board.drawGrid();
            //graphics.drawBoard(this.board);
        });

        large.setOnAction(e -> {
            stopAnimation();
            board.setCellSize(20);
            board.setBoard(40, 28);
            zoomSlider.setValue(20); //Set slider to same cell value
            board.drawGrid();
            //graphics.drawBoard(this.board);
        });
    }

    /**
     * This method observes the zoomSlider values and updates the canvas accordingly.
     */
    public void zoomHandler(){
        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            board.setCellSize(newValue.intValue());
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            board.drawGrid();// Need this to update the board live....
            //graphics.drawBoard(this.board);
        });
    }

    /**
     * This method handle Cell coordinates of canvas by mouseEvents.
     * Draw, erase, toggle cells in editor by click, drag / double click + drag.
     *
     * @param event MouseEvent
     */
    @FXML
    public void getCellPosition(MouseEvent event){
        // Get mouseClick coordinates
      try{
          double x = event.getX(); // mouse x pos
          double y = event.getY(); // mouse y pos

          // Find cell position in board cells array
          // Round down event coordinates to integer, divide it with cellSize to get exact canvas position
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
          //graphics.drawCell(cell);
      }
      catch(NullPointerException ne){

          //ne.printStackTrace();
      }
    }

    /**
     * This method Toggle animation and buttonText
     */
    @FXML
    public void play(){
        if (timeline.getStatus() == Animation.Status.RUNNING){
            timeline.stop();
            play.setText("Play");
        }
        else if (timeline.getStatus() == Animation.Status.STOPPED) {
            timeline.play();
            play.setText("Stop");
        }
    }

    /**
     * This button controller clears the editor-windows and reset settings.
     */
    @FXML
    public void clearBoard(){
        timeline.stop();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        board.clearBoard(board.getGrid());
        board.setBoard(160,110);
        board.drawGrid();
        //graphics.drawBoard(this.board);
        zoomSlider.setValue(cellSize);
        play.setText("Play");
    }

    @FXML
    public void setCellColor(){
        board.setCellColor(cellColor.getValue());
        board.drawGrid();
        //graphics.drawBoard(this.board);
    }

    @FXML
    public void setBackgroundColor(){
        board.setBcColor(backgroundColor.getValue());
        board.drawGrid();
        //graphics.drawBoard(this.board);
    }

    @FXML
    public void setGridColor(){
        board.setGridColor(gridColor.getValue());
        board.drawGrid();
        //graphics.drawBoard(this.board);
    }

    /**
     * This method sets and executes random colors to the Cells, GridLine and Background
     */
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
        //graphics.drawBoard(this.board);
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
        //graphics.drawBoard(this.board);
    }

    @FXML
    public void quitApp(){
        Platform.exit();
    }

}
