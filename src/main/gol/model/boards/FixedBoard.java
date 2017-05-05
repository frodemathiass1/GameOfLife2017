package main.gol.model.boards;

import main.gol.model.Cell;
import main.gol.model.Rules;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class is the class that was used for the old game board with fixed size.
 *
 * @version 1.2
 * @deprecated
 * @see DynamicBoard
 */
public class FixedBoard {

    private Cell[][] grid;
    private ArrayList<Cell> generation;

    /**
     * This constructor constructs a new FixedBoard with the given number of rows and columns
     *
     * @param columns int
     * @param rows int
     */
    public FixedBoard(int columns, int rows) {
      setBoard(columns, rows);
    }

    /**
     * This method is the main method (API) for setting a new grid with byte[][] array as inputParameter.
     *
     * @param columns int
     * @param rows    int
     */
    public void setBoard(int columns, int rows) {

        byte[][] board = new byte[columns][rows];
        setBoard(board);
    }

    /**
     * This method initialize the gameBoard with cells and initialize each cell neighbors.
     *
     * @param board byte[][]
     */
    public void setBoard(byte[][] board) {

        // Lock the columns size so each row has the same amounts of columns
        int rows = board.length;
        int columns = board[0].length;

        // Instantiate new Cell grid add new cell to the grid
        this.grid = new Cell[columns][rows];
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Cell cell = new Cell(x, y);
                if (board[y][x] == 1) {
                    cell.setState(true);
                } else if (board[y][x] == 0) {
                    cell.setState(false);
                }
                this.grid[x][y] = cell;
            }
        }
        // Initialize each cell's neighbors
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y].initNeighborsFixed(this);
            }
        }
    }

    /**
     * This method populates the next generation of cells in a generation list.
     * Checks each cell for its state, then checks each neighbors state and counts surrounding live cells.
     * Collects the next generation of cells in a ArrayList of Cell objects.
     * @version 2.0
     */
    public void nextGeneration() {

        // We use 4 worker threads for this task, we don't want to start too many threads because creating threads
        // is somewhat expensive and it might counteract the purpose of threading it in the first place
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        ReentrantLock lock = new ReentrantLock();

        ArrayList<Cell> generationList = new ArrayList<>();
        Rules rules = new Rules();

        // This loop is re-written to enable thread support after updating the dynamic board.
        for (int x = 0; x < grid.length; x++) {
            int finalX = x;
            threadPool.submit(()->{
                for (int y = 0; y < grid[finalX].length; y++) {
                    if(rules.checkRules(grid[finalX][y])){
                        lock.lock();
                        try{
                            generationList.add(getCell(finalX,y));
                        } finally{
                            lock.unlock();
                        }
                    }
                }
            });

        }
        threadPool.shutdown();

        try {
            threadPool.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Update drawGeneration
        for (Cell cell : generationList) {
            cell.updateState();
        }
        this.generation = generationList;
    }

    /**
     * This method iterates through the drawCell drawGrid and sets all living cells to dead/false
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

        for (int x = 0; x < this.grid.length; x++) {
            for (int y = 0; y < this.grid[x].length; y++) {
                Cell cell = this.grid[x][y];
                if (cell.countAliveNeighbors() < rand.nextInt(3)) {
                    cell.setState(rand.nextBoolean());
                    generationList.add(cell);
                }
            }
        }
        this.generation = generationList;
    }

    /**
     * This method returns a cell within the (grid) array index bounds.
     *
     * @param x int
     * @param y int
     * @return Cell
     */
    public Cell getCell(int x, int y) {

        if (x < 0 || y < 0 || x >= grid.length || y >= grid[x].length) {
            return null;
        } else {
            return this.grid[x][y];
        }
    }

    /**
     * This method returns the grid.
     * @return Cell[][]
     */
    public Cell[][] getGrid() {
        return this.grid;
    }

    /**
     * This method returns the next generation of alive cells.
     *
     * @return ArrayList
     */
    public ArrayList<Cell> getGeneration() {
        return this.generation;
    }

    /***
     * This method takes a cell's x/y coordinates as inputParameter and
     * checks the cell for surrounding alive neighbors then returns the amount as an int.
     *
     * @param x int
     * @param y int
     * @return int
     */
    public int countNeighbours(int x, int y) {

        Cell cell = getCell(x, y);
        return cell.countAliveNeighbors();
    }

    /**
     * Method for testing purposes.
     * This method returns a serialized string of the initialized gameBoard describing each cell's state.
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