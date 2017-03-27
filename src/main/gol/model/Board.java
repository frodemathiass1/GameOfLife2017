package main.gol.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class Board {

    private int cellSize;
    private Cell[][] grid;
    private final GraphicsContext graphics;
    private ArrayList<Cell> generationList;

    private Color gridColor = Color.LIGHTGREY;
    private Color cellColor = Color.BLACK;
    private Color backgroundColor = Color.WHITE;



    /**
     * Board constructor. Takes graphicsContext and cellSize as argument
     *
     * @param graphics GraphicContext
     * @param cellSize int
     */
    public Board(GraphicsContext graphics, int cellSize) {
        this.cellSize = cellSize;
        this.graphics = graphics;
    }


    /**
     *
     * @param columns int
     * @param rows int
     */
    public void setBoard(int columns, int rows) {
        byte[][] board = new byte[rows][columns];
        //System.out.println(board[2][3]);
        this.setBoard(board);
    }

    /**
     *
     * @param board byte[][]
     */
    public void setBoard(byte[][] board) {

        //System.out.println(board.length);
        //System.out.println(board[0].length);

        int rows = board.length;
        int columns = board[0].length;
        grid = new Cell[columns][rows];
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Cell cell = new Cell(x, y);
                if (board[y][x] == 1) { // flip x and y axis. Why? because that's how it works
                    cell.setState(true);
                } else  {
                    cell.setState(false);
                }
                grid[x][y] = cell; // setBoard cell grid
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
     * Game of Life rules
     *
     * Collect next generation of cells in generationList
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
       // Update generation
       for(Cell cell : generationList){
            cell.updateState();
       }
       this.generationList = generationList;
    }


    /**
     * Draw next generation
     */
    public void drawGeneration(){
        for(Cell cell : this.generationList){
            //cell.updateState();
            drawCell(cell);
        }
    }


    /**
     * Draw cell and gridLine
     *
     * @param cell Cell
     */
    public void drawCell(Cell cell) {

        if (cell.getState()){
            graphics.setFill(cellColor);
            graphics.setStroke(gridColor);
        }
        else {
            graphics.setFill(backgroundColor);
            graphics.setStroke(gridColor);
        }
        graphics.fillRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
        graphics.strokeRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
    }


    /**
     * Draw the whole grid with each cell to canvas
     */
    public void drawGrid() {
        for (int x = 0; x < grid.length; x++){
            for (int y = 0; y < grid[x].length; y++){
                drawCell(grid[x][y]);
            }
        }
    }


    /**
     * Loops through array of cells and toggles alive cells to dead
     *
     * @param grid Cell[x][y]
     */
    public void clearBoard(Cell[][] grid){
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y].setState(false);
            }
        }
    }



    /**
     * Generate a random set of alive Cells and add them to generationList
     */
    public void makeRandomGenerations(){
        ArrayList<Cell> generationList = new ArrayList<>();
        Random rand = new Random();

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                Cell cell = grid[x][y];

                if (cell.getState() && cell.countAliveNeighbors() < rand.nextInt(8) ){
                    cell.setState(rand.nextBoolean());
                    generationList.add(cell);
                }
               else if(cell.getState() && (cell.countAliveNeighbors() == rand.nextInt(8) || cell.countAliveNeighbors() == rand.nextInt(4))){
                    cell.setState(rand.nextBoolean());
                    generationList.add(cell);
                }
                else if(cell.getState() && cell.countAliveNeighbors() > rand.nextInt(8)){
                    cell.setState(rand.nextBoolean());
                    generationList.add(cell);
                }
                else if(!cell.getState() && cell.countAliveNeighbors() == rand.nextInt(4)){
                    cell.setState(rand.nextBoolean());
                    generationList.add(cell);
                }
            }
        }
        this.generationList = generationList;
    }


    /**
     * Returns a cell within the array index bounds.
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

    // Getters
    public Cell[][] getGrid(){
        return this.grid;
    }

    public int getCellSize(){
        return this.cellSize;
    }


    // For testing. Counts alive neighbors on board for given cell coordinates
    public int countNeighbours(int x, int y) {
        Cell cell = getCell(x, y);
        return cell.countAliveNeighbors();
    }


    /**
     * For testing
     * returns a string of board byte values, 1 or 0
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder serialized = new StringBuilder();
        for (int x = 0; x < this.grid.length; x++) {
            for (int y = 0; y < this.grid[x].length; y++) {
                String state = this.grid[x][y].getState() ? "1" : "0";
                serialized.append(state);
            }
        }
        return serialized.toString();
    }
}