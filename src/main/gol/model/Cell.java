package main.gol.model;

import main.gol.model.boards.DynamicBoard;
import main.gol.model.boards.FixedBoard;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This Cell class handles the cells in the grid, it's state and each neighbor of any cell.
 * The array/list of cells represents the grid on the User-interface.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.2
 */
public class Cell {

    private final int x, y;
    private boolean state = false;
    private boolean nextState;
    private List<Cell> neighbors;

    /**
     * This cell constructor constructs a cell with a given x/y coordinate.
     *
     * @param x int
     * @param y int
     */
    public Cell(int x, int y) {

        this.x = x;
        this.y = y;
    }

    /**
     * Updates the Cell state to remember its state for the next drawGeneration
     */
    public void updateState() {
        state = nextState;
    }

    /**
     * This method check each cell for surrounding live neighbors and return the amount as an int.
     * @return count int
     */
    public int countAliveNeighbors() {

        int count = 0;
        for (int i = 0; i < this.getNeighbors().size(); i++)
            if (this.neighbors.get(i).getState()) {
                count++;
            }
        return count;
    }

    /**
     * This method check each cell for surrounding dead neighbors and return the amount as an int.
     * Written for testing purposes.
     * @return int
     */
    public int countDeadNeighbors() {

        int count = 0;
        for (int i = 0; i < this.getNeighbors().size(); i++)
            if (!this.neighbors.get(i).getState()) {
                count++;
            }
        return count;
    }

    /**
     * This method initialize each cell's neighbors in a given DynamicBoard.
     *
     * @param board FixedBoard
     */
    public void initNeighbors(DynamicBoard board) {

        Cell topLeft = board.getCell(this.x - 1, this.y - 1);
        Cell top = board.getCell(this.x, this.y - 1);
        Cell topRight = board.getCell(this.x + 1, this.y - 1);
        Cell left = board.getCell(this.x - 1, this.y);
        Cell right = board.getCell(this.x + 1, this.y);
        Cell bottomLeft = board.getCell(this.x - 1, this.y + 1);
        Cell bottom = board.getCell(this.x, this.y + 1);
        Cell bottomRight = board.getCell(this.x + 1, this.y + 1);

        // Makes a stream of adjacent cells and filters out null cells(not on the map), then it collects it in a list.
        this.neighbors = Collections.unmodifiableList(Stream.of(topLeft, top, topRight, left, right, bottomLeft, bottom, bottomRight)
                .filter(Objects::nonNull) // Filter out non-existent neighbors
                .collect(Collectors.toList()));
    }

    /**
     * This method initialize each cell's neighbors in a given FixedBoard.
     * DUPLICATE CODE!
     *
     * @param board FixedBoard
     */
    public void initNeighborsFixed(FixedBoard board) {

        Cell topLeft = board.getCell(this.x - 1, this.y - 1);
        Cell top = board.getCell(this.x, this.y - 1);
        Cell topRight = board.getCell(this.x + 1, this.y - 1);
        Cell left = board.getCell(this.x - 1, this.y);
        Cell right = board.getCell(this.x + 1, this.y);
        Cell bottomLeft = board.getCell(this.x - 1, this.y + 1);
        Cell bottom = board.getCell(this.x, this.y + 1);
        Cell bottomRight = board.getCell(this.x + 1, this.y + 1);

        // Makes a stream of adjacent cells and filters out null cells(not on the map), then it collects it in a list.
        this.neighbors = Stream.of(topLeft, top, topRight, left, right, bottomLeft, bottom, bottomRight)
                .filter(Objects::nonNull) // Filter out non-existent neighbors
                .collect(Collectors.toList());
    }

    /**
     * This method returns the ArrayList of neighbors.
     *
     * @return List neighbors
     */
    public List<Cell> getNeighbors() {
        return this.neighbors;
    }

    /**
     * This method sets the Cell state.
     *
     * @param state boolean
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * This method returns the Cell state.
     *
     * @return state boolean
     */
    public boolean getState() {
        return this.state;
    }

    /**
     * This method sets the Cell's next state.
     *
     * @param ns boolean
     */
    public void setNextState(boolean ns) {
        this.nextState = ns;
    }

    /**
     * This method returns the Cell's x coordinate.
     *
     * @return x int
     */
    public int getX() {
        return this.x;
    }

    /**
     * This method returns the Cell's y coordinate.
     * @return y int
     */
    public int getY() {
        return this.y;
    }

    /**
     * This method returns a string with the Cell's object state data
     * For debugging purposes.
     *
     * @return String
     */
    @Override
    public String toString() {
        return state + " ";
    }
}
