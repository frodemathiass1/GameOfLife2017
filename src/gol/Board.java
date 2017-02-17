package gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import static gol.GUIController.SIZE;


public class Board {

    private GraphicsContext gc;

    public Board(GraphicsContext gc){
        this.gc=gc;
    }

    private byte[][] board = {
            { 1, 0, 0, 1 },
            { 0, 1, 1, 0 },
            { 0, 1, 1, 0 },
            { 1, 0, 0, 1 }
    };

    public byte[][] getBoard(){
        return this.board;
    }

    // Sets board and lays out alive cells on canvas
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
