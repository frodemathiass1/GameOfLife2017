package gol;


public class Board {


    private int numOfRows;
    private int numOfCols;
    private byte board[][];
    private Cell[][] cells;
    private int aliveCells;
    private int deadCells;

    public int getNumOfRows(){

        return this.numOfRows;
    }

    public int getNumOfCols(){

        return this.numOfCols;
    }

    public void setBoard(){

    }
    public void countCells(){

        aliveCells=0;
        deadCells=0;
    }


}
