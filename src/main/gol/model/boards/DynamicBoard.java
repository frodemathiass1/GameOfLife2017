package main.gol.model.boards;

import main.gol.model.Cell;
import main.gol.model.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class blablabla.....
 */
public class DynamicBoard {

    private List<List<Cell>> grid;
    private ArrayList<Cell>  generation;

    /**
     * This constructor constructs a new DynamicBoard with given number of rows and columns
     *
     * @param rows int
     * @param columns int
     */
    public DynamicBoard(int rows, int columns) {

        setBoard(rows,columns);
    }

    /**
     * This method sets a new cell outside the arrayIndex bounds.
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
     * This method is the main method (API) for setting a new grid with byte[][] array as inputParameter.
     *
     * @param columns int
     * @param rows    int
     */
    public void setBoard(int columns, int rows) {

        byte[][] board = new byte[rows][columns]; // flipped x/y
        setGrid(board);
    }

    /**
     * This method initialize the gameBoard with cells and initialize each cell neighbors.
     *
     * @param board
     */
    public void setGrid(byte[][] board) {

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
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                Cell cell = new Cell(x, y);
                if (board[x][y] == 1) { // flip x and y axis. Why? because that's how it works
                    cell.setState(true);
                } else if (board[x][y] == 0) {
                    cell.setState(false);
                }
                this.grid.get(x).add(cell);
            }
        }
        // Initialize each cell's neighbors
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                this.grid.get(x).get(y).initNeighbors(this);
            }
        }
    }

    /**
     * This method returns a cell within the (grid) array index bounds.
     *
     * @param x int
     * @param y int
     * @return Cell
     */
    public Cell getCell(int x, int y) {

        if (x < 0 || y < 0 || x >= this.grid.size() || y >= this.grid.get(x).size()) {
            return null;
        } else {
            return this.grid.get(x).get(y);
        }
    }

    /**
     * This method populates the next generation of cells in a generation list.
     * Checks each cell for its state, then checks each neighbors state and counts surrounding live cells.
     * Collects the next generation of cells in an ArrayList of Cell objects.
     */
    public void nextGeneration() {

        Rules rules = new Rules();
        ArrayList<Cell> generationList = new ArrayList<>();
        for (int x = 0; x < grid.size(); x++) {
            for (int y = 0; y < grid.get(x).size(); y++) {
                Cell cell = grid.get(x).get(y);
                rules.checkRules(cell, generationList);
            }
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
        for (int x = 0; x < this.grid.size(); x++) {
            for (int y = 0; y < this.grid.get(x).size(); y++) {
                Cell cell = this.grid.get(x).get(y);
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

        for (int x = 0; x < grid.size(); x++) {
            for (int y = 0; y < grid.get(x).size(); y++) {
                grid.get(x).get(y).setState(false);
            }
        }
    }

    /**
     * Returns the grid.
     *
     * @return List<List<Cell>>
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
     * Method for testing purposes.
     * This method returns a serialized string of the initialized gameBoard describing each cell's state.
     * e.g."0010100101010"
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder serialized = new StringBuilder();
        for (int x = 0; x < grid.size(); x++) {
            for (int y = 0; y < grid.get(x).size(); y++) {
                String state = this.grid.get(x).get(y).getState() ? "1" : "0";
                serialized.append(state);
            }
        }
        return serialized.toString();
    }
}
