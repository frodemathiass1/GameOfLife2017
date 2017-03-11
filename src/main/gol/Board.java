package main.gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Board {


    public int cellSize;
    private int columns = 60;
    private int rows = 36;
    private Cell[][] grid;
    private GraphicsContext graphics;
    private Color color = Color.BLACK;




    public Board(GraphicsContext graphics, int cellSize) {
        this.cellSize = cellSize;
        this.graphics = graphics;
        this.initialize();
    }




    // Initializes the Board matrix (grid) with dead cells including its neighbors
    private void initialize() {
        grid = new Cell[columns][rows];
        for (int x = 0; x < this.grid.length; x++) {
            for (int y = 0; y < this.grid[x].length; y++) {
                Cell cell = new Cell(x, y);
                cell.updateNeighbors(this);  // update cell neighbors
                grid[x][y] = cell; // initialize cell grid
            }
        }

        // We are working with references so we don't need to update its neighbors unless the map is reinitialized (cell toggles to alive when clicked).
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y].updateNeighbors(this);
                //System.out.println("Neighbors: " + grid[x][y].getNeighbors());
            }
        }
    }





    public void nextGeneration(){
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                Cell cell = grid[x][y];

                if ( cell.isAlive() && cell.countAliveNeighbors() < 2 ){

                    cell.setAlive(false);
                    cell.updateNeighbors(this);
                    drawCell(cell);
                }
                else if( cell.isAlive() && (cell.countAliveNeighbors()==2 || cell.countAliveNeighbors()==3)){

                    cell.setAlive(true);
                    cell.updateNeighbors(this);
                    drawCell(cell);
                }
                else if( cell.isAlive() && cell.countAliveNeighbors() > 3){

                    cell.setAlive(false);
                    cell.updateNeighbors(this);
                    drawCell(cell);
                }
                else if(!cell.isAlive() && cell.countAliveNeighbors() == 3){

                    cell.setAlive(true);
                    cell.updateNeighbors(this);
                    drawCell(cell);
                }
            }
        }
    }





    // Invoked by the Board constructor which takes Graphic Content as argument
    public void drawCell(Cell cell) {
        if (cell.isAlive())
            this.graphics.setFill(color);
        else
            this.graphics.setFill(Color.LIGHTGRAY);

        this.graphics.setStroke(Color.WHITE); // Sets grid color
        this.graphics.setLineWidth(0.3);
        this.graphics.fillRect(cell.getX() * this.cellSize, cell.getY() * this.cellSize, this.cellSize, this.cellSize);
        this.graphics.strokeRect(cell.getX() * this.cellSize, cell.getY() * this.cellSize, this.cellSize, this.cellSize);
    }





    // Draw grid
    public void drawGrid() {
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                drawCell(grid[i][j]);
    }




    // Loops through array of cells and toggles alive cells to dead
    public void clearBoard(Cell[][] cells){
        for(int i =0; i< cells.length; i++)
            for(int j=0; j < cells[j].length; j++)
                cells[i][j].setAlive(false);
    }






    // Setters
    public void setPickedColor(Color c){
        this.color = c;
    }
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }
    public void setRows(int r){
        this.rows=r;
    }
    public void setColumns(int c){
        this.columns=c;
    }

    // Getters
    public Cell getCell(int x, int y) {
        if (x < 0 || y < 0 || x >= grid.length || y >= grid[x].length)
            return null;
        return this.grid[x][y];
    }

    public Cell[][] getGrid(){
        return this.grid;
    }

    public int getCellSize(){
        return this.cellSize;
    }





}