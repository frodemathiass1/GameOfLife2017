package gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Board {

    public int cellSize;
    private int colums = 20;
    private int rows = 10;
    private Cell[][] grid;
    private GraphicsContext graphics;
    private Color color = Color.BLACK;
    //private int[] neighbors;
    private ArrayList<Integer> neighbors;


    /*
   * Board constructor triggers the board initialisation method and sets the boards cells' size
   * and delivers graphic content data to the GraphicContext object variable that is used
   * to draw the board and cells by mouseEvents
   */
    public Board(GraphicsContext graphics, int cellSize) {
        this.cellSize = cellSize;
        this.initialize();
        this.graphics = graphics;
    }


    /*
   *
   * Method responsible for initializing the 2d Cell array (Grid).
   * All the individual Cells are called when the Grid(array) is initialized with their position
   * which are triggered by mouseEvents method in controller
   */







    private void initialize() {
        grid = new Cell[colums][rows];

        for (int x = 0; x < this.colums; x++) {
            for (int y = 0; y < this.rows; y++) {
                // initialize all cells (default dead) selve spillbrettet
                grid[x][y] = new Cell(x, y);

                // To do: initialize each cells neighbors...

                // Each cell's coordinates debugging
                //System.out.println(this.grid[x][y].getX()+" "+this.grid[x][y].getY());

                //neighbors = new int[16];

                // Instantiate neigbours at gameboard initalisation ...or in Cell class?
                neighbors=new ArrayList<>();

                // 1.Top-left (0,0)
                neighbors.add(this.grid[x][y].getX()-1);
                neighbors.add(this.grid[x][y].getY()-1);

                // 2.Top (0,1)
                neighbors.add(this.grid[x][y].getX());
                neighbors.add(this.grid[x][y].getY()-1);

                // 3.Top-right (0,2)
                neighbors.add(this.grid[x][y].getX()+1);
                neighbors.add(this.grid[x][y].getY()-1);

                // 4.Left (1,0)
                neighbors.add(this.grid[x][y].getX()-1);
                neighbors.add(this.grid[x][y].getY());

                // 5.Right (1,2)
                neighbors.add(this.grid[x][y].getX()+1);
                neighbors.add(this.grid[x][y].getY());

                // 6.Bottom-left (2,0)
                neighbors.add(this.grid[x][y].getX()-1);
                neighbors.add(this.grid[x][y].getY()+1);

                // 7.Bottom (2,1)
                neighbors.add(this.grid[x][y].getX());
                neighbors.add(this.grid[x][y].getY()+1);

                // 8.Bottom-right (2,2)
                neighbors.add(this.grid[x][y].getX()+1);
                neighbors.add(this.grid[x][y].getY()+1);

                //neighbors[x]=this.grid[x][y].getX();
                //neighbors[y]=this.grid[x][y].getY();
                //System.out.println("x: "+neighbors[x]+" | y: "+neighbors[y]);
                //System.out.println(neighbors.toString());


            }
        }

    }




    // Invoked by the Board constructor which takes Graphic Content as argument
    public void drawCell(Cell cell) {
        //System.out.println(cell.getX());
        //System.out.println(cell.getY());
        if (cell.isAlive()) {
            this.graphics.setFill(color);
        }else {
            this.graphics.setFill(Color.LIGHTGREY);
        }


        this.graphics.setStroke(Color.WHITE); // Sets grid color
        this.graphics.setLineWidth(0.3); // sets the width of the grid line
        this.graphics.fillRect(cell.getX() * this.cellSize, cell.getY() * this.cellSize, this.cellSize, this.cellSize);
        this.graphics.strokeRect(cell.getX() * this.cellSize, cell.getY() * this.cellSize, this.cellSize, this.cellSize);
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
    public void setPickedColor(Color c){
        this.color = c;
    }
    public void setCellSize(int cellsize) {
        this.cellSize = cellsize;
    }

    // Getters
    // getCellCoordinates Method is called by the MouseClick event which asks every Cell in the array of Cell objects for its coordinates
    public Cell getCellCoordinates(int x, int y) {return this.grid[x][y];}
    public Cell[][] getGrid(){return this.grid;}
    public int getCellSize(){return this.cellSize;}


}