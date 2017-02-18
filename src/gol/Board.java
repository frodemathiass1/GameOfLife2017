package gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
//import static gol.Cell.SIZE;


public class Board{

    private Cell cSize;                                                       //private static final int NUMBER_OF_CELLS = 16;
    private GraphicsContext gc;
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

                System.out.print(board[i][j]);
                if(board[i][j]==1){
                    gc.setFill(Color.WHITE);
                    gc.fillRect(i * cSize.getSize(),j * cSize.getSize(), cSize.getSize(), cSize.getSize());
                } else{
                    gc.setFill(Color.BLACK);
                    gc.fillRect(i * cSize.getSize(),j * cSize.getSize(), cSize.getSize(), cSize.getSize());
                }
            }

            System.out.println();
        }
    }

}
