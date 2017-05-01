package main.gol.model.boards;

/**
 * This class contains size configuration data for the gameBoard.
 * The variables and methods in this class are separated from th GUI controller
 * for easier management, manipulation and readability.
 *
 * @version 1.2
 */
public class Config {

    private int cellSize = 5;
    private int columns = 160;
    private int rows = 110;


    /**
     * This method returns the number of columns.
     * @return int
     */
    public int getColumns() {
        return columns;
    }

    /**
     * This method returns the number of rows.
     * @return int
     */
    public int getRows() {
        return rows;
    }

    /**
     * This method returns the cellSize.
     * @return int
     */
    public int cellSize() {
        return cellSize;
    }

    /**
     * This method sets the cellSize.
     * @param cellSize int
     */
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    /**
     * This method sets the number of columns.
     * @param columns int
     */
}
