package gol;


public class Cell{

    private boolean alive = false;
    private int x, y;
    private Cell[] neibours;

    // Constructor is invoked the Draw cell method in the controller
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // List of neighbours
    public void setNeigbours(Cell[] cells) {

        this.neibours = cells;
    }

    public void setAlive(boolean alive){

        this.alive=alive;
    }

    // Getters
    public boolean isAlive(){return this.alive;}
    public int getX(){return this.x;}
    public int getY(){return this.y;}
}
