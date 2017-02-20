package gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board {





    // Hardcoded array
    private byte[][] cells2 = {
            { 1, 0, 1, 0, 1, 1, 1, 0, 1},
            { 1, 0, 1, 0, 1, 0, 0, 0, 1},
            { 1, 1, 1, 0, 1, 1, 0, 0, 1},
            { 1, 0, 1, 0, 1, 0, 0, 0, 1},
            { 1, 0, 1, 0, 1, 1, 1, 0, 1},

    };
    private GraphicsContext gc;


    // Byte Array
    private byte[][] cells;
    private int row=40;
    private int col=40;
    private int cellSize=15;
    public int getCellSize(){return this.cellSize;}
    public void setCellSize(int cS){this.cellSize=cS;}

    public void populateCells(){
        cells =new byte[row][col];
            for(int i=0; i<cells2.length; i++){
                for(int j=0; j<cells2[i].length; j++){
                        cells[i][j]=cells2[i][j];
                }
            }
    }

    // Board Constructor
    public Board(GraphicsContext g){
        populateCells();
        //popCells();
        this.gc=g;
        setBoard(getCells());

    }

    // Getters
    public byte[][] getCells(){return this.cells;}

                                                            //public int getNumberOfCells(){return NUMBER_OF_CELLS;}
    // Sets board and lays out  cells on canvas
    public void setBoard(byte[][] board){

        for(int i=0; i <board.length; i++ ){
            for(int j = 0; j < board[i].length; j++ ){

                //System.out.print(board[i][j]);
                if(board[i][j]==1){
                    gc.setFill(Color.BLACK);
                    gc.fillRect(i * cellSize,j * cellSize, cellSize, cellSize);
                } else{
                    gc.setFill(Color.LIGHTGREY);
                    gc.fillRect(i * cellSize,j * cellSize, cellSize, cellSize);
                }
            }
            System.out.println();
        }
    }



    /*
    //Alternativ setBoard
    public void setBoard(){
        setCellz();;

        Cell c=new Cell();
        for(int i=0; i <this.cellz.length; i++ ){
            for(int j = 0; j < this.cellz[i].length; j++ ){

                System.out.print(this.cellz[i][j]);
                if(this.cellz[i][j].isAlive()){
                    gc.setFill(Color.BLACK);
                    gc.fillRect(i * c.getSize(),j * c.getSize(), c.getSize(), c.getSize());
                } else{
                    gc.setFill(Color.LIGHTGREY);
                    gc.fillRect(i * c.getSize(),j * c.getSize(), c.getSize(), c.getSize());
                }
            }
            System.out.println();

        }

    }
    */



}