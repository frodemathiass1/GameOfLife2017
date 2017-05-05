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
import javafx.scene.media.MediaException;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.gol.controller.util.Colors;
import main.gol.controller.util.Dialogs;
import main.gol.controller.util.Draw;
import main.gol.controller.util.Sound;
import main.gol.model.boards.Config;
import main.gol.model.boards.DynamicBoard;
import main.gol.model.Cell;
import main.gol.model.filemanager.*;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * The GUIController contains a series of functions that handles the GUI-elements and their
 * actionEvents to make the application do what its supposed to do.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.4
 */
public class GUIController implements Initializable {

    @FXML private MenuItem url1, url2, url3, url4, url5, url6, url7, url8, url9, url10;
    @FXML private MenuItem file1, file2, file3, file4, file5, file6, file7, file8, file9, file10, fileBlock;
    @FXML private MenuItem small, medium, large, largest;
    @FXML private ColorPicker cpCell, cpGrid, cpBackground;
    @FXML private Slider speedSlider,zoomSlider;
    @FXML private ToggleButton play, toggleSound;
    @FXML private Label speedIcon;
    @FXML private Label zoomIcon;
    @FXML private Button next;
    @FXML private Canvas canvas;
    @FXML private Menu fileInfo;

    //private FixedBoard fb;
    private DynamicBoard board;
    private GraphicsContext context;
    private Timeline timeline;
    private Dialogs dialog;
    private Sound sound;
    private Config config;
    private Colors colors;
    private Draw draw;
    private FileHandler fileHandler;
    private URLHandler urlHandler;
    private BoardParser boardParser;

    /**
     * This method is overridden from initializable interface.
     * Spits out the the default gameBoard and initialize the observable GUI components,
     * and instantiates needed objects for controller handling.
     *
     * @param location  java..net.URL
     * @param resources java.util.ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            // Initialize needed objects
            initializeObjects();

           // Set default gameBoard and draw it to canvas
            board = new DynamicBoard(config.getRows(), config.getColumns());
            //board.setCellState(200, 200, true); // Set cellState outside array bounds and fill empty slots
            draw.drawBoard(board.getGrid());

            // Initialize animation timeline.
            timeline = new Timeline();
            KeyFrame kf = new KeyFrame(Duration.seconds(0.1), event -> {
                timeline.setRate(speedSlider.getValue());
                board.nextGeneration();
                draw.drawGeneration(board.getGeneration());
            });
            timeline.getKeyFrames().add(kf);
            timeline.setCycleCount(Timeline.INDEFINITE);

            // Initialize observable gui components & fire off a pattern
            initializeObservables();

        } catch(Exception e) {
            e.printStackTrace();
            System.err.println("Oops. Something went wrong when firing up the application!");
            System.out.println(e.getMessage());
            dialog.oops();
            quit();
        }
    }

    //================================================================================
    // Container functions for initializing objects and observable components
    //================================================================================

    /**
     * Wrapper: This method is a wrapper/container for instantiating needed objects.
     */
    private void initializeObjects() {

        config = new Config();
        colors = new Colors();
        draw = new Draw(getContext(), colors);
        dialog = new Dialogs();
        sound = new Sound();
    }

    /**
     * Wrapper: This method is a wrapper/container method for initializing the observable GUI components.
     */
    private void initializeObservables() {

        togglePlay();                   // Initialize play
        handleGridSizeEvents();         // Initialize "Select drawBoard size" menu. Select Size on GUI.
        handlePatternSelector();        // Initialize "Select pattern" menu. Select pattern on GUI
        handleZoomSlider();             // Initialize Zoom slider. Zoom by changing CellSize
        handleToggleSound();            // Initialize soundToggleButton. Toggle Sound on/off by
        updateColorPickerValues();
    }

    //================================================================================
    // Functions related to setting up new boards from : Files, URL's , byte[][]
    //================================================================================

