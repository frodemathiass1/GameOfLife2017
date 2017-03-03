package gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class Board {

    public int cellSize;
    private int colums = 60;
    private int rows = 40;
    private Cell[][] grid;
    private GraphicsContext gc;
    private Color color = Color.BLACK;


    /*
   * Board constructor triggers the board initialisation method and sets the boards cells' size
   * and delivers graphic content data to the GraphicContext object variable that is used
   * to draw the board and cells by mouseEvents
   */
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

    private double [][] neigbours;

    private void initialize() {
        this.grid = new Cell[colums][rows];
        for (int x = 0; x < this.colums; x++) {
            for (int y = 0; y < this.rows; y++) {
                // initialize all cells (default dead) selve spillbrettet
                this.grid[x][y] = new Cell(x, y);

                // To do: initialize each cells neigbours...

                // Each cell's coordinates debugging
                System.out.println(this.grid[x][y].getX()+" "+this.grid[x][y].getY());

            }
        }
    }



    // Invoked by the Board constructor which takes Graphic Content as argument
    public void drawCell(Cell cell) {
        //System.out.println(cell.getX());
        //System.out.println(cell.getY());
        if (cell.isAlive()) {
            this.gc.setFill(color);
        }else {
            this.gc.setFill(Color.LIGHTGREY);
        }


        this.gc.setStroke(Color.WHITE); // Sets grid color
        this.gc.setLineWidth(0.3); // sets the width of the grid line
        this.gc.fillRect(cell.getX() * this.cellSize, cell.getY() * this.cellSize, this.cellSize, this.cellSize);
        this.gc.strokeRect(cell.getX() * this.cellSize, cell.getY() * this.cellSize, this.cellSize, this.cellSize);
    }


    public void draw() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                drawCell(grid[i][j]);
            }
        }
    }


    // Loop through Cell array and kill alive cells  (Clear board)
    public void killCells(Cell[][] cells){
        for(int i =0; i< cells.length; i++){
            for(int j=0; j < cells[j].length; j++){
                if(cells[i][j].isAlive()==true){
                    cells[i][j].setAlive(false);
                }
            }
        }
    }





    // Setters
    public void setColor(Color c){
        this.color = c;
    }
    public void setCellSize(int cellsize) {
        this.cellSize = cellsize;
    }

    // Getters
    // getCell Method is called by the MouseClick event which asks every Cell in the array of Cell objects for its coordinates
    public Cell getCell(int x, int y) {return this.grid[x][y];}
    public Cell[][] getGrid(){return this.grid;}
    public int getCellSize(){return this.cellSize;}


}