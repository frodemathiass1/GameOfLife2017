package test.gol;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
//import main.gol.GameOfLife;
import main.gol.model.Board;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;


class BoardTest {


    @FXML private GraphicsContext gc;
    @FXML public Canvas canvas;

    Board board = new Board(gc, 5);

    @Test
    public void testNextGeneration2() {
        byte[][] testBoard2 = {
                { 0, 0, 0, 0 },
                { 0, 1, 0, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };
        board.setBoard(testBoard2);
        board.nextGeneration();
        String actual = board.toString();
        String expected = "0000011000100000";
        org.junit.Assert.assertEquals(actual,expected);
    }

    @Test
    public void testNextGeneration1() {
        byte[][] testBoard1 = {
                { 0, 0, 0, 0 },
                { 0, 1, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };
        board.setBoard(testBoard1);
        board.nextGeneration();
        String actual = board.toString();
        String expected = "0000011001100000";
        org.junit.Assert.assertEquals(actual,expected);
    }



}