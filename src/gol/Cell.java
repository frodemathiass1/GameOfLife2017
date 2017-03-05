package gol;


import java.util.ArrayList;

public class Cell{

    private boolean alive = false;
    private int x, y;
    //private Cell[] neibours;
    private ArrayList<Integer> neighbors;
    public int neighborCount = 0; // test

    // Constructor is invoked the Draw cell method in the controller
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;

        // set neighbours at cell instantiation ???
        //setNeigbours(x, y);
        //System.out.println(this.getNeighbors());
    }

    public String getNeighbors(){
        return this.neighbors.toString();
    }
    // List of neighbours
    public void setNeigbours(int x, int y) {

        neighbors=new ArrayList<>();

        // 1.Top-left (0,0)
        neighbors.add(this.x-1);
        neighbors.add(this.y-1);

        // 2.Top (0,1)
        neighbors.add(this.x);
        neighbors.add(this.y-1);

        // 3.Top-right (0,2)
        neighbors.add(this.x+1);
        neighbors.add(this.y-1);

        // 4.Left (1,0)
        neighbors.add(this.x-1);
        neighbors.add(this.y);

        // 5.Right (1,2)
        neighbors.add(this.x+1);
        neighbors.add(this.y);

        // 6.Bottom-left (2,0)
        neighbors.add(this.x-1);
        neighbors.add(this.y+1);

        // 7.Bottom (2,1)
        neighbors.add(this.x);
        neighbors.add(this.y+1);

        // 8.Bottom-right (2,2)
        neighbors.add(this.x+1);
        neighbors.add(this.y+1);

        neighborCount+=8;

    }


    public void setAlive(boolean alive){

        this.alive=alive;
    }

    // Getters
    public boolean isAlive(){return this.alive;}
    public int getX(){return this.x;}
    public int getY(){return this.y;}
}
