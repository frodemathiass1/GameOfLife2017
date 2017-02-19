package gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class Board{


    private Cell cSize;                                                       //private static final int NUMBER_OF_CELLS = 16;
    private GraphicsContext gc;

    // Array pattern : HEI
    private byte[][] cells = {
            { 1, 0, 1, 0, 1, 1, 1, 0, 1},
            { 1, 0, 1, 0, 1, 0, 0, 0, 1},
            { 1, 1, 1, 0, 1, 1, 0, 0, 1},
            { 1, 0, 1, 0, 1, 0, 0, 0, 1},
            { 1, 0, 1, 0, 1, 1, 1, 0, 1},

    };



    // Board Constructor
    public Board(GraphicsContext g){
        this.gc=g;
        setBoard(getCells());
    }

    // Getters
    public byte[][] getCells(){return this.cells;}

                                                            //public int getNumberOfCells(){return NUMBER_OF_CELLS;}
    // Sets board and lays out  cells on canvas
    public void setBoard(byte[][] board){

        cSize=new Cell();
        for(int i=0; i <board.length; i++ ){
            for(int j = 0; j < board[0].length; j++ ){

                System.out.println(board[i][j]);
                if(board[i][j]==1){
                    gc.setFill(Color.BLACK);
                    gc.fillRect(i * cSize.getSize(),j * cSize.getSize(), cSize.getSize(), cSize.getSize());
                } else{
                    gc.setFill(Color.LIGHTGREY);
                    gc.fillRect(i * cSize.getSize(),j * cSize.getSize(), cSize.getSize(), cSize.getSize());
                }

            }
            System.out.println();
        }

    }
}
