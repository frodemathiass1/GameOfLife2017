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
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.gol.model.Boards.TestBoards;
import main.gol.model.Cell;
import main.gol.model.Boards.FixedBoard;
import main.gol.model.FileManagement.TextDecoder;
import java.util.Random;


public class MainController implements Initializable {


    // Internal GUI Objects
    @FXML private Slider speedSlider;
    @FXML private Button play;
    @FXML private ColorPicker cellColor, gridColor, backgroundColor;
    @FXML private Canvas canvas;
    @FXML private Slider zoomSlider;
    @FXML private MenuItem small, normal, large, fileSelect, url1,url2,url3,url4,url5,url6,url7,url8,url9,url10;


    private GraphicsContext gc;
    private Timeline timeline = new Timeline();
    private FixedBoard board;
    private TextDecoder td;
    private int cellSize = 5;
    private int columns = 160;
    private int rows = 110;
    private TestBoards tb;


//    List view URLs
//    @FXML public ListView myListView;
//    private List<String> urls = new ArrayList<>();
//    private ListProperty<String> listProperty = new SimpleListProperty<>();


    /**
     * Init method for the application. Draws out a empty grid to canvas and initialize
     * timeline with keyframe animation, sets default color settings
     * and initializes the observable sliders and file/url selectors
     *
     * @param location java..net.URL
     * @param resources java.util.ResourceBundle
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        //initTestBoard();
        initBoard();
        initAnimation();
        setDefaultColors();
        cellSizeObserver();
        boardSizeObserver();
        fileSelect.setOnAction(e-> initFileBoard());
        urlSelector();
        //initListViewSelector();
        //System.out.println(this.board.countNeighbours(0,0)); // for testing neighbors
    }

    /**
     * This method handles the URL menu items
     */
    @FXML public void urlSelector(){

         url1.setOnAction(e-> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/airforce.cells"));
         url2.setOnAction(e-> initWebBoard("http://www.conwaylife.com/patterns/gosperglidergun.cells"));
         url3.setOnAction(e-> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/B-52_bomber.cells"));
         url4.setOnAction(e-> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/beacon_maker.cells"));
         url5.setOnAction(e-> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/big_glider.cells"));
         url6.setOnAction(e-> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/bottle.cells"));
         url7.setOnAction(e-> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/brain.cells"));
         url8.setOnAction(e-> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/Cordership.cells"));
         url9.setOnAction(e-> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/cow.cells"));
        url10.setOnAction(e-> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/loaflipflop.cells"));

    }

    /**
     * This Controller run GOL pattern from ContextMenu file Selector
     */
    @FXML public void initFileBoard(){

        stopAnimation();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        td = new TextDecoder();
        td.chooseFile();
        td.runFile(td.getInFile(),td.getMatrix());

        board = new FixedBoard(gc,cellSize);
        board.setBoard(td.getRows(),td.getColumns());
        board.setBoard(td.getMatrix());
        board.drawGrid();
    }

    /**
     *
     * @param url Takes web url argument to read read
     */
    @FXML public void initWebBoard(String url){

        stopAnimation();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        td = new TextDecoder();
        td.runURL(url, td.getMatrix());

        board = new FixedBoard(gc, cellSize);
        board.setBoard(td.getRows(), td.getColumns());
        board.setBoard(td.getMatrix());
        board.drawGrid();
    }

    /**
     * This method initialize the Default FixedBoard
     */
    public void initBoard(){
        gc = canvas.getGraphicsContext2D();
        board = new FixedBoard(gc, cellSize);
        board.setBoard(columns, rows);
        board.drawGrid();
    }

    /**
     * @deprecated init method for the test Board
     */
    public void initTestBoard(){
        gc = canvas.getGraphicsContext2D();
        board = new FixedBoard(gc, 20);
        TestBoards tb = new TestBoards();

        // TestBoards class has several testBoards, each with getters to grab them
        // Need to check the testBoard for dimensions data before row/col data
        // are injected into the setBoard method with the byte array
        tb.findDimensions(tb.getTestBoard());
        board.setBoard(tb.getRows(), tb.getCols());
        this.board.setBoard(tb.getTestBoard());
        board.drawGrid();
    }

    /**
     * Initialize timeline animation
     */
    public void initAnimation(){
        timeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(0.1), event -> nextGeneration());
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Set all colors to default settings
     */
    public void setDefaultColors(){
        cellColor.setValue(Color.BLACK);
        backgroundColor.setValue(Color.WHITE);
        gridColor.setValue(Color.LIGHTGRAY);
    }

    /**
     * Calls next generation when next button is clicked and draw to canvas
     */
    @FXML
    public void nextGeneration(){
        timeline.setRate(speedSlider.getValue());
        board.nextGeneration();
        board.drawGeneration();
        //graphics.drawGeneration(this.board.getGenerationList());
    }

    /**
     * Calls makeRandomGeneration method when button is clicked and draw to canvas
     */
    @FXML
    public void randomGeneration(){
        board.makeRandomGenerations();
        board.drawGeneration();
    }

    /**
     * Stop timeline animation
     */
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
    private void boardSizeObserver(){
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
    public void cellSizeObserver(){
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

          ne.printStackTrace();
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

    /**
     * Set the cell color
     */
    @FXML
    public void setCellColor(){
        board.setCellColor(cellColor.getValue());
        board.drawGrid();
        //graphics.drawBoard(this.board);
    }

    /**
     * Sets the background color
     */
    @FXML
    public void setBackgroundColor(){
        board.setBcColor(backgroundColor.getValue());
        board.drawGrid();
        //graphics.drawBoard(this.board);
    }

    /**
     * Sets the grid color
     */
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

    /**
     * Reset all colors to default settings
     */
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

    /**
     * Close application button
     */
    @FXML
    public void quitApp(){
        Platform.exit();
    }

    /**
     * Show information dialog box when info button is clicked
     */
    @FXML public void showInfo(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("About this application");
        alert.setHeaderText("WARNING!!!!\nBeware of lethal injections!\nPlay at your own risk!");
        alert.setContentText("Crafted & Coded by\nMagnus, Frode & Tommy\nHioA, Spring 2017");
        alert.showAndWait();
    }

    //    @FXML
//    public void handleURL(ActionEvent event){
//
//        listProperty.set(FXCollections.observableArrayList(urls));
//
//    }

//    public void initListViewSelector(){
//
//        myListView.setPrefWidth(200);
//        myListView.setPrefHeight(200);
//        urls.add("https://bitstorm.org/gameoflife/lexicon/cells/airforce.cells");
//        urls.add("http://www.conwaylife.com/patterns/gosperglidergun.cells");
//        urls.add("https://bitstorm.org/gameoflife/lexicon/cells/B-52_bomber.cells");
//        urls.add("https://bitstorm.org/gameoflife/lexicon/cells/beacon_maker.cells");
//        urls.add("https://bitstorm.org/gameoflife/lexicon/cells/big_glider.cells");
//        urls.add("https://bitstorm.org/gameoflife/lexicon/cells/bottle.cells");
//        urls.add("https://bitstorm.org/gameoflife/lexicon/cells/brain.cells");
//        urls.add("https://bitstorm.org/gameoflife/lexicon/cells/Cordership.cells");
//        urls.add("https://bitstorm.org/gameoflife/lexicon/cells/cow.cells");
//        urls.add("https://bitstorm.org/gameoflife/lexicon/cells/loaflipflop.cells");
//
//        myListView.itemsProperty().bind(listProperty);
//        listProperty.set(FXCollections.observableArrayList(urls));
//    }
}
