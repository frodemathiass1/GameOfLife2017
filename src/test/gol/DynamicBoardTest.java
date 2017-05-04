package test.gol;

import main.gol.model.Cell;
import main.gol.model.boards.DynamicBoard;
import org.junit.Assert;
import org.junit.Test;

/**
 * The testNextGeneration tests makes sure the Game of Life rules works as intended. The test creates a board and
 * runs the nextGeneration method on it. Then it stores the outcome as a string in a "actual"-variable after nextGeneration has chooseAndSelectType.
 * Then we manually figure out what the string should look like after the rules have chooseAndSelectType and store it in a "expected"-variable.
 * When that is done we chooseAndSelectType a assertEquals to make sure the test was successful.
 * @see org.junit.Test
 */
public class DynamicBoardTest {


    final DynamicBoard db = new DynamicBoard(10,10);


    @Test
    public void testSetCellStateTrue() throws Exception {

        //Act
        db.setCellState(15, 15, true);
        Cell expected = db.getCell(15, 15);

        //Assert
        org.junit.Assert.assertEquals(expected.getState(), true);
    }

    @Test
    public void testSetCellStateFalse() throws Exception{
        //Act
        db.setCellState(20, 20, true);
        Cell expected1 = db.getCell(19, 19);

        //Assert
        org.junit.Assert.assertEquals(expected1.getState(), false);
        System.out.println(expected1.getState());
    }


    @Test
    public void testNextGeneration1() {

        //Arrange
        byte[][] testBoard = {
                {0, 0, 0, 0},
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };

        //Act
        db.setBoard(testBoard);
        db.nextGeneration();

        //Assert
        String actual = db.toString();
        String expected = "0000011001100000";
        Assert.assertEquals(actual, expected);

        //Console
        System.out.println("testNextGeneration1");
        System.out.println("Actual:   " + actual);
        System.out.println("Expected: " + expected + "\n");


    }

    @Test
    public void testNextGeneration2() {

        //Arrange
        byte[][] testBoard = {
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };

        //Act
        db.setBoard(testBoard);
        db.nextGeneration();

        //Assert
        String actual = db.toString();
        String expected = "0000011001100000";
        Assert.assertEquals(actual, expected);

        //Console
        System.out.println("testNextGeneration2");
        System.out.println("Actual:   " + actual);
        System.out.println("Expected: " + expected + "\n");
    }

    @Test
    public void testNextGeneration3() {

        //Arrange
        byte[][] testBoardGlider = {
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0}
        };

        //Act
        db.setBoard(testBoardGlider);
        db.nextGeneration();

        //Assert
        String actual = db.toString();
        String expected = "0000101001100100";
        Assert.assertEquals(expected, actual);

