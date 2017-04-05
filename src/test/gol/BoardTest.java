package test.gol;

import javafx.scene.canvas.GraphicsContext;
//import main.gol.GameOfLife;
import main.gol.model.Boards.FixedBoard;
import org.junit.jupiter.api.Test;


class BoardTest {


    private GraphicsContext gc;


    FixedBoard board = new FixedBoard(gc, 5);

    @Test
    public void testNextGeneration1() {

        //Arrange
        byte[][] testBoard1 = {
                { 0, 0, 0, 0 },
                { 0, 1, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };

        //Act
        board.setBoard(testBoard1);
        board.nextGeneration();

        //Assert
        String actual = board.toString();
        String expected = "0000011001100000";
        org.junit.Assert.assertEquals(actual,expected);

        //Console
        System.out.println("testNextGeneration1");
        System.out.println("Actual:   "+actual);
        System.out.println("Expected: "+expected+"\n");


    }

    @Test
    public void testNextGeneration2() {

        //Arrange
        byte[][] testBoard2 = {
                { 0, 0, 0, 0 },
                { 0, 1, 0, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };

        //Act
        board.setBoard(testBoard2);
        board.nextGeneration();

        //Assert
        String actual = board.toString();
        String expected = "0000011001100000";
        org.junit.Assert.assertEquals(actual,expected);

        //Console
        System.out.println("testNextGeneration2");
        System.out.println("Actual:   "+actual);
        System.out.println("Expected: "+expected+"\n");
    }

    @Test
    public void testNextGeneration3() {

        //Arrange
        byte[][] testBoard3 = {
                { 0, 1, 0, 0 },
                { 0, 0, 1, 0 },
                { 1, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };

        //Act
        board.setBoard(testBoard3);
        board.nextGeneration();

        //Assert
        String actual = board.toString();
        String expected = "0100001101100000";
        org.junit.Assert.assertEquals(actual,expected);

        //Console
        System.out.println("testNextGeneration3");
        System.out.println("Actual:   "+actual);
        System.out.println("Expected: "+expected+"\n");
    }

    @Test
    public void testNextGeneration4() {

        // Arrange
        byte[][] testBoard4 =  {
                { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };

        // Act
        board.setBoard(testBoard4);
        board.nextGeneration();

        // Assert
        String expected = "0100000000001100000001100000000000000000000000000000000000000000000000000000000000000000000000000000";
        String actual = board.toString();
        org.junit.Assert.assertEquals(actual,expected);

        // Console
        System.out.println("testNextGeneration4");
        System.out.println("Actual:   "+actual);
        System.out.println("Expected: "+expected+"\n");
    }

    //"testNextGenerationFail" tester om reglene ikke funker.Hvis reglene ikke funker vil testene faile.
    @Test
    public void testNextGenerationFail1() {

        //Arrange
        byte[][] testBoard4 = {
                { 0, 0, 0, 0 },
                { 0, 0, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 1 },
        };

        //Act
        board.setBoard(testBoard4);
        board.nextGeneration();

        //Assert
        String actual = board.toString();
        String unexpected = "0000001001100001";
        //actual = 0000011001110010
        org.junit.Assert.assertNotEquals(unexpected, actual);

        //Console
        System.out.println("testNextGenerationFail1");
        System.out.println("Actual:   "+actual);
        System.out.println("Expected: "+unexpected+"\n");

    }

    @Test
    public void testNextGenerationFail2() {

        //Arrange
        byte[][] testBoard5 = {
                { 0, 0, 0, 0 },
                { 0, 0, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 1 },
        };

        //Act
        board.setBoard(testBoard5);
        board.nextGeneration();

        //Assert
        String actual = board.toString();
        String unexpected = "0000000000000000";
        //actual = 0000011001110010
        org.junit.Assert.assertNotEquals(unexpected, actual);

        //Console
        System.out.println("testNextGenerationFail2");
        System.out.println("Actual:   "+actual);
        System.out.println("Expected: "+unexpected+"\n");
    }

    @Test
    public void testNextGenerationFail3() {

        //Arrange
        byte[][] testBoard5 = {
                { 0, 0, 0, 0 },
                { 0, 0, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 1 },
        };

        //Act
        board.setBoard(testBoard5);
        board.nextGeneration();

        //Assert
        String actual = board.toString();
        String unexpected = "1111100110001101";
        //actual = 0000011001110010
        org.junit.Assert.assertNotEquals(unexpected, actual);

        //Console
        System.out.println("testNextGenerationFail3");
        System.out.println("Actual:   "+actual);
        System.out.println("Expected: "+unexpected+"\n");
    }
}