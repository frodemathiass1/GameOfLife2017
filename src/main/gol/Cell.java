package main.gol;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class Cell{


    private int x, y;
    private boolean alive=false;
    private List<Cell> neighbors;


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }



    public List<Cell> getNeighbors(){
        return this.neighbors;
    }



    public int countAliveNeighbors(){
        int neighborCount=0;
        for(int i = 0; i < this.getNeighbors().size(); i++ )
            if(this.neighbors.get(i).isAlive())
                neighborCount++;
        return neighborCount;
    }




    // List of neighbours
    public void updateNeighbors(Board board) {
        Cell topLeft = board.getCell(this.x-1, this.y - 1);
        Cell top = board.getCell(this.x, this.y - 1);
        Cell topRight = board.getCell(this.x + 1, this.y - 1);
        Cell left = board.getCell(this.x - 1, this.y);
        Cell right = board.getCell(this.x + 1, this.y);
        Cell bottomLeft = board.getCell(this.x - 1, this.y + 1);
        Cell bottom = board.getCell(this.x, this.y + 1);
        Cell bottomRight = board.getCell(this.x + 1, this.y + 1);

        // Makes a stream of adjacent cells and filters out null cells(not on the map), then it collects it in a list.
        this.neighbors = Stream.of(topLeft, top, topRight, left, right, bottomLeft, bottom, bottomRight)
                .filter(Objects::nonNull) // Filter out non-existent neighbors
                .collect(Collectors.toList());
    }




    public void setAlive(boolean alive){
        this.alive=alive;
    }

    public boolean isAlive(){return this.alive;}

    public int getX(){return this.x;}

    public int getY(){return this.y;}

    @Override
    public String toString() {
        return "Cell: " + "alive: " + alive + ", X=" + x + ", Y=" + y;
    }
}
