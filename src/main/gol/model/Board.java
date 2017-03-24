package main.gol.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.*;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class Board {

    // testing the test
    public String ping(){
        return "Ping!";
    }

    private int cellSize;
    private int columns = 160;
    private int rows = 110;
    private Cell[][] grid;
    private final GraphicsContext graphics;

    // Does not belong in this class (Why not fix it then?)
    private Color gridColor = Color.LIGHTGREY;
    private Color cellColor = Color.BLACK;
    private Color backgroundColor = Color.WHITE;

    /**
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

                // dies, as if by underpopulation.
                if (cell.getState() && cell.countAliveNeighbors() < 2 ){
                    cell.setNextState(false);
                    generationList.add(cell);
                }
                // lives on to the next generation.
                else if(cell.getState() && (cell.countAliveNeighbors() == 2 || cell.countAliveNeighbors() == 3)){
                    cell.setNextState(true);
                    generationList.add(cell);
                }
                // dies, as if by overpopulation.
                else if(cell.getState() && cell.countAliveNeighbors() > 3){
                    cell.setNextState(false);
                    generationList.add(cell);
                }
                // becomes a live cell, as if by reproduction.
                else if(!cell.getState() && cell.countAliveNeighbors() == 3){
                    cell.setNextState(true);
                    generationList.add(cell);
                }
            }
        }
       // Draw next generation
       for(Cell cell : generationList){
           drawCell(cell);
       }
    }

    /**
     *
     * @return DropShadow
     */
    public DropShadow makeShadow(){
        DropShadow ds = new DropShadow();
        ds.setRadius(5.0);
        ds.setOffsetX(3.0);
        ds.setOffsetY(3.0);
        ds.setColor(Color.BLACK);
        return ds;
    }

    /**
     *
     * @param cell Cell
     */
    public void drawCell(Cell cell) {
        if (cell.getNextState()){
            graphics.setFill(cellColor); // Set cell color
            graphics.setStroke(cellColor); // Sets grid color to cell color
            cell.setState(true);
        }
        else {
            graphics.setFill(backgroundColor); // Set background color
            graphics.setStroke(gridColor); // Sets selected grid color
            cell.setState(false);
        }
        graphics.fillRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
        graphics.strokeRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
    }

    /**
     * Draw grid to canvas
     */
    public void drawGrid() {
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                drawCell(grid[i][j]);
            }
        }
    }

    /**
     * Loops through array of cells and toggles alive cells to dead
     *
     * @param cells Cell[x][y]
     */
    public void clearBoard(Cell[][] cells){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j].setNextState(false);
            }
        }
    }

    // Setters
    public void setCellColor(Color color){
        this.cellColor = color;
    }

    public void setGridColor(Color color){
        this.gridColor = color;
    }

    public void setBcColor(Color color){
        this.backgroundColor = color;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public void setRows(int rows){
        this.rows = rows;
    }

    public void setColumns(int cols){
        this.columns = cols;
    }

    // Getters
    public Cell[][] getGrid(){
        return this.grid;
    }

    public int getCellSize(){
        return this.cellSize;
    }

    /**
     * Returns a cell within the array index
     * @param x int
     * @param y int
     * @return cell
     */
    public Cell getCell(int x, int y) {
        if (x < 0 || y < 0 || x >= grid.length || y >= grid[x].length){
            return null;
        }
        else {
            return this.grid[x][y];
        }
    }

    /**
     * Generates a random set of alive Cells
     */
    public void makeRandomGenerations(){
        ArrayList<Cell> generationList = new ArrayList<>();
        Random rand = new Random();

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                Cell cell = grid[x][y];

                if (cell.getState() && cell.countAliveNeighbors() < rand.nextInt(8) ){
                    cell.setNextState(rand.nextBoolean());
                    generationList.add(cell);
                }
               else if(cell.getState() && (cell.countAliveNeighbors() == rand.nextInt(8) || cell.countAliveNeighbors() == rand.nextInt(4))){
                    cell.setNextState(rand.nextBoolean());
                    generationList.add(cell);
                }
                else if(cell.getState() && cell.countAliveNeighbors() > rand.nextInt(8)){
                    cell.setNextState(rand.nextBoolean());
                    generationList.add(cell);
                }
                else if(!cell.getState() && cell.countAliveNeighbors() == rand.nextInt(4)){
                    cell.setNextState(rand.nextBoolean());
                    generationList.add(cell);
                }
            }
        }
        // Draw the next generation
        for(Cell cell : generationList){
            drawCell(cell);
        }
    }

}