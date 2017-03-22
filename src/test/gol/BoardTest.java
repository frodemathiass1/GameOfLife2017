package test.gol;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import main.gol.GameOfLife;
import main.gol.model.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;




class BoardTest {

    /// fuckkings dritt...

    @Test
    public void testNextGeneration() {
        byte[][] board = {
                { 0, 0, 0, 0 },
                { 0, 1, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };

        //GameOfLife gol = new GameOfLife();
        //gol.setBoard(board);
        //gol.nextGeneration();
        //org.junit.Assert.assertEquals(gol.toString(),"0000011001100000");
    }

    /**
     *  bare for å teste
     */
    public GraphicsContext gc;

    @Test
    public void pingTest(){
        Board board = new Board(gc,10);
        String result = board.ping();
        System.out.println(result);
    }
}