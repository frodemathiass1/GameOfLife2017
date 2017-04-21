package main.gol.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.gol.controller.util.Dialogs;
import main.gol.controller.util.Sounds;
import main.gol.model.Boards.FixedBoard;
import main.gol.model.Boards.TestBoards;
import main.gol.model.Cell;
import main.gol.model.FileManager.FileReader;
import main.gol.model.FileManager.URLReader;

import java.util.Optional;
import java.util.Random;


/**
 * The MainController class contains a series of functions that handles the GUI-elements of the Game of Life
 * application to make them do the things they're supposed to do.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.0
 */
public class MainController implements Initializable {


    // Internal GUI Objects
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
    private MenuItem small, normal, large, fileSelect, url1, url2, url3, url4, url5, url6, url7, url8, url9, url10;
    @FXML
    private ToggleButton toggleButton;


    private GraphicsContext gc;
    private Timeline timeline = new Timeline();
    private FixedBoard board;
    private URLReader urlReader;
    private int cellSize = 5;
    private int columns = 160;
    private int rows = 110;
    private TestBoards tb;
    private Sounds sound = new Sounds();
    private Dialogs dialog = new Dialogs();
    //private Draw draw = new Draw();


    /**
     * Init method for the application. Draws out a empty grid to canvas and initialize
     * timeline with keyframe animation, sets default color settings
     * and initializes the observable sliders and file/url selectors
     *
     * @param location  java..net.URL
     * @param resources java.util.ResourceBundle
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        //Open chooser at start for testing the files faster
        //FileReader reader = new FileReader();
        //reader.chooseFile();
        //reader.parseAndPopulateList();
        //reader.getInfo();

        //initTestBoard();
        makeDefaultBoard();
        initAnimation();
        setDefaultColors();
        cellSizeObserver();
        boardSizeObserver();
        fileSelect.setOnAction(e -> selectFile());
        handleWebBoards();
        //System.out.println(this.board.countNeighbours(0,0)); // for testing neighbors

        toggleButton.setOnAction(e -> {
            if (toggleButton.isSelected()) {
                sound.setVol(0.0);

            } else {
                sound.setVol(0.2);
            }
        });


    }


    /**
     * This method initializes the Default FixedBoard
     */
    public void makeDefaultBoard() {

        board = new FixedBoard(getGraphics(), cellSize);
        board.setBoard(columns, rows);
        board.drawGrid();
        //draw.drawGrid(board.getGrid(),getGraphics());
    }


    /**
     * Helper method, for setting up and drawing new board to canvas
     *
     * @param matrix  byte[][]
     * @param rows    int
     * @param columns int
     */
    public void makeNewBoard(byte[][] matrix, int rows, int columns) {

        board = new FixedBoard(getGraphics(), cellSize);
        board.setBoard(rows, columns);
        board.setBoard(matrix);
        board.drawGrid();
        //draw.drawGrid(board.getGrid(),getGraphics());
    }


    /**
     * Initialize and run GOL pattern from ContextMenu file Selector
     */
    @FXML
    public void selectFile() {

        stopAnimation();
        clearCanvas();

        FileReader fr = new FileReader();
        fr.chooseFile();
        fr.readGameBoardFromDisk(FileReader.getTheFile());

        makeNewBoard(fr.getMatrix(), fr.getRows(), fr.getColumns());
    }


    /**
     * Initialize and run GOL pattern from ContextMenu URL Selector
     */
    @FXML
    public void selectURL() {

        stopAnimation();
        //reset();

        urlReader = new URLReader();
        TextInputDialog inputURL = new TextInputDialog();
        inputURL.setTitle("Open URL");
        inputURL.setHeaderText("Enter the URL you want to open here");
        inputURL.setContentText("URL:");
        Optional<String> result = inputURL.showAndWait();

        if (result.isPresent()) {
            if (!result.get().toLowerCase().startsWith("http")) {
                Dialogs h = new Dialogs();
                h.httpError();
            } else {
                try {
                    urlReader.readGameBoardFromURL(result.get());
                    makeNewBoard(urlReader.getMatrix(), urlReader.getRows(), urlReader.getColumns());
                } catch (Exception e) {
                    Dialogs u = new Dialogs();
                    u.urlError();
                }
            }
        }
    }


