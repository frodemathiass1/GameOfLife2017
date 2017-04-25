package main.gol.model.boards;

import main.gol.model.Cell;
import main.gol.model.Rules;

import java.util.ArrayList;
import java.util.Random;


/**
 * This test class tests if the playStop function works as intended. The playStop function handles the
 * Game of Life rules.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.0
 * @deprecated
 */
public class FixedBoard {

    // Cell, grid, background Color
    //private Color gridColor = Color.LIGHTGREY;
    //private Color cellColor = Color.BLACK;
    //private Color backgroundColor = Color.WHITE;
    //private final GraphicsContext graphics;
    //private int cellSize;


    private Cell[][] grid;
    private ArrayList<Cell> generation;


    /**
     * FixedBoard Constructor
     *
     * @param columns int
     * @param rows int
     */
    public FixedBoard(int columns, int rows) {

      setBoard(columns, rows);
    }


    /**
     * API: Method for creating a new fixed board.
     *
     * @param columns int
     * @param rows    int
     */
    public void setBoard(int columns, int rows) {

        byte[][] board = new byte[columns][rows];

        byte[][] testBoard3 = {
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0}
        };
        setBoard(board);
    }


    /**
     * Initialize Grid and instantiate Cells with its neighbors.
     *
     * @param board byte[][]
     */
    public void setBoard(byte[][] board) {

        int rows = board.length;
        int columns = board[0].length;

        grid = new Cell[columns][rows];
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Cell cell = new Cell(x, y);
                if (board[y][x] == 1) {
                    cell.setState(true);
                } else if (board[y][x] == 0) {
                    cell.setState(false);
                }
                grid[x][y] = cell;
            }
        }

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y].initNeighborsFixed(this);
                //System.out.println("Neighbors: " + grid[x][y].getNeighbors());
            }
        }
    }


    /**
     * This method handles the Game of Life rules.
     * Checks each cell for it state counts and checks each neighbors state.
     * Collects the next generation of cells in a ArrayList of Cell objects.
     */
    public void nextGeneration() {

        ArrayList<Cell> generationList = new ArrayList<>();
        Rules rules = new Rules();

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                Cell cell = grid[x][y];
                rules.checkRules(cell, generationList);
            }
        }
        // Update generation
        for (Cell cell : generationList) {
            cell.updateState();
        }
        this.generation = generationList;
    }




    /**
     * This method iterates through the cell grid and set all living cells to dead/false
     *
     * @param grid cell[][]
     */
    public void clearBoard(Cell[][] grid) {

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y].setState(false);
            }
        }
    }


    /**
     * This method generates a random set of alive cells and adds them to the generation list
     */
    public void randomize() {

        ArrayList<Cell> generationList = new ArrayList<>();
        Random rand = new Random();

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                Cell cell = grid[x][y];
                if (cell.countAliveNeighbors() < rand.nextInt(3)) {
                    cell.setState(rand.nextBoolean());
                    generationList.add(cell);
                }
            }
        }
        this.generation = generationList;
    }


    /**
     * This method handles cells within the cell grid (array) borders
     * and the corresponding cell coordinates bound to grid dimensions
     *
     * @param x int Cell x position
     * @param y int Cell y position
     * @return cell
     */
    public Cell getCell(int x, int y) {

        if (x < 0 || y < 0 || x >= grid.length || y >= grid[x].length) {

            return null;
        } else {

            return this.grid[x][y];
        }
    }

    /**
     * @return cell[][] grid
     */
    public Cell[][] getGrid() {
        return this.grid;
    }


    /**
     * List with the next generation of Cells
     *
     * @return ArrayList generation
     */
    public ArrayList<Cell> getGeneration() {
        return this.generation;
    }


    /**
     * Testing method: This method takes Cell x/y coordinates and counts
     * alive surrounding neighbor cells.
     *
     * @param x int Cell X position
     * @param y int Cell Y Position
     * @return int
     */
    public int countNeighbours(int x, int y) {
        Cell cell = getCell(x, y);
        return cell.countAliveNeighbors();
    }


    /**
     * Testing method: This method takes a generation of cells
     * and serialize each cell state on the testBoard to a string.
     * e.g."0010100101010"
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder serialized = new StringBuilder();
        for (int x = 0; x < this.grid.length; x++) {
            for (int y = 0; y < this.grid[x].length; y++) {
                String state = this.grid[x][y].getState() ? "1" : "0";
                serialized.append(state);
            }
        }
        return serialized.toString();
    }







//    /**
//     * @param color cellColor
//     */
//    public void setCellColor(Color color) {
//        this.cellColor = color;
//    }
//
//
//    /**
//     * @param color gridColor
//     */
//    public void setGridColor(Color color) {
//        this.gridColor = color;
//    }
//
//
//    /**
//     * @param color backgroundColor
//     */
//    public void setBcColor(Color color) {
//        this.backgroundColor = color;
//    }
//
//
//    /**
//     * @param cellSize cellSize
//     */
//    public void setCellSize(int cellSize) {
//        this.cellSize = cellSize;
//    }

    //    /**
//     * This method loops through the generation list and draw each cell to canvas
//     */
//    public void generation() {
//
//        for (Cell cell : this.generation) {
//            //cell.updateState();
//            cell(cell);
//        }
//    }
//
//
//    /**
//     * This method toggles the individual cells state and
//     * Sets corresponding color on the canvas grid
//     *
//     * @param cell Cell
//     */
//    public void cell(Cell cell) {
//
//        if (cell.getState()) {
//            graphics.setFill(getCellColor());
//            graphics.setStroke(getGridColor());
//        } else {
//            graphics.setFill(getBackgroundColor());
//            graphics.setStroke(getGridColor());
//        }
//        graphics.fillRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
//        graphics.strokeRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
//    }
//
//
//    /**
//     * This method draws the grid of cells to canvas.
//     */
//    public void grid() {
//
//        for (int x = 0; x < grid.length; x++) {
//            for (int y = 0; y < grid[x].length; y++) {
//                cell(grid[x][y]);
//            }
//        }
//    }


}