    /**
     * This method create new boards by parsed from files, strings, or what3ever.
     * Sets up a new board with byte[][] arrays as inputParameter.
     *
     * @param board byte[][]
     */
    private void newBoard(byte[][] board) {

        try {
            this.board = new DynamicBoard(config.getRows(), config.getColumns());
            this.board.setBoard(board);
            //this.board.setCellState(6, 8, true); // Expand board
            draw.drawBoard(this.board.getGrid());
            zoomSlider.setValue(zoomSlider.getMin());

        } catch (NullPointerException ne) {
            System.err.println("NullPointer Exception!");
            ne.printStackTrace();

        } catch (ArrayIndexOutOfBoundsException oob) {
            System.err.println("ArrayIndex out of bounds!");
            oob.printStackTrace();
        }
    }

    /**
     *  ToDo: lage egen styleClass og sette Bold font i CSS fila + javadoc
     */
    private void setFileInfo() {

        // Show the file info.
        Decoder info = new Decoder();
        fileInfo.setDisable(false);
        fileInfo.setText("(" + info.getTheName() + ")");
        fileInfo.setStyle("-fx-font-weight: bold;");

    }

    /**
     * This method runs the files that are loaded from the File/Patterns menu-selection
     */
    private void runFile()  {

        try {
            fileHandler = new FileHandler();
            boardParser = new BoardParser();

            // Get correct file type, and parse to BoardParser.
            if (fileHandler.getTheFileType().equals("RLE File")) {
                // Instantiate a new temp file, and delete it after use.
                File temp = new File("temp.gol");
                boardParser.readAndParseFile(temp);
                temp.delete();
            } else if (fileHandler.getTheFileType().equals("Text File")) {
                // Plaintext files is parsed directly.
                boardParser.readAndParseFile(fileHandler.getTheFile());
            }
            // Reset the old board
            board.clearBoard();
            updateColorPickerValues();
            // Draw the new board.
            newBoard(boardParser.getTheBoard());
            setFileInfo();
            largest.fire();
            zoomSlider.setValue(1);

        } catch (Exception e) {
            dialog.fileError();
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method runs the URL's that are loaded from the File/Patterns menu-selection
     *
     * @param url String
     */
    private void runURL(String url) {

        try {
            urlHandler = new URLHandler();
            boardParser = new BoardParser();
            urlHandler.selectUrlType(url);

            if (urlHandler.getUrlType().equals("RLE Url")) {
                // Instantiate a new temp file, and delete it after use.
                File temp = new File("temp.gol");
                boardParser.readAndParseFile(temp);
                temp.delete();
            } else if (urlHandler.getUrlType().equals("Text Url")) {
                // Plaintext URLs is parsed directly.
                boardParser.readAndParseURL(url);
            }
            // Reset the old board.
            board.clearBoard();
            updateColorPickerValues();
            // Draw the new board.
            newBoard(boardParser.getTheBoard());
            setFileInfo();
            largest.fire();
            zoomSlider.setValue(1);


        } catch (Exception e) {
            dialog.urlError();
            System.err.println("Something went wrong reading the URL.");
        }
    }

    /**
     * Event Handlers "Patterns" menu-button. Set predefined boards from project resources.
     */
    @FXML
    private void handlePatternSelector() {

        // URL actions PlainText URLs
        url1.setOnAction(e -> handleURL("http://www.conwaylife.com/patterns/airforce.cells"));
        url2.setOnAction(e -> handleURL("http://www.conwaylife.com/patterns/b52bomber.cells"));
        url3.setOnAction(e -> handleURL("http://www.conwaylife.com/patterns/backrake1.cells"));
        url4.setOnAction(e -> handleURL("http://www.conwaylife.com/patterns/beaconmaker.cells"));
        url5.setOnAction(e -> handleURL("http://www.conwaylife.com/patterns/bigglider.cells"));
        // URL actions RLE URLs
        url6.setOnAction(e -> handleURL("http://www.conwaylife.com/patterns/3enginecordership.rle"));
        url7.setOnAction(e -> handleURL("http://www.conwaylife.com/patterns/blinkership1.rle"));
        url8.setOnAction(e -> handleURL("http://www.conwaylife.com/patterns/brain.rle"));
        url9.setOnAction(e -> handleURL("http://www.conwaylife.com/patterns/chemist.rle"));
        url10.setOnAction(e -> handleURL("http://www.conwaylife.com/patterns/jolson.rle"));
        // File actions PlainText files
        file1.setOnAction(e -> handleFile("resources/patterns/candelabra.cells"));
        file2.setOnAction(e -> handleFile("resources/patterns/candlefrobra.cells"));
        file3.setOnAction(e -> handleFile("resources/patterns/carnival_shuttle.cells"));
        file4.setOnAction(e -> handleFile("resources/patterns/158P3.cells"));
        file5.setOnAction(e -> handleFile("resources/patterns/cow.cells"));
        fileBlock.setOnAction(e -> {
            handleFile("resources/patterns/block.txt");
            Decoder.getTheName("A BIG BLOCK!");
            Decoder.setOrigin("Tommy Pedersen");
            Decoder.setContent("This block was created by one of the coders for this project.");
            Decoder.setLink("Not published on internet");
        });
        // File actions RLE files
        file6.setOnAction(e -> handleFile("resources/patterns/mirage.rle"));
        file7.setOnAction(e -> handleFile("resources/patterns/loaflipflop.rle"));
        file8.setOnAction(e -> handleFile("resources/patterns/pinwheel.rle"));
        file9.setOnAction(e -> handleFile("resources/patterns/ringoffire.rle"));
        file10.setOnAction(e -> handleFile("resources/patterns/sailboat.rle"));
    }

    /**
     * This method create boards with a valid URL string as inputParameter.
     * Takes url for valid GOL .txt/.cells file patterns.
     *
     * @param url String
     */
    private  void handleURL(String url) {

        runURL(url);
    }

    /**
     * This method create boards with a valid path string as inputParameter.
     * Takes String filepath for valid GOL .txt/.cells file patterns.
     *
     * @param path String
     */
    private void handleFile(String path) {

        File file = new File(path);
        fileHandler = new FileHandler();
        fileHandler.setTheFile(file);
        fileHandler.fileSelectType(file);
        runFile();
    }

    /**
     * EventHandler "Open File" menu-button.
     * Sets a new board by selecting a file, parsing it to the correct decoder and creating a new board.
     */
    @FXML
    public void loadFileFromDisk() {

        fileHandler = new FileHandler();
        fileHandler.chooseAndSelectType();
        runFile();
        largest.fire();
        zoomSlider.setValue(1);
    }

    /**
     * EventHandler "Open URL" menu-button.
     * Sets a new board by getting the URL you type, parsing it to the correct decoder and creating a new board.
     */
    @FXML
    public void loadFileFromURL() {

        // Opens a new input dialog window for URLs
        TextInputDialog inputURL = new TextInputDialog();
        inputURL.setTitle("Open URL");
        inputURL.setHeaderText("Resources: http://conwaylife.com/wiki/Category:Patterns");
        inputURL.setContentText("Enter URL here");
        Optional<String> result = inputURL.showAndWait();

        if (result.isPresent()) {
            // URLs must start with http for this method to work.
            if (!(result.get().toLowerCase().startsWith("http"))) {
                dialog.httpError();
            } else {
                runURL(result.get());
            }
        }
    }

    //================================================================================
    // Functions related to Play, Reset and Bomb Buttons
    //================================================================================

    /**
     * EventHandler: "Next" button. Draw the next generation of cells(no animation).
     * @throws MediaException me
     */
    @FXML
    public void nextStep() throws MediaException {

        board.nextGeneration();
        draw.drawGeneration(board.getGeneration());
        int cellsCounter = board.getGeneration().size();

        if (cellsCounter >= 0) {
            sound.play(sound.getFx3());
            next.setText("Cells "+ cellsCounter);
        } else {
            next.setText("Next");
        }
    }

    /**
     * This method stop timeline animation if its running and changes the buttonText
     */
    private void stopAnimationIfRunning() {

        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
            play.setText("Play");
            play.setSelected(false);
        }
    }

    /**
     * EventHandler: "Reset" button.
     * Stop animation and clear the editor-window of living cells.
     * Reset settings to default and play a oneshot fx sound.
     */
    @FXML
    public void reset() {

        stopAnimationIfRunning();
        colors.resetAll();
        speedSlider.setValue(1.5);
        board.clearBoard();
        config.setDefault();
        board = new DynamicBoard(config.getRows(), config.getColumns());
        draw.drawBoard(board.getGrid());
        zoomSlider.setValue(speedSlider.getMin());
        play.setText("Play");
        next.setText("Next");
        fileInfo.setDisable(true);
        fileInfo.setText("");
        sound.play(sound.getFx8());
    }

    /**
     * EventHandler: "Bomb Icon" button.
     * Fills the Board with random live cells to simulate a MadManPacMan sorta game."Cell consumer".
     */
    @FXML
    public void handleTheBomber() {

        board.clearBoard();
        try {
            setRandomColor();

        } catch (Exception e) {
            e.printStackTrace();
        }
        updateColorPickerValues();
        // This combination of calls enables the MadManPacMan sort of Game simulation.
        draw.drawBoard(board.getGrid());
        board.randomize();
        draw.drawGeneration(board.getGeneration());
        board.nextGeneration();
        speedSlider.setValue(speedSlider.getMax());
    }

    //================================================================================
    // Functions related to Buttons and sliders for speed, sound and size
    //================================================================================

    /**
     * EventHandler: "Select Grid size" MenuButton
     */
    @FXML
    private void handleGridSizeEvents() {

        stopAnimationIfRunning();

        largest.setOnAction(e -> {
            config.setCellSize(5);
            draw.drawBoard(board.getGrid());
        });
        large.setOnAction(e -> {
            config.setCellSize(15);
            draw.drawBoard(board.getGrid());
        });
        medium.setOnAction(e -> {
            config.setCellSize(30);
            draw.drawBoard(board.getGrid());
        });
        small.setOnAction(e -> {
            config.setCellSize(40);
            draw.drawBoard(board.getGrid());
        });
    }

    /**
     * EventHandler/Listener: "Zoom slider". Handles the slider listener.
     */
    private void handleZoomSlider() {

        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setScaleX(newValue.intValue());
            canvas.setScaleY(newValue.intValue());
        });
    }

    /**
     * EventHandler: "Sound icon". Toggle sound.
     */
    private void handleToggleSound() {

        toggleSound.setOnAction(e -> {
            if (toggleSound.isSelected()) {
                sound.play(sound.getPop3());
                sound.setVol(0.0);
            } else {
                sound.setVol(0.2);
                sound.play(sound.getPop1());
            }
        });
    }

    /**
     * EventHandler: "Play/Stop Button".
     * Toggle animation, change buttonText and play a one shot sound when triggered.
     */
    private void togglePlay() {

        play.setOnAction((event -> {
            if (timeline.getStatus() == Animation.Status.RUNNING) {
                timeline.stop();
                play.setText("Play");
                sound.play(sound.getPop2());
            } else if (timeline.getStatus() == Animation.Status.STOPPED) {
                timeline.play();
                play.setText("Stop");
                sound.play(sound.getPop1());
            }
        }));
    }

    /**
     * Event Handler "Zoom icon". Flip max/min zoom values.
     */
    @FXML
    public void flipZoom() {

        zoomIcon.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                zoomSlider.setValue(3);
            } else if (event.getClickCount() == 2) {
                zoomSlider.setValue(1);
            }
        });
    }

    /**
     * Event Handler: "Speed icon". Flip max/min animation rate
     */
    @FXML
    public void flipSpeed() {

        speedIcon.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                speedSlider.setValue(speedSlider.getMax());
            } else if (event.getClickCount() == 2) {
                speedSlider.setValue(speedSlider.getMin());
            }
        });
    }

    //================================================================================
    // Functions related to Mouse events
    //================================================================================

    /**
     * EventHandler:
     * <p>
     * This method handle Cell coordinates of canvas by mouseEvents and the Logic of placing cells
     * correct on the screen and sceneGraph node canvas.
     * <p>
     * Draw, erase, and toggle cells in editor by MouseGestures:
     * <p>
     * Drag: Draw cells.
     * LeftClick : Toggle Cell alive / Dead.
     * DoubleClick (Hold button and Move support):  Erase one or multiple Cells.
     *
     * @param event MouseEvent
     */
    @FXML
    public void handleMouseEvent(MouseEvent event) {

        try {
            double x = event.getX(); // mouse x pos
            double y = event.getY(); // mouse y pos

            // Find Cell position in board Cell drawBoard array
            int cellPosX = (int) Math.floor(x / config.getCellSize());
            int cellPosY = (int) Math.floor(y / config.getCellSize());

            // Get Cell
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
            draw.drawCell(cell);

        } catch (NullPointerException ne) {
            // Do nothing!
        }
    }

    //================================================================================
    // Methods related to Graphics and Color settings
    //================================================================================

    /**
     * This method returns the Canvas graphicsContext(Helper method for Draw calls).
     *
     * @return GraphicContext
     */
    private GraphicsContext getContext() {

        context = canvas.getGraphicsContext2D();
        return context;
    }

    /**
     * EventHandler: "ColorPicker Cell". Set cell-color from ColorPicker.
     */
    @FXML
    public void setCellColor() {

        colors.setCell(cpCell.getValue());
        draw.drawBoard(board.getGrid());
        updateColorPickerValues();
    }

    /**
     * EventHandler: "ColorPicker Grid". Set grid-color from ColorPicker.
     */
    @FXML
    public void setGridColor() {

        colors.setGridLine(cpGrid.getValue());
        draw.drawBoard(board.getGrid());
        updateColorPickerValues();
    }

    /**
     * EventHandler: "ColorPicker: Background". Set background-color from ColorPicker.
     */
    @FXML
    public void setBackgroundColor() {

        colors.setBackground(cpBackground.getValue());
        draw.drawBoard(board.getGrid());
        updateColorPickerValues();
    }

    /**
     * EventHandler: "Random color" menu-button. Set random colors to Cells, GridLine and Background.
     */
    @FXML
    public void setRandomColor() {

        Random rand = new Random();
        int b = 255; // int bounder
        Color cell = Color.rgb(rand.nextInt(b), rand.nextInt(b), rand.nextInt(b));
        Color grid = Color.rgb(rand.nextInt(b), rand.nextInt(b), rand.nextInt(b));
        Color background = Color.rgb(rand.nextInt(b), rand.nextInt(b), rand.nextInt(b));

        // Set Random colors
        colors.setCell(cell);
        colors.setGridLine(grid);
        colors.setBackground(background);

        updateColorPickerValues();
        draw.drawBoard(board.getGrid());

        sound.play(sound.getFx2());
    }

    /**
     * EventHandler: "Reset color" menu-button. Reset all colors to default settings.
     */
    @FXML
    public void resetColor() {
        
        colors.resetAll();
        updateColorPickerValues();
        draw.drawBoard(board.getGrid());

        try {
            sound.play(sound.getFx8());
        } catch (MediaException me) {
            dialog.audioError();
        }
    }

    /**
     * This method updates the colorPicker widget values.
     */
    private void updateColorPickerValues() {

        cpCell.setValue(colors.getCell());
        cpBackground.setValue(colors.getBackground());
        cpGrid.setValue(colors.getGridLine());
    }

    //================================================================================
    // Functions related to the MenuBar-buttons and Information Dialogs
    //================================================================================

    /**
     * EventHandler: "FileInfo". Shows the dialog box with the loaded file information.
     */
    @FXML
    public void showFileInfo() {
        dialog.fileInfo();
    }

    /**
     *
     * EventHandler: "Info" menu-button. Show information dialog box when info button is clicked.
     */
    @FXML
    public void showInfo() {
        dialog.showInfo();
    }

    /**
     *
     * EventHandler: "About Game Of Life" menu-button. Show information dialog box with Game of Life rules and description.
     */
    @FXML
    public void aboutGameOfLife() {
        dialog.aboutGol();
    }

    /**
     * EventHandler: "How to play" menu-button. "Show information dialog box about how to play the game.
     */
    @FXML
    public void howToPlay() {
        dialog.howToPlay();
    }

    /**
     * EventHandler: "Quit" menu-button". Exit the application.
     */
    @FXML
    public void quit() {

        try {
            sound.play(sound.getFx3());
        } catch (MediaException me) {
              dialog.audioError();
        }
        Platform.exit();
    }
}
