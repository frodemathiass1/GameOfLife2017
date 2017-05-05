package main.gol.model.boards;

import main.gol.model.Cell;
import main.gol.model.Rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class handles all the Board logic and its Cells which represents the Grid.
 *
 * @version 2.0
 * @see Cell
 */
public class DynamicBoard {

    private List<List<Cell>> grid;
    private ArrayList<Cell>  generation;

    /**
     * This constructor constructs a new DynamicBoard with given number of rows and columns.
     *
     * @param rows int
     * @param columns int
     */
    public DynamicBoard(int rows, int columns) {

        setBoard(columns, rows);
    }

    /**
     * This method sets a new cell outside the arrayIndex bounds by expanding the
     * grid and filling the empty slots in the ArrayList.
     * Board expansion is enabled but not implemented, due to nick of time for the project deadline .
     *
     * @param x int
     * @param y int
     * @param state boolean
     */
    public void setCellState(int x, int y, boolean state) {

        int rows = this.grid.size();
        int columns = this.grid.get(0).size();
        int newRows = x + 1;
        int newColumns = y + 1;

        if (rows != newRows) {
            int add = newRows - rows;
            for (int i = 0; i < add; i++) {
                List<Cell> row = new ArrayList<>();
                for (int j = 0; j < newColumns; j++) {
                    Cell cell = new Cell(rows + i, j);
                    if (rows + i == x && j == y) {
                        cell.setState(state);
                    }
                    row.add(cell);
                    cell.initNeighbors(this);
                }
                this.grid.add(row);
            }
        }

        if (columns != newColumns) {
            int add = newColumns - columns;
            for (int i = 0; i < newRows; i++) {
                List<Cell> row = this.grid.get(i);
                for (int j = 0; j < add; j++) {
                    Cell cell = new Cell(i, columns + j);
                    if (i == x && columns + j == y) {
                        cell.setState(state);
                    }
                    row.add(cell);
                    cell.initNeighbors(this);
                }
            }
        }
    }

    /**
     * This method initializes a board based on width/height (columns/rows) with false values(Dead cells).
     *
     * @param columns int
     * @param rows    int
     */
    public void setBoard(int columns, int rows) {

        byte[][] board = new byte[rows][columns]; // flipped x/y
        setBoard(board);
    }

    /**
     * This method initializes a board based on a byte[][] board array containing values with both live and dead cells.
     *
     * @param board byte[][]
     */
    public void setBoard(byte[][] board) {

        // Lock the columns size so each row has the same amounts of columns
        int rows = board.length;
        int columns = board[0].length;

        // Instantiate new row and add it to the list
        this.grid = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            List<Cell> row = new ArrayList<>();
            this.grid.add(row);
        }
        // For each index position in the grid, check the given byte[][] array values and set each cell accordingly
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Cell cell = new Cell(x, y);
                if (board[y][x] == 1) {
                    cell.setState(true);
                } else if (board[y][x] == 0) {
                    cell.setState(false);
                }
                this.grid.get(y).add(cell);
            }
        }
        // Initialize each cell's neighbors
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                this.grid.get(y).get(x).initNeighbors(this);
            }
        }
    }

    /**
     * This method returns a cell based on x/y coordinates.
     *
     * @param x int
     * @param y int
     * @return Cell
     */
    public Cell getCell(int x, int y) {

        if (x < 0 || y < 0 || y >= this.grid.size() || x >= this.grid.get(y).size()) {
            return null;
        } else {
            return this.grid.get(y).get(x);
        }
    }

    /**
     * This method populates the next generation of cells in a generation list.
     * Checks each cell for its state, then checks each neighbors state and counts surrounding live cells,
     * then finally collects the next generation of cells in an ArrayList.
     */
    public void nextGeneration() {
        // We use 4 worker threads for this task, we don't want to start too many threads because creating threads
        // is somewhat expensive and it might counteract the purpose of threading it in the first place
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        ReentrantLock lock = new ReentrantLock();

        Rules rules = new Rules();

        ArrayList<Cell> generationList = new ArrayList<>();

        // Iterate through each row and start a worker that checks the rule per cell in that row
        for (List<Cell> row : grid) {
            threadPool.submit(() -> {
                for (Cell cell : row) {
                    if (rules.checkRules(cell)) {
                        lock.lock();
                        try {
                            generationList.add(cell);
                        } finally {
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
     * This method generates a random set of alive cells and adds them to the generation list.
     */
    public void randomize() {

        ArrayList<Cell> generationList = new ArrayList<>();
        Random rand = new Random();
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                Cell cell = grid.get(y).get(x);
                if (cell.countAliveNeighbors() < rand.nextInt(3)) {
                    cell.setState(rand.nextBoolean());
                    generationList.add(cell);
                }
            }
        }
        this.generation = generationList;
    }

    /**
     * This method iterates through the grid and sets all living cells to dead/false
     */
    public void clearBoard() {

        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                grid.get(y).get(x).setState(false);
            }
        }
    }

    /**
     * Returns the grid.
     *
     * @return List
     */
    public List<List<Cell>> getGrid() {
        return this.grid;
    }

    /**
     * Returns the next generation of alive cells.
     *
     * @return ArrayList drawGeneration
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
     * Counts the alive cells for this board
     * @return the number of alive cells
     */
    public int getAliveCells() {
        return (int) this.grid.stream()
                .flatMap(Collection::stream)
                .filter(Cell::getState)
                .count();
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
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                String state = this.grid.get(y).get(x).getState() ? "1" : "0";
                serialized.append(state);
            }
        }
        return serialized.toString();
    }
}
