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
import main.gol.model.boards.FixedBoard;
import main.gol.model.filemanager.FileHandler;
import main.gol.model.filemanager.URLHandler;

import javax.xml.ws.http.HTTPException;
import java.io.FileNotFoundException;
import java.io.IOException;
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
 * @version 1.0
 */
public class GUIController implements Initializable {



    @FXML private Label zoomIcon,getSpeedIcon;
    @FXML private Slider speedSlider;
    @FXML private Button next;
    @FXML private ColorPicker cpCell, cpGrid, cpBackground;
    @FXML private Canvas canvas;
    @FXML private Slider zoomSlider;
    @FXML private MenuItem small, normal, large,mega,url1, url2, url3, url4, url5, url6, url7, url8, url9, url10;
    @FXML private ToggleButton play, toggleSound;


    private GraphicsContext context;
    private DynamicBoard dB;
    private FixedBoard fb;
    private URLHandler urlHandler;
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

        try{
            togglePlay();
            initializeGraphicsAndSounds();  // Initialize Dialogs, Sound, and GraphicsContext
            // FIXED BOARD TESTING
            //fb = new FixedBoard(50,50);
            //draw.fixedGrid(fb.getGrid(), getContext());


           // Set Default gameBoard and draw to canvas*
            dB = new DynamicBoard(config.getRows(), config.getColumns());
            dB.setCellState(200, 200, true); // Set cellState outside array bounds and fill empty slots
            draw.grid(dB.getGrid());


            // Initialize animation timeline.
            timeline = new Timeline();
            KeyFrame kf = new KeyFrame(Duration.seconds(0.1), event -> {
                timeline.setRate(speedSlider.getValue());
                dB.nextGeneration();
                draw.generation(dB.getGeneration());
                //FIXED BOARD ANIMATION TESTING
                //fb.nextGeneration();
                //draw.generation(fb.getGeneration(), getContext());
            });
            timeline.getKeyFrames().add(kf);
            timeline.setCycleCount(Timeline.INDEFINITE);

            initializeObservables(); // Init observable gui components

            //url8.fire();


        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println("Oops. Something when wrong when firing up the application!");
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
    public void initializeGraphicsAndSounds(){

        config = new Config();
        colors = new Colors();
        draw = new Draw(getContext(), colors);
        dialog = new Dialogs();
        sound = new Sound();


    }

    /**
     * This method is a wrapper method for initializing the observable GUI components
     */
    public void initializeObservables(){

        handleGridSizeEvents();         // Initialize "Select grid size" menu. Select Size on GUI.
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
     * Sets up a new dB with byte[][] arrays as inputParameter
     *
     * @param board byte[][]
     */
    public void newBoard(byte[][] board)  {

        try{
            dB = new DynamicBoard(config.getRows(), config.getColumns());
            dB.setGrid(board);
            draw.grid(dB.getGrid());

            //fb = new FixedBoard(50,50);
            //fb.setBoard(board);
            //draw.fixedGrid(fb.getGrid());

        }
        catch (NullPointerException ne){
            System.err.println("NullPointer Exception!");
            ne.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException oob){
            System.err.println("ArrayIndex out of bounds!");
            oob.printStackTrace();
        }
    }




    /**
     * API: Create boards with a Valid URL string as inputParameter.
     *
     * @param url Takes url for valid GOL .txt/.cells file patterns
     */

    public void handleURL(String url) {


        try {
            urlHandler = new URLHandler();
            urlHandler.readAndParse(url);
            newBoard(urlHandler.getMatrix());

        } catch (Exception e) {
            dialog.urlError();
            System.err.println("Something went wrong reading the URL.");
        }
    }


    /**
     * ActionEvent Handlers "Pattern Select Menu"
     * Add internal GUI FXML objects with fx:id attributes in FXML document
     * for expanding the predefined patterns menu
     */
    @FXML
    public void handlePatternSelector() {

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
    }


    /**
     * ActionEvent Handler "Load from Disk "
     * Set new dB by selecting file.
     */
    @FXML
    public void loadFileFromDisk() throws Exception  {

        try{
            FileHandler reader = new FileHandler();
            newBoard(reader.getTheMatrix());
        }
        catch (NullPointerException ne){
            dialog.oops();
            System.err.println("File cannot be parsed");
        }
        catch (FileNotFoundException fnf){
            dialog.notFoundException();
        }
    }


    /**
     * ActionEvent Handler: "Load from Url" MenuButton
     *
     */
    @FXML
    public void loadFileFromURL() throws HTTPException {

        stopAnimationIfRunning();

        urlHandler = new URLHandler();
        TextInputDialog inputURL = new TextInputDialog();
        inputURL.setTitle("Open URL");
        inputURL.setHeaderText("Resources: https://bitstorm.org/gameoflife/lexicon/");
        inputURL.setContentText("Enter URL here");
        Optional<String> result = inputURL.showAndWait();

        if (result.isPresent()) {
            if (!result.get().toLowerCase().startsWith("http")) {
                dialog.httpError();
                System.out.println("Wrong URL");
            }
            else {
                try {
                    urlHandler.readAndParse(result.get());
                    newBoard(urlHandler.getMatrix());

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
    public void nextStep() throws MediaException{

        dB.nextGeneration();
        draw.generation(dB.getGeneration());

        // FOR TESTING NEXT GENERATION ON FIXED BOARDS
        //fb.nextGeneration();
        //draw.generation(fb.getGeneration(), getContext());

        cellsCounter = dB.getGeneration().size();
        //cellsCounter = fb.getGeneration().size();
        if (cellsCounter >= 0){

            sound.play(sound.getFx3());
            next.setText("Cells "+ cellsCounter);
        }
        else{
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
        colors.reset();

        speedSlider.setValue(1.5);
        dB.clearBoard();

        dB = new DynamicBoard(config.getRows(), config.getColumns());
        draw.grid(dB.getGrid());

        zoomSlider.setValue(20);
        play.setText("Play");
        next.setText("Next");

        sound.play(sound.getFx8());
    }


    /**
     * ActionEvent Handler: "Bomb Icon" button
     * Fills the grid with random alive cells. Simulates MadManPacMan sorta.....Cell consumer
     */
    @FXML
    public void handleTheBomber() throws IOException {

        stopAnimationIfRunning();

        colors.setCell(Color.BLACK);
        colors.setBc(Color.DARKCYAN);
        colors.setGridLine(Color.BLACK);
        updateColorPickerValues();

        // This combination of dB/draw calls enables the pac-man sort of Game simulation.
        draw.grid(dB.getGrid());
        dB.randomize();
        draw.generation(dB.getGeneration());
        dB.nextGeneration();

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

        small.setOnAction(e -> {
            DynamicBoard db = new DynamicBoard(110,160);
            config.setCellSize(5);
            draw.grid(db.getGrid());
            zoomSlider.setValue(config.cellSize()); //Set slider to same cell value
        });

        normal.setOnAction(e -> {
            DynamicBoard db = new DynamicBoard(55,80);
            config.setCellSize(10);
            draw.grid(db.getGrid());
            zoomSlider.setValue(config.cellSize()); //Set slider to same cell value
        });

        large.setOnAction(e -> {
            DynamicBoard db = new DynamicBoard(28,40);
            config.setCellSize(20);
            draw.grid(db.getGrid());
            zoomSlider.setValue(config.cellSize()); //Set slider to same cell value
        });
        mega.setOnAction(e -> {
            DynamicBoard db = new DynamicBoard(15,20);
            config.setCellSize(40);
            draw.grid(db.getGrid());
            zoomSlider.setValue(config.cellSize()); //Set slider to same cell value
        });

    }


    /**
     * ActionEvent Handler for the "Zoom" slider
     * Handles the Observable listener for the slider
     */
    public void handleZoomSlider(){

        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            config.setCellSize(newValue.intValue());
            context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            draw.grid(dB.getGrid());

        });
    }


    /**
     * ActionEvent handler for the "sound Toggle IconButton".
     * Mute or turn up by setting the volume
     */
    public void handleToggleSound() {

        toggleSound.setOnAction(e -> {
            if (toggleSound.isSelected()) {
                sound.setVol(0.0);

            } else {
                sound.setVol(0.2);
            }
        });
    }

    public void togglePlay(){

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

            // Find cell position in dB cell grid array
            int cellPosX = (int) Math.floor(x / config.cellSize());
            int cellPosY = (int) Math.floor(y / config.cellSize());

            // Get cell
            Cell cell = dB.getCell(cellPosX, cellPosY);    // DYNAMIC
            //Cell cell = fb.getCell(cellPosX, cellPosY);  // FIXED

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
            draw.cell(cell);


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
    public GraphicsContext getContext(){
        context = canvas.getGraphicsContext2D();
        return context;
    }


    /**
     * Set cell color from ColorPicker
     */
    @FXML
    public void setCellColor() {

        colors.setCell(cpCell.getValue());
        draw.grid(dB.getGrid());
        updateColorPickerValues();
    }


    /**
     * Set grid color from ColorPicker
     */
    @FXML
    public void setGridColor() {

        colors.setGridLine(cpGrid.getValue());
        draw.grid(dB.getGrid());
        updateColorPickerValues();

    }

    /**
     * Set background color from ColorPicker
     */
    @FXML
    public void setBackgroundColor() {

        colors.setBc(cpBackground.getValue());
        draw.grid(dB.getGrid());
        updateColorPickerValues();
    }



    /**
     * This method sets and executes random colors to the Cells, GridLine and Background
     */
    @FXML
    public void setRandomColor() throws Exception{

        Random rand = new Random();
        int b = 255; // int bounder
        Color cell = Color.rgb(rand.nextInt(b), rand.nextInt(b), rand.nextInt(b));
        Color grid = Color.rgb(rand.nextInt(b), rand.nextInt(b), rand.nextInt(b));
        Color backGround = Color.rgb(rand.nextInt(b), rand.nextInt(b), rand.nextInt(b));

        // Set Random colors
        colors.setCell(cell);
        colors.setBc(backGround);
        colors.setGridLine(grid);

        updateColorPickerValues();
        draw.grid(dB.getGrid());

        sound.play(sound.getLaser());

    }


    /**
     * Reset all colors to default settings
     */
    @FXML
    public void resetColor()  {
        
        colors.reset();
        updateColorPickerValues();
        draw.grid(dB.getGrid());
        try{
            sound.play(sound.getFx8());
        }
        catch (MediaException me){
            dialog.audioError();
        }

    }

    
    /**
     * This method updates the colorPicker widget values
     *
     */
    public void updateColorPickerValues(){


        cpCell.setValue(colors.getCell());
        cpBackground.setValue(colors.getBc());
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

        try{
            sound.play(sound.getFx3());
        }
        catch (MediaException me){
              dialog.audioError();
        }
        Platform.exit();

    }

    @FXML
    public void resetZoom() {

        zoomIcon.setOnMouseClicked(event -> {
            if(event.getClickCount() == 1){
                zoomSlider.setValue(40);
            }
            else if(event.getClickCount() == 2) {
                zoomSlider.setValue(5);
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
