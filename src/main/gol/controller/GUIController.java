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
 * The GUIController class contains a series of functions that handles the GUI-elements and events
 * to make them do the things they're supposed to do.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.2
 */
public class GUIController implements Initializable {

    @FXML private MenuItem f1, f2, f3, f4, f5, f6, f7, f8, f9, f10;
    @FXML private Label speedIcon;
    @FXML private Label zoomIcon, getSpeedIcon;
    @FXML private Slider speedSlider;
    @FXML private Button next;
    @FXML private ColorPicker cpCell, cpGrid, cpBackground;
    @FXML private Canvas canvas;
    @FXML private Slider zoomSlider;
    @FXML private MenuItem small, normal, large, mega, url1, url2, url3, url4, url5, url6, url7, url8, url9, url10;
    @FXML private ToggleButton play, toggleSound;

    private GraphicsContext context;
    private DynamicBoard board;
    //private FixedBoard fb;
    private Timeline timeline;
    private Dialogs dialog;
    private Sound sound;
    private Config config;
    private Colors colors;
    private Draw draw;
    private int cellsCounter = 0;

    /**
     * This method initializes the default gameBoard with it's default color settings.
     * It also initializes animation, observable components such as sliders, file/url selections and buttons.
     *
     * @param location  java..net.URL
     * @param resources java.util.ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            togglePlay();
            initializeGraphicsAndSounds();  // Initialize Dialogs, Sound, and GraphicsContext
            // FIXED BOARD TESTING
            //fb = new FixedBoard(50,50);
            //draw.drawFixedBoard(fb.getGrid(), getContext());

           // Set Default gameBoard and draw to canvas*
            board = new DynamicBoard(config.getRows(), config.getColumns());
            board.setCellState(200, 200, true); // Set cellState outside array bounds and fill empty slots
            draw.drawBoard(board.getGrid());

            // Initialize animation timeline.
            timeline = new Timeline();
            KeyFrame kf = new KeyFrame(Duration.seconds(0.1), event -> {
                timeline.setRate(speedSlider.getValue());
                board.nextGeneration();
                draw.drawGeneration(board.getGeneration());
                //FIXED BOARD ANIMATION TESTING
                //fb.nextGeneration();
                //draw.drawGeneration(fb.getGeneration(), getContext());
            });
            timeline.getKeyFrames().add(kf);
            timeline.setCycleCount(Timeline.INDEFINITE);

            initializeObservables(); // Init observable gui components

            //url8.fire();
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println("Oops. Something went wrong when firing up the application!");
            dialog.oops();
            quit();
        }
    }

    //================================================================================
    // Container functions for initializing objects and observable components
    //================================================================================

    /**
     * This method is a wrapper method for instantiating needed objects
     */
    public void initializeGraphicsAndSounds() {

        config = new Config();
        colors = new Colors();
        draw = new Draw(getContext(), colors);
        dialog = new Dialogs();
        sound = new Sound();
    }

