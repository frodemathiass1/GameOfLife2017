package main.gol.model;


/**
 * This class contains and executes the Game of Life algorithm for the cell automation.
 * <p>
 *     From Wikipedia
 * <p>
 *     The universe of the Game of Life is an infinite two-dimensional orthogonal drawBoard of square cells,
 *     each of which is in one of two possible states, alive or dead, or populated or unpopulated.
 * <p>
 *     Every drawCell interacts with its eight neighbours, which are the cells that are horizontally,
 *     vertically, or diagonally adjacent. At each step in time, the following transitions occur:
 * <ol>
 * <li>Any live Cell with fewer than two live neighbours dies, as if caused by underpopulation.
 * <li>Any live Cell with two or three live neighbours lives on to the next drawGeneration.
 * <li>Any live Cell with more than three live neighbours dies, as if by overpopulation.
 * <li>Any dead Cell with exactly three live neighbours becomes a live drawCell, as if by reproduction.
 * </ol>
 * <p>
 * @version 1.2
 */
public class Rules {

    /**
     * This method checks checks if the rules applies to each Cell, by checking its neighbors
     * then adds it to the list of Cells
     *
     * @param cell Cell
     * @return boolean
     */
    public boolean checkRules(Cell cell) {

        if (cell.getState() && cell.countAliveNeighbors() < 2) {
            // dies, as if by underpopulation.
            cell.setNextState(false);
        } else if (cell.getState() && (cell.countAliveNeighbors() == 2 || cell.countAliveNeighbors() == 3)) {
            // lives on to the next drawGeneration.
            cell.setNextState(true);
        } else if (cell.getState() && cell.countAliveNeighbors() > 3) {
            // dies, as if by overpopulation.
            cell.setNextState(false);
        } else if (!cell.getState() && cell.countAliveNeighbors() == 3) {
            // becomes a live drawCell, as if by reproduction.
            cell.setNextState(true);
        } else {
            return false;
        }
        return true;
    }
}