    /**
     * ActionEvent handler for WebBoards in context menu
     */
    @FXML
    public void handleWebBoards() {

        url1.setOnAction(e -> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/airforce.cells"));
        url2.setOnAction(e -> initWebBoard("http://www.conwaylife.com/patterns/gosperglidergun.cells"));
        url3.setOnAction(e -> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/B-52_bomber.cells"));
        url4.setOnAction(e -> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/beacon_maker.cells"));
        url5.setOnAction(e -> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/big_glider.cells"));
        url6.setOnAction(e -> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/bottle.cells"));
        url7.setOnAction(e -> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/brain.cells"));
        url8.setOnAction(e -> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/Cordership.cells"));
        url9.setOnAction(e -> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/cow.cells"));
        url10.setOnAction(e -> initWebBoard("https://bitstorm.org/gameoflife/lexicon/cells/loaflipflop.cells"));

    }


    /**
     * Initialize WebBoard pattern
     *
     * @param url Takes url for valid GOL text/cells file patterns
     */
    public void initWebBoard(String url) {

        stopAnimation();
        clearCanvas();
        //reset();

        try {
            urlReader = new URLReader();
            urlReader.readGameBoardFromURL(url);
            makeNewBoard(urlReader.getMatrix(), urlReader.getRows(), urlReader.getColumns());
        } catch (Exception e) {
            Dialogs u = new Dialogs();
            u.urlError();
        }
    }


    /**
     * @deprecated init method for the test Board
     */
    public void initTestBoard() {

        board = new FixedBoard(gc, 20);
        TestBoards tb = new TestBoards();

        tb.findDimensions(tb.getTestBoard());
        board.setBoard(tb.getRows(), tb.getCols());
        this.board.setBoard(tb.getTestBoard());
        board.drawGrid();
        //draw.drawGrid(board.getGrid(),getGraphics());
    }


    /**
     * Returns the canvas graphic object (helper method)
     *
     * @return GraphicContext
     */
    public GraphicsContext getGraphics() {

        return gc = canvas.getGraphicsContext2D();
    }


    /**
     * Initialize timeline animation
     */
    public void initAnimation() {

        timeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(0.1), event -> nextGeneration());
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
    }


    /**
     * Set all colors to default settings
     */
    public void setDefaultColors() {

        cellColor.setValue(Color.BLACK);
        backgroundColor.setValue(Color.WHITE);
        gridColor.setValue(Color.LIGHTGRAY);
    }


    /**
     * Calls next generation when next button is clicked and draw to canvas
     */
    @FXML
    public void nextGeneration() {

        timeline.setRate(speedSlider.getValue());
        board.nextGeneration();
        board.drawGeneration();

        //draw.drawGeneration(board.getGenerationList(),getGraphics());
    }


    /**
     * Calls makeRandomGeneration method when button is clicked and draw to canvas
     */
    @FXML
    public void mysteryButton() {

        sound.setVol(0.0);
        stopAnimation();
        clearCanvas();

        board.setBoard((int) canvas.getWidth() / 3, (int) canvas.getHeight() / 3);
        board.setCellSize(3);

        board.makeRandomGenerations();
        board.drawGeneration();

        speedSlider.setValue(speedSlider.getMax());
        setRandomColor();
        play();
        sound.setVol(0.2);
        sound.play(sound.getLaser());

        //draw.drawGeneration(board.getGenerationList(),getGraphics());
    }


    /**
     * Stop timeline animation
     */
    public void stopAnimation() {

        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
            play.setText("Play");
        }
    }


    /**
     * Clear canvas GUI graphics
     */
    public void clearCanvas() {

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


    /**
     * Action event handler for GridSize settings
     */
    @FXML
    private void boardSizeObserver() {

        small.setOnAction(e -> {

            stopAnimation();
            board.setCellSize(5);
            board.setBoard(160, 110);
            zoomSlider.setValue(5); //Set slider to same cell value
            board.drawGrid();
            //draw.drawGrid(board.getGrid(),getGraphics());
        });

        normal.setOnAction(e -> {
            stopAnimation();
            board.setCellSize(10);
            board.setBoard(80, 55);
            zoomSlider.setValue(10); //Set slider to same cell value
            board.drawGrid();
            //draw.drawGrid(board.getGrid(),getGraphics());
        });

        large.setOnAction(e -> {
            stopAnimation();
            board.setCellSize(20);
            board.setBoard(40, 28);
            zoomSlider.setValue(20); //Set slider to same cell value
            board.drawGrid();
            //draw.drawGrid(board.getGrid(),getGraphics());
        });
    }


    /**
     * This method observes the zoomSlider values and updates the canvas accordingly.
     */
    public void cellSizeObserver() {

        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            board.setCellSize(newValue.intValue());
            clearCanvas();
            board.drawGrid();// Need this to update the board live....
            //draw.drawGrid(board.getGrid(),getGraphics());
        });
    }


    /**
     * This method handle Cell coordinates of canvas by mouseEvents.
     * Draw, erase, toggle cells in editor by click, drag / double click + drag.
     *
     * @param event MouseEvent
     */
    @FXML
    public void handleMouseEvent(MouseEvent event) {

        try {
            double x = event.getX(); // mouse x pos
            double y = event.getY(); // mouse y pos

            // Find cell position in board cells array
            // Round down event coordinates to integer, divide it with cellSize to get exact canvas position
            int cellPosX = (int) Math.floor(x / board.getCellSize());
            int cellPosY = (int) Math.floor(y / board.getCellSize());

            // Get cell
            Cell cell = board.getCell(cellPosX, cellPosY);

            // Toggle alive
            boolean toggleState = !cell.getState();

            // For smooth drawing
            if (toggleState) {
                cell.setState(toggleState);
            }

            // Double click and drag to smooth erase
            if (event.getClickCount() > 1) {
                toggleState = false;
                cell.setState(toggleState);
            }

            // Draw the cell
            this.board.drawCell(cell);
            //draw.drawCell(cell,getGraphics());
        } catch (NullPointerException ne) {

            ne.printStackTrace();
        }
    }


    /**
     * This method Toggle animation and buttonText
     */
    @FXML
    public void play() {


        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
            play.setText("Play");

            sound.play(sound.getBeep2());
        } else if (timeline.getStatus() == Animation.Status.STOPPED) {
            timeline.play();
            play.setText("Stop");

            sound.play(sound.getBeep1());
        }
    }


    /**
     * This method is controller the reset button.
     * Clears the editor-windows and reset grid settings.
     */
    @FXML
    public void reset() {

        timeline.stop();
        clearCanvas();
        resetColor();
        speedSlider.setValue(1.5);

        board.clearBoard(board.getGrid());

        board.setBoard(160, 110);
        board.setCellSize(5);
        board.drawGrid();
        //draw.drawGrid(board.getGrid(),getGraphics());

        zoomSlider.setValue(cellSize);
        play.setText("Play");

        sound.play(sound.getRattle());
    }


    /**
     * Sets the cell color
     */
    @FXML
    public void setCellColor() {

        board.setCellColor(cellColor.getValue());
        board.drawGrid();
        //draw.drawGrid(board.getGrid(),getGraphics());
    }


    /**
     * Sets the background color
     */
    @FXML
    public void setBackgroundColor() {

        board.setBcColor(backgroundColor.getValue());
        board.drawGrid();
        //draw.drawGrid(board.getGrid(),getGraphics());
    }


    /**
     * Sets the grid color
     */
    @FXML
    public void setGridColor() {

        board.setGridColor(gridColor.getValue());
        board.drawGrid();
        //draw.drawGrid(board.getGrid(),getGraphics());
    }


    /**
     * This method sets and executes random colors to the Cells, GridLine and Background
     */
    @FXML
    public void setRandomColor() {

        Random rand = new Random();
        Color randCellColor = Color.rgb(rand.nextInt(175), rand.nextInt(255), rand.nextInt(175));
        Color randBcColor = Color.rgb(rand.nextInt(175), rand.nextInt(255), rand.nextInt(175));
        Color randGridColor = Color.rgb(rand.nextInt(175), rand.nextInt(255), rand.nextInt(175));

        // Set all colors to random
        board.setCellColor(randCellColor);
        board.setBcColor(randBcColor);
        board.setGridColor(randGridColor);

        // Update the color picker to correct color
        cellColor.setValue(randCellColor);
        backgroundColor.setValue(randBcColor);
        gridColor.setValue(randGridColor);

        board.drawGrid();
        //draw.drawGrid(board.getGrid(),getGraphics());
    }


    /**
     * Reset all colors to default settings
     */
    @FXML
    public void resetColor() {

        // Reset all colors to original value
        board.setCellColor(Color.BLACK);
        board.setBcColor(Color.WHITE);
        board.setGridColor(Color.LIGHTGRAY);

        // Update the color picker to correct color
        cellColor.setValue(Color.BLACK);
        backgroundColor.setValue(Color.WHITE);
        gridColor.setValue(Color.LIGHTGRAY);
        board.drawGrid();
        //draw.drawGrid(board.getGrid(),getGraphics());
        //graphics.drawBoard(this.board);
    }


    /**
     * Close application button
     */
    @FXML
    public void closeApp() {
        Platform.exit();
    }


    /**
     * Show information dialog box when info button is clicked
     */
    @FXML
    public void showInfo() {

        dialog.showInfo();
    }

    @FXML
    public void aboutGol() {

        dialog.aboutGol();
    }

    // Getters
    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }


}
