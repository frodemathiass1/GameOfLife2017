package main.gol.model.boards;

/**
 * This class contains size configuration data for the gameBoard.
 * <p>
 * The variables and methods in this class are separated from the GUI controller
 * for easier management, manipulation and readability.
 *
 * @version 1.2
 */
public class Config {

    private static int cellSize = 5;
    private static int columns = 160;
    private static int rows = 104;

    /**
     * This method sets the default board configuration.
     */
    public void setDefault() {

        setCellSize(5);
        setColumns(160);
        setRows(104);
    }

    /**
     * This method returns the number of columns.
     *
     * @return int
     */
    public int getColumns() {
        return columns;
    }

    /**
     * This method returns the number of rows.
     *
     * @return int
     */
    public int getRows() {
        return rows;
    }

    /**
     * This method returns the cellSize.
     *
     * @return int
     */
    public int getCellSize() {
        return cellSize;
    }

    /**
     * This method sets the cellSize.
     *
     * @param cellSize int
     */
    public void setCellSize(int cellSize) {
        Config.cellSize = cellSize;
    }

    /**
     * This method sets the number of rows.
     *
     * @param rows int
     */
    public void setRows(int rows) {
        Config.rows = rows;
    }

    /**
     * This method sets the number of columns.
     *
     * @param columns int
     */
    public void setColumns(int columns) {
        Config.columns = columns;
    }
}