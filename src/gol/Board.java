package gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class Board {

    private int cellSize;
    private int colums = 30;
    private int rows = 20;
    private Cell[][] cells;
    private GraphicsContext gc;


    /*
   * Board constructor triggers the board initialisation method and sets the boards cells' size
   * and delivers graphic content data to the GraphicContext object variable that is used
   * to draw the board and cells by mouseEvents
   */
    // Board Constructor
    public Board(GraphicsContext gc, int cellSize) {
        this.cellSize = cellSize;
        this.initialize();
        this.gc = gc;
    }

    /*
   *
   * Method responsible for initializing the 2d Cell array (Grid).
   * All the individual Cells are called when the Grid(array) is initialized with their position
   * which are triggered by mouseEvents method in controller
   */

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


    // Method is called by the MouseClick event which asks every Cell in the
    // array of Cell objects in Board Class which are being returned here
    //
    public Cell getCell(int x, int y) {
        return this.cells[x][y];
    }



    // Invoked by the Board constructor which takes Graphic Content as argument
    // Draw cell to canvas
    public void drawCell(Cell cell) {
        System.out.println(cell.getX());
        System.out.println(cell.getY());

        if (cell.isAlive()) {
            this.gc.setFill(Color.BLACK);
            // Need colorPicker object here to assign colorPicker value?
        } else {
            this.gc.setFill(Color.LIGHTGREY);


        }
        /*
        * Formula for drawing the Cells in their correct position next to eachother
        * in the board grid of dead cells. Cell x Cell = next position.
        * Each x,y coordinate for every cell in the array of cells is being called in this codeline.
        * In other words, each cell is being asked for its position in this line.
        * */
        this.gc.fillRect(cell.getX() * this.cellSize, cell.getY() * this.cellSize, this.cellSize, this.cellSize);
    }



}