        //Console
        System.out.println("testNextGeneration3");
        System.out.println("Actual:   " + actual);
        System.out.println("Expected: " + expected + "\n");
    }

    @Test
    public void testNextGeneration4() {

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
        db.setBoard(testBoardGlider);
        db.nextGeneration();

        // Assert
        String expected = "0000000000101000000001100000000100000000000000000000000000000000000000000000000000000000000000000000";
        String actual = db.toString();
        Assert.assertEquals(expected, actual);


        // Console
        System.out.println("testNextGeneration4");
        System.out.println("Actual:   " + actual);
        System.out.println("Expected: " + expected + "\n");
    }

    /**
     * The testNextGenerationFail tests if the rules doesn't work. It makes sure the unexpected value (the value that
     * indicates that the rule doesn't work) and the actual result doesn't match. If the rules doesn't work the tests
     * will fail.
     */


    @Test
    public void testNextGenerationFail1() {

        //Arrange
        byte[][] testBoard = {
                {0, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 1},
        };

        //Act
        db.setBoard(testBoard);
        db.nextGeneration();

        //Assert
        String actual = db.toString();
        String unexpected = "0000001001100001";
        //actual = 0000011001110010
        Assert.assertNotEquals(unexpected, actual);

        //Console
        System.out.println("testNextGenerationFail1");
        System.out.println("Actual:   " + actual);
        System.out.println("Expected: " + unexpected + "\n");

    }

    @Test
    public void testNextGenerationFail2() {

        //Arrange
        byte[][] testBoard = {
                {0, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 1},
        };

        //Act
        db.setBoard(testBoard);
        db.nextGeneration();

        //Assert
        String actual = db.toString();
        String unexpected = "0000000000000000";
        Assert.assertNotEquals(unexpected, actual);

        //Console
        System.out.println("testNextGenerationFail2");
        System.out.println("Actual:   " + actual);
        System.out.println("Expected: " + unexpected + "\n");
    }

    @Test
    public void testNextGenerationFail3() {

        //Arrange
        byte[][] testBoard = {
                {0, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 1},
        };

        //Act
        db.setBoard(testBoard);
        db.nextGeneration();

        //Assert
        String actual = db.toString();
        String unexpected = "1111100110001101";
        //actual = 0000011001110010
        Assert.assertNotEquals(unexpected, actual);

        //Console
        System.out.println("testNextGenerationFail3");
        System.out.println("Actual:   " + actual);
        System.out.println("Expected: " + unexpected + "\n");
    }

    /**
     * The testCountNeighbours tests finds out how many alive neighbours one cell has. It verifies that the countNeighbours
     * method works as intended.
     */
    @Test
    public void testCountNeighbours()  {

        //Arrange
        byte[][] testBoard = {
                {1, 1, 1, 0},
                {1, 1, 1, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0},
        };

        //Act
        db.setBoard(testBoard);
        int actual =  db.countNeighbours(1, 1);
        int expected = 8;

        //Assert
        Assert.assertEquals(expected, actual);
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
        db.setBoard(testBoard);
        int actual =  db.countNeighbours(0, 1);
        int expected = 1;

        //Assert
        Assert.assertEquals(expected, actual);
        System.out.println("Expected: "+expected+" Actual: " + actual);
    }

    /**
     * HVA SKAL JEG SKRIVE HER?
     * FORSLAG: "testGetCell tests if the getCell method returns a cell which is alive."
     */

    //FORSLAG: Skal jeg endre getCell-testene til testGetCellAlive og testGetCellDead?
    @Test
    public void testGetCell(){

        //Arrange
        byte[][] testBoard = {
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 0},
        };

        //Act
        db.setBoard(testBoard);
        Cell actual = db.getCell(1, 1);

        //Assert
        Assert.assertTrue(actual.getState());
        System.out.println(actual.getState());

    }

    @Test
    public void testGetCellFalse(){

        //Arrange
        byte[][] testBoard = {
                {1, 1, 0, 0},
                {1, 0, 0, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 0},
        };

        //Act
        db.setBoard(testBoard);
        Cell actual = db.getCell(1, 1);


        //Assert
        Assert.assertFalse(actual.getState());
        System.out.println(actual.getState());

    }

    // Not entirely sure what this asserition is supposed to do....it doesnt do anything ?
    @Test(expected = NullPointerException.class)
    public void testGetCellOutOfBounds1()throws NullPointerException{

        //Arrange
        byte[][] testBoard = {
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1}
        };

        //Act
        db.setBoard(testBoard);
        Cell actual = db.getCell(4, 4);

        //Assert
        Assert.assertTrue(actual.getState());
        System.out.println(actual.getState());
    }

    // Test for nullPointer exceptions when cell does not exist
    @Test (expected = NullPointerException.class)
    public void testGetCellOutOfBounds2() throws NullPointerException{

        //Arrange
        byte[][] testBoard = {
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1}
        };

        //Act
        db.setBoard(testBoard);
        Cell actual = db.getCell(4, 4);

        //Assert
        Assert.assertFalse(actual.getState());
        System.out.println(actual.getState());
    }
}