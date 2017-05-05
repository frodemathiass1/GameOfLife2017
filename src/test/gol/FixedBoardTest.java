package test.gol;

import main.gol.model.boards.FixedBoard;
import org.junit.Assert;
import org.junit.Test;


/**
 * This test class tests if the nextGeneration algorithm works as intended.
 * Returns a string of boolean values for hardcoded gameBoards and assert if the algorithm method
 * returns the correct set of values.
 * @see org.junit.Test
 */
public class FixedBoardTest {


    private final FixedBoard fb = new FixedBoard(10, 10);

    @Test
    public void testNextGeneration(){

        // Arrange
        byte[][] testBoardGlider = {
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Act
        fb.setBoard(testBoardGlider);
        fb.nextGeneration();

        // Assert
        String expected = "0100000000001100000001100000000000000000000000000000000000000000000000000000000000000000000000000000";
        String actual = fb.toString();
        //assertEquals(actual, expected);
        Assert.assertEquals(actual, expected);

        // Console
        System.out.println("testNextGeneration4");
        System.out.println("Actual:   " + actual);
        System.out.println("Expected: " + expected + "\n");
    }

    @Test
    public void testNextGenerationFail(){

        //Arrange
        byte[][] testBoard = {
                {0, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 1},
        };

        //Act
        fb.setBoard(testBoard);
        fb.nextGeneration();

        //Assert
        String actual = fb.toString();
        String unexpected = "0000001001100001";
        //actual = 0000011001110010
        Assert.assertNotEquals(unexpected, actual);

        //Console
        System.out.println("testNextGenerationFail1");
        System.out.println("Actual:   " + actual);
        System.out.println("Expected: " + unexpected + "\n");
    }


    @Test
    public void testCountNeighbors(){

        //Arrange
        byte[][] testBoard = {
                {1, 1, 1, 0},
                {1, 1, 1, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0},
        };

        //Act
        fb.setBoard(testBoard);
        int actual =  fb.countNeighbours(1, 1);
        int expected = 8;

        //Assert
        Assert.assertEquals(expected, actual);
        System.out.println("Expected: "+expected+" Actual: " + actual);
    }

    @Test
    public void testCountNeighbors2(){
        //Arrange
        byte[][] testBoard = {
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        //Act
        fb.setBoard(testBoard);
        int actual =  fb.countNeighbours(0, 1);
        int expected = 1;

        //Assert
        Assert.assertEquals(expected, actual);
        System.out.println("Expected: "+expected+" Actual: " + actual);
    }

}