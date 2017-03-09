package gol;


import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cell{

    private boolean alive = false;
    private int x, y;
    private List<Cell> neighbors;



    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        // initialize neighbours at cell instantiation or in board class???
        //setNeigbours(x, y);
        //System.out.println(this.getNeighbors());
    }

    public List<Cell> getNeighbors(){
        return this.neighbors;
    }
    // List of neighbours
    public void updateNeighbors(Board board) {
        // 1.Top-left (0,0)
        Cell topLeft = board.getCellCoordinates(this.x-1, this.y - 1);

        // 2.Top (0,1)
        Cell top = board.getCellCoordinates(this.x, this.y - 1);

        // 3.Top-right (0,2)
        Cell topRight = board.getCellCoordinates(this.x + 1, this.y - 1);

        // 4.Left (1,0)
        Cell left = board.getCellCoordinates(this.x - 1, this.y);

        // 5.Right (1,2)
        Cell right = board.getCellCoordinates(this.x + 1, this.y);

        // 6.Bottom-left (2,0)
        Cell bottomLeft = board.getCellCoordinates(this.x - 1, this.y + 1);

        // 7.Bottom (2,1)
        Cell bottom = board.getCellCoordinates(this.x, this.y + 1);

        // 8.Bottom-right (2,2)
        Cell bottomRight = board.getCellCoordinates(this.x + 1, this.y + 1);

        // Makes a stream of adjacent cells and filters out null cells(not on the map), then it collects it in a list.
        neighbors = Stream.of(topLeft, top, topRight, left, right, bottomLeft, bottom, bottomRight)
                .filter(Objects::nonNull) // Filter out non-existent neighbors
                .collect(Collectors.toList());
    }

    // Setters
    public void setAlive(boolean alive){
        this.alive=alive;
    }

    // Getters
    public boolean isAlive(){return this.alive;}
    public int getX(){return this.x;}
    public int getY(){return this.y;}

    @Override
    public String toString() {
        return "Cell{" +
                "alive=" + alive +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
