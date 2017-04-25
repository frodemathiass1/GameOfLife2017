package main.gol.model.boards;


/**
 * This class contains a set of size configuration data for the gameBoard.
 */
public class Config {

    private static int cellSize = 5;
    private static int rows = 110;
    private static int columns = 160;

    private static int MAX_ROWS = 200;      // for setting up dynamic board expansion
    private static int MAX_COLUMNS = 200;   // for setting up dynamic board expansion


    /**
     * This method sets up the default configuration
     */
    public void setDefault(){

        setRows(110);
        setColumns(160);
        setCellSize(5);
    }

    // Getters
    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int cellSize() {
        return cellSize;
    }

    // Setters
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
}
