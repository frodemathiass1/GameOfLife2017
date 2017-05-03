package test.gol;

import main.gol.model.Cell;
import main.gol.model.boards.DynamicBoard;
import org.junit.Assert;
import org.junit.Test;

public class CellTest {


    @Test
    public void toggleStateTest(){


        final Cell cell = new Cell(0, 0);
        System.out.println(cell.getState());

        Assert.assertFalse(cell.getState());
        cell.setState(true);

        Assert.assertTrue(cell.getState());
        System.out.println(cell.getState());
    }

    @Test
    public void testInitNeighbors() throws Exception {

        //Arrange
        final Cell cell = new Cell(3, 3);

        //Act
        DynamicBoard db = new DynamicBoard(10,10);
        cell.initNeighbors(db);
        int expectedAmount = 8;
        int actualAmount = cell.countDeadNeighbors();
        String expectedNeighbors = "[false , false , false , false , false , false , false , false ]";
        String actualNeighbors = cell.getNeighbors().toString();

        //Assert
        Assert.assertEquals(expectedNeighbors, actualNeighbors);
        System.out.println("Expected neighbors state: " + expectedNeighbors + " Actual neighbors state: " + actualNeighbors);
        Assert.assertEquals(expectedAmount, actualAmount);
        System.out.println("Expected amount of neighbors: " + expectedAmount + " Actual amount of neighbors: " + actualAmount);
    }

    @Test
    public void testInitNeighbors2(){

        //Arrange
        final Cell cell3 = new Cell(0, 0);

        //Act
        DynamicBoard db = new DynamicBoard(10,10);
        cell3.initNeighbors(db);
        int expectedAmount = 3;
        int actualAmount = cell3.countDeadNeighbors();

        //Assert
        Assert.assertEquals(expectedAmount, actualAmount);
        System.out.println("Expected amount of neighbors: " + expectedAmount + " Actual amount of neighbors: " + actualAmount);
    }


    @Test
    public void testGetX() throws Exception {

        //Arrange
        final Cell cell3 = new Cell(4, 3);

        //Act
        int actual = cell3.getX();
        int expected = 4;

        //Assert
        Assert.assertEquals(expected, actual);
        System.out.println("Expected: " + expected + " Actual: " + actual);

    }

    @Test
    public void testGetY() throws Exception {

        //Arrange
        final Cell cell3 = new Cell(3, 3);

        //Act
        int actual = cell3.getY();
        int expected = 3;

        //Assert
        Assert.assertEquals(expected, actual);
        System.out.println("Expected: " + expected + " Actual: " + actual);
    }
}