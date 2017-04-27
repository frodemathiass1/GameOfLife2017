package main.gol.model;

import java.util.ArrayList;

/**
 * This class contains and executes the Game of Life algorithm for the cell automation.
 *
 *  From Wikipedia
 *
 *      The universe of the Game of Life is an infinite two-dimensional orthogonal grid of square cells,
 *      each of which is in one of two possible states, alive or dead, or populated or unpopulated.
 *
 *      Every cell interacts with its eight neighbours, which are the cells that are horizontally,
 *      vertically, or diagonally adjacent. At each step in time, the following transitions occur:
 *
 *      1: Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.
 *
 *      2: Any live cell with two or three live neighbours lives on to the next generation.
 *
 *      3: Any live cell with more than three live neighbours dies, as if by overpopulation.
 *
 *      4: Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 *
 */
public class Rules {

    /**
     * This method checks checks if the rules applies to each cell, by checking its neighbors
     * then adds it to the list of Cells
     *
     * @param cell Cell
     * @param generations ArrayList
     */
    public void checkRules(Cell cell, ArrayList<Cell> generations) {

        // dies, as if by underpopulation.
        if (cell.getState() && cell.countAliveNeighbors() < 2) {
            cell.setNextState(false);
            generations.add(cell);
        }
        // lives on to the next generation.
        else if (cell.getState() && (cell.countAliveNeighbors() == 2 || cell.countAliveNeighbors() == 3)) {
            cell.setNextState(true);
            generations.add(cell);
        }
        // dies, as if by overpopulation.
        else if (cell.getState() && cell.countAliveNeighbors() > 3) {
            cell.setNextState(false);
            generations.add(cell);
        }
        // becomes a live cell, as if by reproduction.
        else if (!cell.getState() && cell.countAliveNeighbors() == 3) {
            cell.setNextState(true);
            generations.add(cell);
        }
    }
}