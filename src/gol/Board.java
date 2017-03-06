package gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    public int cellSize;
    private int colums = 60;
    private int rows = 40;
    private Cell[][] grid;
    private GraphicsContext graphics;
    private Color color = Color.BLACK;
    private ArrayList<Integer> neighbors;
    //protected ArrayList<ArrayList<Cell>>[][] grid2;


    public Board(GraphicsContext graphics, int cellSize) {
        this.cellSize = cellSize;
        this.initialize();
        this.graphics = graphics;

    }


    // Initializes the Board matrix (grid) with dead cells including its neighbors
    // All individual cells are called when the grid is initialized with their coordinates
    // triggered by mouse-events in controller class.
    private void initialize() {
        grid = new Cell[colums][rows];
        //grid2 = new ArrayList[colums][rows];

        for (int x = 0; x < this.grid.length; x++) {
            for (int y = 0; y < this.grid[x].length; y++) {
                grid[x][y] = new Cell(x, y); // initialize cell grid
                //grid2<Cell>[x][y]=new Cell(x, y);

    // To do: initialize each cells neighbors...
    // Instantiate neigbours at gameboard initialisation ...or in Cell class? See alternative in cell class
    // Må vel være en bedre måte å gjøre det her på????????


                //loop som skal adde naboer kun innenfor kantene ,funker ikke helt... still out of bounds
             /*
                 for (int rowMod = 1; rowMod < grid.length - 1; rowMod++) {
                        for (int colMod = 1; colMod < grid[rowMod].length - 1; colMod++) {
                            if (rows >= 0 && colums >= 0) {

                            }
                        }
                    }
             */

                neighbors = new ArrayList<>();

                // 1.Top-left
                neighbors.add(this.grid[x][y].getX() - 1);
                neighbors.add(this.grid[x][y].getY() - 1);


                // 2.Top
                neighbors.add(this.grid[x][y].getX());
                neighbors.add(this.grid[x][y].getY() - 1);

                // 3.Top-right
                neighbors.add(this.grid[x][y].getX() + 1);
                neighbors.add(this.grid[x][y].getY() - 1);

                // 4.Left
                neighbors.add(this.grid[x][y].getX() - 1);
                neighbors.add(this.grid[x][y].getY());


                // 5.Right
                neighbors.add(this.grid[x][y].getX() + 1);
                neighbors.add(this.grid[x][y].getY());

                // 6.Bottom-left
                neighbors.add(this.grid[x][y].getX() - 1);
                neighbors.add(this.grid[x][y].getY() + 1);

                // 7.Bottom
                neighbors.add(this.grid[x][y].getX());
                neighbors.add(this.grid[x][y].getY() + 1);

                // 8.Bottom-right
                neighbors.add(this.grid[x][y].getX() + 1);
                neighbors.add(this.grid[x][y].getY() + 1);


                // Debugging: printing cells and neighbors
                System.out.println("Cell:  X: " + grid[x][y].getX() + " | Y: " + grid[x][y].getY()); // Cell coordinate
                System.out.print("Neighbors: ");

                for (int i = 0; i < neighbors.size(); i++) {
                    System.out.print(neighbors.get(i)+", ");
                }
                System.out.println();
                System.out.println();
            }
        }

    }

    public void nextGeneration(){
        // tegn grafikk til canvas basert på gol regler


    }

    public  Integer neighborCounter=0;
    public boolean foundAlive=false;

    public void resetCounter(){
        neighborCounter=0;
    }


    public void checkNeighbors(){
      // regler ?
      // gå igjennom alle naboer og sjekk etter levende celler
      // ta vare på tilstander ... og posisjoner ?


        // loops through grid and looks for alive cells
        for(int x = 0; x <this.grid.length; x++){
            for(int y=0; y < this.grid[x].length; y++){

                // If cell is alive check corresponding neighbour array for alive cells(??)
                // but will not work bcus neighbor is array of Integers (x/y positions) ?
                if(this.grid[x][y].isAlive()==true){ // sjekk etter levende naboer

                    foundAlive=true;
                    neighborCounter++;  // Må finne levende naboer for at dette skal fungere
                    System.out.println(neighborCounter);


                    // Game of Life Rules
                    if( (grid[x][y].isAlive()==true) && (neighborCounter < 2) ){ // underpopulation
                            // do something...
                    }
                    else if( (grid[x][y].isAlive()==true) && (neighborCounter ==2 || neighborCounter == 3) ){ // next generation
                        // do something...
                    }
                    else if( (grid[x][y].isAlive()==true) && (neighborCounter > 3) ){ // overpopulation
                         // do something...
                    }
                    else if( (grid[x][y].isAlive()==false) && (neighborCounter > 3) ){  //reproduction
                         // do something...
                    }






                }
            }

        }
        System.out.println(" ---  "+neighborCounter);
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
                if(cells[i][j].isAlive()==true){
                    cells[i][j].setAlive(false);
                }
            }
        }
    }



    // Setters
    public void setPickedColor(Color c){
        this.color = c;
    }
    public void setCellSize(int cellsize) {
        this.cellSize = cellsize;
    }

    // Getters
    public Cell getCellCoordinates(int x, int y) {
        return this.grid[x][y];
    }

    public Cell[][] getGrid(){
        return this.grid;
    }

    public int getCellSize(){
        return this.cellSize;
    }


}