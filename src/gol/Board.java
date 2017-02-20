package gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board {

    private int cellSize;
    private int colums = 4;
    private int rows = 4;
    private Cell[][] cells;
    private GraphicsContext gc;


    // Initialize game board
    private void initialize() {
        this.cells = new Cell[colums][rows];
        for (int x = 0; x < this.colums; x++) {
            for (int y = 0; y < this.rows; y++) {
                // initialize all cells (default dead)  selve spillbrettet
                this.cells[x][y] = new Cell(x, y);
                // To do: initialize each cells neigbours...
            }
        }
    }

    // Board Constructor
    public Board(GraphicsContext gc, int cellSize) {
        this.cellSize = cellSize;
        this.initialize();
        this.gc = gc;
    }


    // Draw cell to canvas
    public void drawCell(Cell cell) {
        // System.out.println(cell.getX());
        //  System.out.println(cell.getY());
        if (cell.isAlive()) {
            this.gc.setFill(Color.BLACK);

        } else {
            this.gc.setFill(Color.LIGHTGRAY);
        }
        this.gc.fillRect(cell.getX() * this.cellSize, cell.getY() * this.cellSize, this.cellSize, this.cellSize);
    }

    // returns cells
    public Cell getCell(int x, int y) {
        return this.cells[x][y];
    }
}