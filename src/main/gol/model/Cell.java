package main.gol.model;

import main.gol.model.boards.DynamicBoard;
import main.gol.model.boards.FixedBoard;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This Cell class handles each cell, it's state and each neighbor of any cell.
 * The array of cells represents the grid on the User-interface.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.0
 */
public class Cell {

    private final int x, y;
    private boolean state = false;
    private boolean nextState;
    private List<Cell> neighbors;

    /**
     * Cell Constructor
     *
     * @param x int
     * @param y int
     */
    public Cell(int x, int y) {

        this.x = x;
        this.y = y;
    }

    /**
     * Updates the Cell state to remember its state for the next generation
     */
    public void updateState() {

        state = nextState;
    }

    /**
     * @return count int
     */
    public int countAliveNeighbors() {

        int count = 0;
        for (int i = 0; i < this.getNeighbors().size(); i++)
            if (this.neighbors.get(i).getState()) {
                //System.out.println(this.neighbors.get(i));
                count++;
            }
        //System.out.println(neighborCount);
        return count;
    }

    /**
     * List of neighbors
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
        this.neighbors = Stream.of(topLeft, top, topRight, left, right, bottomLeft, bottom, bottomRight)
                .filter(Objects::nonNull) // Filter out non-existent neighbors
                .collect(Collectors.toList());
    }

    /**
     * FIX SUPPORT FOR FIXED BOARD OLD VERSION
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
     * @return List neighbors
     */
    private List<Cell> getNeighbors() {

        return this.neighbors;
    }

    /**
     * @param state boolean
     */
    public void setState(boolean state) {

        this.state = state;
    }

    /**
     * @return state boolean
     */
    public boolean getState() {

        return this.state;
    }

    /**
     * Set next state (dead/alive)
     *
     * @param ns boolean
     */
    public void setNextState(boolean ns) {

        this.nextState = ns;
    }

    /**
     * @return x int
     */
    public int getX() {

        return this.x;
    }

    /**
     * @return y int
     */
    public int getY() {

        return this.y;
    }

    /**
     * Returns a string with Cell object state data
     *
     * @return String
     */
    @Override
    public String toString() {

        return state + " ";
    }

}
