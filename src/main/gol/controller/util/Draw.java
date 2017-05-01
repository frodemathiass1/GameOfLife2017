package main.gol.controller.util;

import javafx.scene.canvas.GraphicsContext;
import main.gol.model.boards.Config;
import main.gol.model.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for drawing graphics to the GUI canvas.
 *
 * @version 1.1
 */
public class Draw {

    private final GraphicsContext context;
    private final Colors colors;
    private final Config config;

    /**
     * This constructor takes the graphicsContext and colors as inputParameters to instantiate new Draw objects.
     * The constructor enables method calls for drawing individual cells or the entire board drawBoard to its context with defined colors.
     *
     * @param context GraphicsContext
     * @param colors Colors
     */
    public Draw(GraphicsContext context, Colors colors) {

        this.context = context;
        this.colors = colors;
        config = new Config();
    }

    /**
     * This method draws the entire Grid to canvas. Takes a 2Dimensional "ListOfLists" as inputParameter.
     * and the Graphic getContext.
     *
     * @param board List
     */
    public void drawBoard(List<List<Cell>> board) {

        for (int x = 0; x < board.size(); x++) {
            for (int y = 0; y < board.get(x).size(); y++) {
                drawCell(board.get(x).get(y));
            }
        }
    }

    /**
     * This method draws entire (old deprecated) drawBoard to canvas. Takes a 2Dimensional array as inputParameter.
     *
     * @deprecated
     * @param board Cell[][]
     */
    public void drawFixedBoard(Cell[][] board) {

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                drawCell(board[x][y]);
            }
        }
    }

    /**
     * This method draw individual cells to the canvas triggered by canvas mouse-events.
     * The drawCell is toggled to dead/alive by clicking on the canvas cell coordinate.
     * Sets 'dead' cells to the drawBoard background color, and 'alive' cells to defined cell color.
     *
     * @param cell Cell
     */
    public void drawCell(Cell cell) {

        int cellSize = config.cellSize();
        int x = cell.getX();
        int y = cell.getY();

        if (cell.getState()) {
            context.setFill(colors.getCell());
            context.setStroke(colors.getGridLine());
        } else {
            context.setFill(colors.getBackground());
            context.setStroke(colors.getGridLine());
        }
        context.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
        context.strokeRect(x * cellSize, y * cellSize, cellSize, cellSize);
    }

    /**
     * This method draws next generation of Cells to canvas.
     *
     * @param gen ArrayList
     */
    public void drawGeneration(ArrayList<Cell> gen) {

        for (Cell cell : gen) {
            drawCell(cell);
        }
    }
}
