package main.gol.model.Boards;


/**
 * Class used for visual confirmation of testable Boards in GUI for JUnit BoardTests.
 *
 *
 */
public class TestBoards {


    public int rows;
    public int cols;


    private byte[][] testBoard = {
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };


    private byte[][] testBoard2 = {
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 0},
    };

    /**
     * Calculates the testBoard dimensions. Data is injected into the setBoard method
     * in Board class.
     *
     * @param testBoard byte[][]
     */
    public void findDimensions(byte[][] testBoard) {

        int y = 0;
        int x = 0;

        for (int row = 0; row < testBoard.length; row++) {
            for (int col = 0; col < testBoard[row].length; col++) {
                y++;
            }
            x++;
        }

        setCols(y);
        setRows(x);

    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public byte[][] getTestBoard() {
        return testBoard;
    }

    public byte[][] getTestBoard2() {
        return testBoard2;
    }
}
