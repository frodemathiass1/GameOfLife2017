package gol;


public class Cell{

    // Dead at initalisation
    private boolean alive = false;
    private int posX, posY;
    private Cell[] neibours;

    // Cell Constructor
    public Cell(int x, int y) {
        this.posX = x;
        this.posY = y;
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
    public int getX(){return this.posX;}
    public int getY(){return this.posY;}

}
