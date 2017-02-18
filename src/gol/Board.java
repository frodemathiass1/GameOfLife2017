package gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import static gol.MainController.SIZE;


public class Board {

    public static final int NUMBER_OF_CELLS = 16;

    private GraphicsContext gc;

    private byte[][] cells = {
            { 1, 0, 0, 1 },
            { 0, 1, 1, 0 },
            { 0, 1, 1, 0 },
            { 1, 0, 0, 1 }};

    // private Cell[][] cellObjects;


    // Board Constructor
    public Board(GraphicsContext gc){
        this.gc=gc;

    }



    // Get Board Method
    public byte[][] getBoard(){
        return this.cells;
    }




    // Sets board and lays out  cells on canvas
    public void setBoard(byte[][] board){
        for(int i=0; i <board.length; i++ ){
            for(int j = 0; j < board[0].length; j++ ){

                System.out.print(board[i][j]);
                if(board[i][j]==1){
                    gc.setFill(Color.GREEN);
                    gc.fillRect(i*SIZE,j*SIZE, SIZE, SIZE);
                }
                else{
                    gc.setFill(Color.BLUE);
                    gc.fillRect(i*SIZE,j*SIZE, SIZE, SIZE);
                }
            }
            System.out.println();
        }
    }
}
