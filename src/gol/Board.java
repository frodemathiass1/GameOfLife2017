package gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Board {

    public int cellSize;
    private int columns = 60;
    private int rows = 40;
    private Cell[][] grid;
    private GraphicsContext graphics;
    private Color color = Color.BLACK;


    public Board(GraphicsContext graphics, int cellSize) {
        this.cellSize = cellSize;
        this.initialize();
        this.graphics = graphics;

    }

    // Initializes the Board matrix (grid) with dead cells including its neighbors
    private void initialize() {
        grid = new Cell[columns][rows];

        for (int x = 0; x < this.grid.length; x++) {
            for (int y = 0; y < this.grid[x].length; y++) {
                Cell cell = new Cell(x, y);
                cell.updateNeighbors(this);
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
        ArrayList<Cell> alive = new ArrayList<>();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                Cell cell = grid[x][y];
                // Add all the cells that are alive to a list of cells, then set the cell to not be alive and repaint it
                if (cell.isAlive()) {
                    alive.add(cell);
                    cell.setAlive(false);
                    drawCell(cell);
                }
            }
        }
        // Iterate through all the cells that were alive and update their neighbors to have the opposite state
        alive.forEach(cellAlive -> cellAlive.getNeighbors().forEach(cell -> {
            cell.setAlive(!cell.isAlive());
            drawCell(cell);
        }));
    }



    // Invoked by the Board constructor which takes Graphic Content as argument
    public void drawCell(Cell cell) {
        //System.out.println(cell.getX());
        //System.out.println(cell.getY());
        if (cell.isAlive()) {
            this.graphics.setFill(color);
        }
        else {
            this.graphics.setFill(Color.LIGHTGREY);
        }

        this.graphics.setStroke(Color.WHITE); // Sets grid color
        this.graphics.setLineWidth(0.3); // sets the width of the grid line
        this.graphics.fillRect(cell.getX() * this.cellSize, cell.getY() * this.cellSize, this.cellSize, this.cellSize);
        this.graphics.strokeRect(cell.getX() * this.cellSize, cell.getY() * this.cellSize, this.cellSize, this.cellSize);
    }


    public void drawGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                drawCell(grid[i][j]);
            }
        }
    }


    // Loops through array of cells and toggles alive cells to dead
    public void clearBoard(Cell[][] cells){
        for(int i =0; i< cells.length; i++){
            for(int j=0; j < cells[j].length; j++){
                cells[i][j].setAlive(false);
            }
        }
    }



    // Setters
    public void setPickedColor(Color c){
        this.color = c;
    }
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    // Getters
    public Cell getCellCoordinates(int x, int y) {
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