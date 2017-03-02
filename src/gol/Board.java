package gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class Board {

    public int cellSize;
    private int colums = 50;
    private int rows = 40;
    private Cell[][] cells;
    private GraphicsContext gc;
    private Color color = Color.BLACK;

    public void setColor(Color c){
        this.color = c;
    }

    /*
   * Board constructor triggers the board initialisation method and sets the boards cells' size
   * and delivers graphic content data to the GraphicContext object variable that is used
   * to draw the board and cells by mouseEvents
   */
    // Board Constructor

    public Board() {

    }

    public Board(GraphicsContext gc, int cellSize) {
        this.cellSize = cellSize;
        this.initialize();
        this.gc = gc;
    }

    public int getCellSize(){
        return this.cellSize;
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
        //System.out.println(cell.getX());
        //System.out.println(cell.getY());

        if (cell.isAlive()) {
            this.gc.setFill(color);



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
        this.gc.setStroke(Color.WHITE);
        this.gc.setLineWidth(0.3);
        this.gc.strokeRect(cell.getX() * this.cellSize, cell.getY() * this.cellSize, this.cellSize, this.cellSize);
    }

    public void draw() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                drawCell(cells[i][j]);
            }
        }
    }


    public void setCellSize(int cellsize) {
        this.cellSize = cellsize;
    }

    public Color getColor() {
        return color;
    }
}