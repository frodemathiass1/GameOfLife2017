package main.gol.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import main.gol.model.Board;
import main.gol.model.Cell;




public class GOLGraphics {

/*

    @FXML private GraphicsContext gc;
    @FXML private Slider speedSlider;
    @FXML private Button play;
    //@FXML private ColorPicker cellColor, gridColor, backgroundColor;
    @FXML private Canvas canvas;
    @FXML private Slider sizeSlider;
    @FXML private MenuItem small, normal, large;

    private Color gridColor = Color.LIGHTGREY;
    private Color cellColor = Color.BLACK;
    private Color backgroundColor = Color.WHITE;

    private int cellSize = 5;

    // GOLGraphics constructor
    public GOLGraphics() {
        gc = canvas.getGraphicsContext2D();
    }



    public void drawBoard(Board board) {
        Cell[][] grid = board.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                drawCell(grid[i][j]);
                //System.out.println(grid[i][j].getNextState());
            }
        }
    }
    public void drawCell(Cell cell) {
        //System.out.println(cell.getState());
        if (cell.getState()){
            gc.setFill(cellColor); // Set cell color
            gc.setStroke(cellColor); // Sets grid color to cell color
            //cell.setState(true);
        }
        else {
            gc.setFill(backgroundColor); // Set background color
            gc.setStroke(gridColor); // Sets selected grid color
            //cell.setState(false);
        }
        gc.fillRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
        gc.strokeRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
    }
*/


}
