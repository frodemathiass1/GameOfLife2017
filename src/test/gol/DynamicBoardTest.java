package test.gol;

import main.gol.model.boards.DynamicBoard;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by frodemathiassen on 24.04.2017.
 */
public class DynamicBoardTest {


    DynamicBoard db = new DynamicBoard(10,10);


    @Test
    public void setCellState() throws Exception {

    }

    @Test
    public void testNextGeneration1() {

        //Arrange
        byte[][] testBoard1 = {
                {0, 0, 0, 0},
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };

        //Act
        db.setGrid(testBoard1);
        db.nextGeneration();

        //Assert
        String actual = db.toString();
        String expected = "0000011001100000";
        org.junit.Assert.assertEquals(actual, expected);

        //Console
        System.out.println("testNextGeneration1");
        System.out.println("Actual:   " + actual);
        System.out.println("Expected: " + expected + "\n");


    }

    @Test
    public void testCountNeighbours()  {

        //Arrange
        byte[][] testBoard6 = {
                {1, 1, 1, 0},
                {1, 1, 1, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0},
        };

        //Act
        db.setGrid(testBoard6);
        int actual =  db.countNeighbours(1, 1);
        int expected = 8;

        //Assert
        org.junit.Assert.assertEquals(expected, actual);
        System.out.println("Expected: "+expected+" Actual: " + actual);
    }

    @Test
    public void testCountNeighbours2()  {

        //Arrange
        byte[][] testBoard = {
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        //Act
        db.setGrid(testBoard);
        int actual =  db.countNeighbours(0, 1);
        int expected = 1;

        //Assert
        org.junit.Assert.assertEquals(expected, actual);
        System.out.println("Expected: "+expected+" Actual: " + actual);
    }
}