    /**
     * This method is a wrapper method for initializing the observable GUI components
     */
    public void initializeObservables() {

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
     * API: Create boards by parsed by files, strings, or what3ever
     *
     * Sets up a new board with byte[][] arrays as inputParameter
     *
     * @param board byte[][]
     */
    public void newBoard(byte[][] board) {

        try {
            this.board = new DynamicBoard(config.getRows(), config.getColumns());
            this.board.setGrid(board);
            draw.drawBoard(this.board.getGrid());
            //fb = new FixedBoard(50,50);
            //fb.setGrid(board);
            //draw.drawFixedBoard(fb.getGrid());
        } catch (NullPointerException ne) {
            System.err.println("NullPointer Exception!");
            ne.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException oob) {
            System.err.println("ArrayIndex out of bounds!");
            oob.printStackTrace();
        }
    }

    /**
     * API: Create boards with a valid URL string as inputParameter.
     * Takes url for valid GOL .txt/.cells file patterns.
     *
     * @param url
     */
    public void handleURL(String url) {

        try {
            BoardParser bp = new BoardParser();
            bp.ParseURL(url);
            // Reset the old board.
            reset();
            updateColorPickerValues();
            // Draw the new board.
            newBoard(bp.getTheBoard());
        } catch (Exception e) {
            dialog.urlError();
            System.err.println("Something went wrong reading the URL.");
        }
    }

    /**
     * API: Create boards with a valid path string as inputParameter.
     * Takes String filepath for valid GOL .txt/.cells file patterns.
     *
     * @param path String
     */
    public void handleFile(String path) {

        try {
            File file = new File(path);
            BoardParser bp = new BoardParser();
            bp.ParseFile(file);
            // Reset the old board.
            reset();
            updateColorPickerValues();
            // Draw the new board.
            newBoard(bp.getTheBoard());
        } catch (Exception ex) {
            dialog.notFoundException();
        }
    }

    /**
     * ActionEvent Handlers "Pattern Select Menu"
     * Add internal GUI FXML objects with fx:id attributes in FXML document
     * for expanding the predefined patterns menu
     */
    @FXML
    public void handlePatternSelector() {

        // URL actions
        url1.setOnAction(e -> handleURL("https://bitstorm.org/gameoflife/lexicon/cells/airforce.cells"));
        url2.setOnAction(e -> handleURL("http://www.conwaylife.com/patterns/gosperglidergun.cells"));
        url3.setOnAction(e -> handleURL("https://bitstorm.org/gameoflife/lexicon/cells/B-52_bomber.cells"));
        url4.setOnAction(e -> handleURL("https://bitstorm.org/gameoflife/lexicon/cells/beacon_maker.cells"));
        url5.setOnAction(e -> handleURL("https://bitstorm.org/gameoflife/lexicon/cells/big_glider.cells"));
        url6.setOnAction(e -> handleURL("https://bitstorm.org/gameoflife/lexicon/cells/bottle.cells"));
        url7.setOnAction(e -> handleURL("https://bitstorm.org/gameoflife/lexicon/cells/brain.cells"));
        url8.setOnAction(e -> handleURL("https://bitstorm.org/gameoflife/lexicon/cells/Cordership.cells"));
        url9.setOnAction(e -> handleURL("https://bitstorm.org/gameoflife/lexicon/cells/cow.cells"));
        url10.setOnAction(e -> handleURL("https://bitstorm.org/gameoflife/lexicon/cells/loaflipflop.cells"));
        // File actions
        f1.setOnAction(e -> handleFile("patterns/candelabra.cells"));
        f2.setOnAction(e -> handleFile("patterns/candlefrobra.cells"));
        f3.setOnAction(e -> handleFile("patterns/carnival_shuttle.cells"));
        f4.setOnAction(e -> handleFile("patterns/caterer.cells"));
        f5.setOnAction(e -> handleFile("patterns/centinal.cells"));
        f6.setOnAction(e -> handleFile("patterns/chemist.cells"));
        f7.setOnAction(e -> handleFile("patterns/chicken_wire2.cells"));
        f8.setOnAction(e -> handleFile("patterns/clock_II.cells"));
        f9.setOnAction(e -> handleFile("patterns/Cordership.cells"));
        f10.setOnAction(e -> handleFile("patterns/spaceship.cells"));
    }

    /**
     * ActionEvent Handler "Open File"
     * Sets a new board by selecting a file, parsing it to the correct decoder and creating a new board.
     */
    @FXML
    public void loadFileFromDisk() throws Exception {

        try {
            // Get correct file type, and parse to BoardParser.
            FileHandler fh = new FileHandler();
            BoardParser bp = new BoardParser();
            fh.fileSelect();
            if (fh.getTheFileType().equals("RLE File")) {
                // Instantiate a new temp file, and delete it after use.
                File temp = new File("temp.gol");
                bp.ParseFile(temp);
                temp.delete();
            } else if (fh.getTheFileType().equals("Text File")) {
                // Plaintext files is parsed directly.
                bp.ParseFile(fh.getTheFile());
            }
            // Reset the old board.
            reset();
            updateColorPickerValues();
            // Draw the new board.
            newBoard(bp.getTheBoard());
        } catch (Exception e) {
            dialog.fileError();
            System.err.println("Error: " + e);
        }
    }

    /**
     * ActionEvent Handler "Open URL"
     * Sets a new board by getting the URL you type, parsing it to the correct decoder and creating a new board.
     */
    @FXML
    public void loadFileFromURL() throws Exception {

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
                System.err.println("Wrong URL");
            } else {
                try {
                    // Get correct URL type, and parse to BoardParser.
                    BoardParser bp = new BoardParser();
                    URLHandler uh = new URLHandler();
                    uh.selectUrlType(result.get());
                    System.out.println(result.get());
                    if (uh.getUrlType().equals("RLE Url")) {
                        // Instantiate a new temp file, and delete it after use.
                        File temp = new File("temp.gol");
                        bp.ParseFile(temp);
                        temp.delete();
                    } else if (uh.getUrlType().equals("Text Url")) {
                        // Plaintext URLs is parsed directly.
                        bp.ParseURL(result.get());
                    }
                    // Reset the old board.
                    reset();
                    updateColorPickerValues();
                    // Draw the new board.
                    newBoard(bp.getTheBoard());
                } catch (Exception e) {
                    dialog.urlError();
                    System.err.println("Error trying to read URL");
                }
            }
        }
    }

    //================================================================================
    // Functions related to Play, Reset and Bomb Buttons
    //================================================================================

    @FXML
    public void nextStep() throws MediaException {

        board.nextGeneration();
        draw.drawGeneration(board.getGeneration());

        // FOR TESTING NEXT GENERATION ON FIXED BOARDS
        //fb.nextGeneration();
        //draw.drawGeneration(fb.getGeneration(), getContext());

        cellsCounter = board.getGeneration().size();
        //cellsCounter = fb.getGeneration().size();
        if (cellsCounter >= 0) {
            sound.play(sound.getFx3());
            next.setText("Cells "+ cellsCounter);
        } else {
            next.setText("Next");
        }
    }

    /**
     * ActionEvent Handler for "Play/Stop Button" on GUI.This method
     * Toggles animation , changes buttonText and plays a one shot sound when triggered.
     */
