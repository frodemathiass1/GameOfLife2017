package main.gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class Board {


    public int cellSize;
    private int columns = 80;
    private int rows = 55;
    private Cell[][] grid;
    private GraphicsContext graphics;
    private Color color = Color.BLACK;


    /**
     * Board constructor
     *
     * @param graphics GraphicContext
     * @param cellSize int
     */
    public Board(GraphicsContext graphics, int cellSize) {
        this.cellSize = cellSize;
        this.graphics = graphics;
        this.initialize();
    }



    /**
     * Instantiate grid with cells and set each neighbor
     */
    private void initialize() {
        grid = new Cell[columns][rows];
        for (int x = 0; x < this.grid.length; x++) {
            for (int y = 0; y < this.grid[x].length; y++) {
                Cell cell = new Cell(x, y);
                grid[x][y] = cell; // initialize cell grid
            }
        }

        // We are working with references so we don't need to update its neighbors unless the map is reinitialized (cell toggles to alive when clicked).
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y].initNeighbors(this);
                //System.out.println("Neighbors: " + grid[x][y].getNeighbors());
            }
        }
    }


    /**
     * Generate next generation of cells and draw to canvas
     * Game of Life rules
     */
    public void nextGeneration(){
        ArrayList<Cell> generationList = new ArrayList<>();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                Cell cell = grid[x][y];

                if ( cell.getState() && cell.countAliveNeighbors() < 2 ){

                    cell.setNextState(false);
                    generationList.add(cell);
                }
                else if( cell.getState() && (cell.countAliveNeighbors()==2 || cell.countAliveNeighbors()==3)){

                    cell.setNextState(true);
                    generationList.add(cell);
                }
                else if( cell.getState() && cell.countAliveNeighbors() > 3){

                    cell.setNextState(false);
                    generationList.add(cell);
                }
                else if(!cell.getState() && cell.countAliveNeighbors() == 3){

                    cell.setNextState(true);
                    generationList.add(cell);
                }
            }
        }
       // prints out the next generation
       for(Cell cell : generationList){
           drawCell(cell);
       }
    }


    /**
     *
     * @param cell Cell
     */
    public void drawCell(Cell cell) {
        if (cell.getNextState()){
            this.graphics.setFill(color);
            cell.setState(true);
        }
        else {
              this.graphics.setFill(Color.LIGHTGRAY);
              cell.setState(false);
        }
        this.graphics.setStroke(Color.WHITE); // Sets grid color
        this.graphics.setLineWidth(0.3);
        this.graphics.fillRect(cell.getX() * this.cellSize, cell.getY() * this.cellSize, this.cellSize, this.cellSize);
        this.graphics.strokeRect(cell.getX() * this.cellSize, cell.getY() * this.cellSize, this.cellSize, this.cellSize);
    }


    /**
     * Draw grid to canvas
     */
    public void drawGrid() {
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                drawCell(grid[i][j]);
    }




    /**
     * Loops through array of cells and toggles alive cells to dead
     *
     * @param cells
     */
    public void clearBoard(Cell[][] cells){
        for(int i =0; i< cells.length; i++)
            for(int j=0; j < cells[j].length; j++)
                cells[i][j].setNextState(false);
    }


    /**
     *
     * @param color
     */
    public void setPickedColor(Color color){
        this.color = color;
    }

    /**
     *
     * @param cellSize int
     */
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    /**
     *
     * @param rows int
     */
    public void setRows(int rows){
        this.rows=rows;
    }

    /**
     *
     * @param cols int
     */
    public void setColumns(int cols){
        this.columns=cols;
    }

    /**
     *
     * @param x
     * @param y
     * @return Cell[][]
     */
    public Cell getCell(int x, int y) {
        if (x < 0 || y < 0 || x >= grid.length || y >= grid[x].length)
            return null;
        return this.grid[x][y];
    }

    /**
     *
     * @return Cell[][]
     */
    public Cell[][] getGrid(){
        return this.grid;
    }

    /**
     *
     * @return int
     */
    public int getCellSize(){
        return this.cellSize;
    }

}