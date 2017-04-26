package main.gol.model.boards;

import main.gol.model.Cell;
import main.gol.model.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class DynamicBoard {

    private List<List<Cell>> grid;
    private ArrayList<Cell>  generation;


    /**
     * DynamicBoard constructor
     *
     */
    public DynamicBoard(int rows, int columns) {

        setBoard(rows,columns);

    }

    /**
     * This method sets a cell outside the bounds of the dynamic grid.
     *
     * @param x int
     * @param y int
     * @param state boolean
     */
    public void setCellState(int x, int y, boolean state) {

        int rows = grid.size();
        int columns = grid.get(0).size();
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
                List<Cell> row = grid.get(i);
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
     * API:
     * Main method for setting a new grid, with byte[][] array as parameter.
     *
     * @param columns int
     * @param rows    int
     */
    public void setBoard(int columns, int rows) {

        byte[][] board = new byte[rows][columns]; // flipped x/y
        setGrid(board);
    }

    public void setGrid(byte[][] matrix) throws NullPointerException {


        int rows = matrix.length;
        int columns = matrix[0].length;

        grid = new ArrayList<>();
        for (int i = 0; i < rows; i++) {

            List<Cell> row = new ArrayList<>();
            this.grid.add(row);
        }

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                Cell cell = new Cell(x, y);
                if (matrix[x][y] == 1) { // flip x and y axis. Why? because that's how it works
                    cell.setState(true);
                } else if (matrix[x][y] == 0) {
                    cell.setState(false);
                }
                grid.get(x).add(cell);
            }
        }

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                grid.get(x).get(y).initNeighbors(this);
                //System.out.println("Neighbors: " + grid[x][y].getNeighbors());
            }
        }

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

        if (x < 0 || y < 0 || x >= grid.size() || y >= grid.get(x).size()) {

            return null;
        }
        else {

            return grid.get(x).get(y);
        }
    }


    /**
     * This method handles the Game of Life rules.
     * Checks each cell for it state counts and checks each neighbors state.
     * Collects the next generation of cells in a ArrayList of Cell objects.
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
        // Update generation
        for (Cell cell : generationList) {
            cell.updateState();
        }
        this.generation = generationList;
    }


    /**
     * This method generates a random set of alive cells
     */
    public void randomize() {

        ArrayList<Cell> generationList = new ArrayList<>();
        Random rand = new Random();
        for (int x = 0; x < grid.size(); x++) {
            for (int y = 0; y < grid.get(x).size(); y++) {
                Cell cell = grid.get(x).get(y);
                if (cell.countAliveNeighbors() < rand.nextInt(3)) {
                    cell.setState(rand.nextBoolean());
                    generationList.add(cell);
                }
            }
        }
        this.generation = generationList;
    }



    /**
     * This method loop through the cell grid and set all living cells to dead/false
     */
    public void clearBoard() {

        for (int x = 0; x < grid.size(); x++) {
            for (int y = 0; y < grid.get(x).size(); y++) {
                grid.get(x).get(y).setState(false);
            }
        }
    }


    /**
     * @return ArrayList grid
     */
    public List<List<Cell>> getGrid() {
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
        for (int x = 0; x < grid.size(); x++) {
            for (int y = 0; y < grid.get(x).size(); y++) {
                String state = this.grid.get(x).get(y).getState() ? "1" : "0";
                serialized.append(state);
            }
        }
        return serialized.toString();
    }
}