//    @FXML
//    public void toggleAnimation() throws IOException {
//
//
//        if (timeline.getStatus() == Animation.Status.RUNNING) {
//            timeline.stop();
//            play.setText("Play");
//            sound.play(sound.getPop2());
//
//        } else if (timeline.getStatus() == Animation.Status.STOPPED) {
//            timeline.play();
//            play.setText("Stop");
//            //sound.play(sound.getPop1(),0.2);
//            sound.play(sound.getPop1());
//        }
//    }

    /**
     * Helper method
     * Stop timeline animation and change buttonText
     */
    public void stopAnimationIfRunning() {

        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
            play.setText("Play");
            play.setSelected(false);
        }
    }

    /**
     * ActionEvent Handler for "Reset" button
     *
     * Stops animation and clears the editor-window of living cells.
     * It also resets the settings to default and plays a oneshot 'pop' sound.
     */
    @FXML
    public void reset() {

        stopAnimationIfRunning();
        colors.resetAll();

        speedSlider.setValue(1.5);
        board.clearBoard();

        board = new DynamicBoard(config.getRows(), config.getColumns());
        draw.drawBoard(board.getGrid());

        zoomSlider.setValue(20);
        play.setText("Play");
        next.setText("Next");

        sound.play(sound.getFx8());
    }

    /**
     * ActionEvent Handler: "Bomb Icon" button
     * Fills the drawBoard with random alive cells. Simulates MadManPacMan sorta.....Cell consumer
     */
    @FXML
    public void handleTheBomber() throws IOException {

        reset();

        try {
            setRandomColor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //colors.setCell(Color.BLACK);
        //colors.setBackground(Color.DARKCYAN);
        //colors.setGridLine(Color.BLACK);
        updateColorPickerValues();

        // This combination of board/draw calls enables the pac-man sort of Game simulation.
        draw.drawBoard(board.getGrid());
        board.randomize();
        draw.drawGeneration(board.getGeneration());
        board.nextGeneration();

        speedSlider.setValue(speedSlider.getMax());
        //sound.play(sound.getLaser());
        sound.play(sound.getFx2());
    }

    //================================================================================
    // Functions related to Buttons and sliders for speed, sound and size
    //================================================================================

    /**
     * ActionEvent Handler for "Select Grid size" MenuButton
     */
    @FXML
    private void handleGridSizeEvents() {

        stopAnimationIfRunning();

        small.setOnAction(e -> {DynamicBoard db = new DynamicBoard(110,160);
            config.setCellSize(5);
            draw.drawBoard(db.getGrid());
            zoomSlider.setValue(config.cellSize()); //Set slider to same drawCell value
        });

        normal.setOnAction(e -> {DynamicBoard db = new DynamicBoard(55,80);
            config.setCellSize(10);
            draw.drawBoard(db.getGrid());
            zoomSlider.setValue(config.cellSize()); //Set slider to same drawCell value
        });

        large.setOnAction(e -> {DynamicBoard db = new DynamicBoard(28,40);
            config.setCellSize(20);
            draw.drawBoard(db.getGrid());
            zoomSlider.setValue(config.cellSize()); //Set slider to same drawCell value
        });

        mega.setOnAction(e -> {DynamicBoard db = new DynamicBoard(15,20);
            config.setCellSize(40);
            draw.drawBoard(db.getGrid());
            zoomSlider.setValue(config.cellSize()); //Set slider to same drawCell value
        });
    }

    /**
     * ActionEvent Handler for the "Zoom" slider
     * Handles the Observable listener for the slider
     */
    public void handleZoomSlider() {

        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {config.setCellSize(newValue.intValue());
            context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            draw.drawBoard(board.getGrid());
        });
    }

    /**
     * ActionEvent handler for the "sound Toggle IconButton".
     * Mute or turn up by setting the volume
     */
    public void handleToggleSound() {
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
     * ActionEvent Handler for "Play/Stop Button" on GUI
     * Toggles animation, changes buttonText and plays a one shot sound when triggered.
     */
    public void togglePlay() {
        play.setOnAction((event -> {
            if (timeline.getStatus() == Animation.Status.RUNNING) {
                timeline.stop();
                play.setText("Play");
                sound.play(sound.getPop2());
            } else if (timeline.getStatus() == Animation.Status.STOPPED) {
                timeline.play();
                play.setText("Stop");
                //sound.play(sound.getPop1(),0.2);
                sound.play(sound.getPop1());
            }
        }));
    }

    //================================================================================
    // Functions related to Mouse events
    //================================================================================

    /**
     * This method handle Cell coordinates of canvas by mouseEvents and the Logic of placing cells
     * correct on the screen and sceneGraph node canvas.
     *
     * Draw, erase, and toggle cells in editor by MouseGestures:
     *
     * Drag: Draw cells
     * LeftClick : Toggle Cell alive / Dead
     * DoubleClick (Hold button and Move support):  Erase one or multiple Cells
     *
     * @param event MouseEvent
     */
    @FXML
    public void handleMouseEvent(MouseEvent event) {

        try {
            double x = event.getX(); // mouse x pos
            double y = event.getY(); // mouse y pos

            // Find drawCell position in board drawCell drawBoard array
            int cellPosX = (int) Math.floor(x / config.cellSize());
            int cellPosY = (int) Math.floor(y / config.cellSize());

            // Get drawCell
            Cell cell = board.getCell(cellPosX, cellPosY);    // DYNAMIC
            //Cell drawCell = fb.getCell(cellPosX, cellPosY);  // FIXED

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
            //sound.play(sound.getPop1(), 0.1);
            draw.drawCell(cell);

        } catch (NullPointerException ne) {
            System.out.println("Clicks draw of bounds !");
        }
    }

    //================================================================================
    // Methods related to Graphics and Color settings
    //================================================================================

    /**
     * Returns the Canvas graphicsContext (Use for Draw calls)
     *
     * @return GraphicContext getContext
     */
    public GraphicsContext getContext() {

        context = canvas.getGraphicsContext2D();
        return context;
    }

    /**
     * Set drawCell color from ColorPicker
     */
    @FXML
    public void setCellColor() {

        colors.setCell(cpCell.getValue());
        draw.drawBoard(board.getGrid());
        updateColorPickerValues();
    }


    /**
     * Set drawBoard color from ColorPicker
     */
    @FXML
    public void setGridColor() {

        colors.setGridLine(cpGrid.getValue());
        draw.drawBoard(board.getGrid());
        updateColorPickerValues();
    }

    /**
     * Set background color from ColorPicker
     */
    @FXML
    public void setBackgroundColor() {

        colors.setBackground(cpBackground.getValue());
        draw.drawBoard(board.getGrid());
        updateColorPickerValues();
    }

    /**
     * This method sets and executes random colors to the Cells, GridLine and Background
     */
    @FXML
    public void setRandomColor() throws Exception {

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

        sound.play(sound.getLaser());
    }

    /**
     * Reset all colors to default settings
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
     * This method updates the colorPicker widget values
     */
    public void updateColorPickerValues() {

        cpCell.setValue(colors.getCell());
        cpBackground.setValue(colors.getBackground());
        cpGrid.setValue(colors.getGridLine());
    }

    //================================================================================
    // Functions related to MenuBar and Information Dialogs
    //================================================================================

    /**
     * Show information dialog box when info button is clicked
     */
    @FXML
    public void showInfo() {
        dialog.showInfo();
    }

    /**
     * Show information dialog box with Game of Life rules and description when button is clicked
     */
    @FXML
    public void aboutGameOfLife() {
        dialog.aboutGol();
    }

    // trenger påfyll......
    @FXML
    public void howToPlay() {
        dialog.howToPlay();
    }

    /**
     * Event Handler "Quit Button"
     * Exit application.
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

    /**
     * Event Handler "Zoom icon"
     * Zoom max/min by clicking the icon.
     */
    @FXML
    public void flipZoom() {
        zoomIcon.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                zoomSlider.setValue(40);
            } else if (event.getClickCount() == 2) {
                zoomSlider.setValue(5);
            }
        });
    }

    /**
     * Event Handler "Speed icon"
     * Max/min animation speed by clicking the icon.
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

/*    @FXML
    public void resetSpeed() {

        getSpeedIcon.setOnMouseClicked(event -> {
            if(event.getClickCount() == 1){
                speedSlider.setValue(2.5);
            }
            else if(event.getClickCount() == 2) {
                speedSlider.setValue(0.5);
            }
        });

    }*/
}
