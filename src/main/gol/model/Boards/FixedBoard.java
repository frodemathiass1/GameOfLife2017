package main.gol.model.Boards;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.gol.model.Cell;

import java.util.ArrayList;
import java.util.Random;


/**
 * This test class tests if the nextGeneration function works as intended. The nextGeneration function handles the
 * Game of Life rules.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.0
 */
public class FixedBoard {

    private int cellSize;
    private Cell[][] grid;
    private final GraphicsContext graphics;
    private ArrayList<Cell> generationList;

    // Cell, grid, background Color
    private Color gridColor = Color.LIGHTGREY;
    private Color cellColor = Color.BLACK;
    private Color backgroundColor = Color.WHITE;


    /**
     * Board constructor
     *
     * @param graphics GraphicContext
     * @param cellSize int
     */
    public FixedBoard(GraphicsContext graphics, int cellSize) {

        this.cellSize = cellSize;
        this.graphics = graphics;
    }


    /**
     * Set up the byte[][] board and call init setBoard method for cell grid
     *
     * @param columns int
     * @param rows    int
     */
    public void setBoard(int columns, int rows) {

        byte[][] board = new byte[rows][columns];
        setBoard(board);
        //System.out.println(board[2][3]);
    }


    /**
     * Initialize board with populated grid of cells, initialize each cell neighbors
     *
     * @param board byte[][]
     */
    public void setBoard(byte[][] board) {

        //System.out.println(board.length);
        //System.out.println(board[0].length);


       /* for (int x = 0; x < board.length; x++) {
            for (int y = 0; < board[x].length; y++) {
            }
        }*/

        int rows = board.length;
        int columns = board[0].length;

        grid = new Cell[columns][rows];
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Cell cell = new Cell(x, y);
                if (board[y][x] == 1) { // flip x and y axis. Why? because that's how it works
                    cell.setState(true);
                } else if (board[y][x] == 0) {
                    cell.setState(false);
                }
                grid[x][y] = cell; // setBoard cell grid
            }
        }

        // We are working with references so we don't need to update its neighbors unless the map is reinitialized (cell toggles to alive when clicked).
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y].initNeighbors(this);
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

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                Cell cell = grid[x][y];

                // dies, as if by underpopulation.
                if (cell.getState() && cell.countAliveNeighbors() < 2) {
                    cell.setNextState(false);
                    generationList.add(cell);
                }
                // lives on to the next generation.
                else if (cell.getState() && (cell.countAliveNeighbors() == 2 || cell.countAliveNeighbors() == 3)) {
                    cell.setNextState(true);
                    generationList.add(cell);
                }
                // dies, as if by overpopulation.
                else if (cell.getState() && cell.countAliveNeighbors() > 3) {
                    cell.setNextState(false);
                    generationList.add(cell);
                }
                // becomes a live cell, as if by reproduction.
                else if (!cell.getState() && cell.countAliveNeighbors() == 3) {
                    cell.setNextState(true);
                    generationList.add(cell);
                }
            }
        }
        // Update generation
        for (Cell cell : generationList) {
            cell.updateState();
        }
        this.generationList = generationList;
    }


    /**
     * This method loops through the generation list and draw each cell to canvas
     */
    public void drawGeneration() {

        for (Cell cell : this.generationList) {
            //cell.updateState();
            drawCell(cell);
        }
    }


    /**
     * This method toggles the individual cells state and
     * Sets corresponding color on the canvas grid
     *
     * @param cell Cell
     */
    public void drawCell(Cell cell) {

        if (cell.getState()) {
            graphics.setFill(getCellColor());
            graphics.setStroke(getGridColor());
        } else {
            graphics.setFill(getBackgroundColor());
            graphics.setStroke(getGridColor());
        }
        graphics.fillRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
        graphics.strokeRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
    }


    /**
     * This method draws the grid of cells to canvas.
     */
    public void drawGrid() {

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                drawCell(grid[x][y]);
            }
        }
    }


    /**
     * This method loop through the cell grid and set all living cells to dead/false
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
     * This method is a replica of next generation method but with random neighbor constrictions
     */
    public void makeRandomGenerations() {

        ArrayList<Cell> generationList = new ArrayList<>();
        Random rand = new Random();

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                Cell cell = grid[x][y];

                if (cell.getState() && cell.countAliveNeighbors() < rand.nextInt(8)) {
                    cell.setState(rand.nextBoolean());
                    generationList.add(cell);
                } else if (cell.getState() && (cell.countAliveNeighbors() == rand.nextInt(8) || cell.countAliveNeighbors() == rand.nextInt(8))) {
                    cell.setState(rand.nextBoolean());
                    generationList.add(cell);
                } else if (cell.getState() && cell.countAliveNeighbors() > rand.nextInt(8)) {
                    cell.setState(rand.nextBoolean());
                    generationList.add(cell);
                } else if (!cell.getState() && cell.countAliveNeighbors() == rand.nextInt(8)) {
                    cell.setState(rand.nextBoolean());
                    generationList.add(cell);
                }
            }
        }
        this.generationList = generationList;
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
     * @param color cellColor
     */
    public void setCellColor(Color color) {
        this.cellColor = color;
    }


    /**
     * @param color gridColor
     */
    public void setGridColor(Color color) {
        this.gridColor = color;
    }


    /**
     * @param color backgroundColor
     */
    public void setBcColor(Color color) {
        this.backgroundColor = color;
    }


    /**
     * @param cellSize cellSize
     */
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }


    /**
     * @return cell[][] grid
     */
    public Cell[][] getGrid() {
        return this.grid;
    }


    /**
     * @return int cellSize
     */
    public int getCellSize() {
        return this.cellSize;
    }


    /**
     * @return Color gridColor
     */
    public Color getGridColor() {
        return gridColor;
    }


    /**
     * @return Color cellColor
     */
    public Color getCellColor() {
        return cellColor;
    }


    /**
     * @return Color backgroundColor
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }


    /**
     * List with the next generation of Cells
     *
     * @return ArrayList generationList
     */
    public ArrayList<Cell> getGenerationList() {
        return this.generationList;
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
}