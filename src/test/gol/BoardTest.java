
package test.gol;


import javafx.scene.canvas.GraphicsContext;
import main.gol.model.Board;
import org.junit.jupiter.api.Test;




class BoardTest {


/*
    private GraphicsContext gcTest;
    Board board = new Board(gcTest, 5);

    @Test
    // Next Generation equals previous generation when called (no pattern change)
    public void testNextGeneration1() throws Exception  {

        // Arrange
        byte[][] testBoard1 = {
                { 0, 0, 0, 0 },
                { 0, 1, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };

        // Act
        board.setBoard(testBoard1);
        board.nextGeneration();

        // Assert
        String expected = "0000011001100000";
        String actual = board.toString();
        org.junit.Assert.assertEquals(actual,expected);

        // Console
        System.out.println("Expected: " +expected);
        System.out.println("Actual:   " +actual);
    }


    @Test
    public void testNextGeneration2() throws Exception {

        // Arrange
        byte[][] testBoard2 = {
                { 0, 0, 0, 0 },
                { 0, 1, 0, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };

        // Act
        board.setBoard(testBoard2);
        board.nextGeneration();

        // Assert
        String expected = "0000011001100000";
        String actual = board.toString();
        org.junit.Assert.assertEquals(actual,expected);

        // Console
        System.out.println("Expected: " +expected);
        System.out.println("Actual:   " +actual);

    }

    // Glider pattern
    @Test
    public void testNextGeneration3() throws Exception {

        // Arrange
        byte[][] testBoard3 = {
                { 0, 1, 0, 0 },
                { 0, 0, 1, 0 },
                { 1, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };

        // Act
        board.setBoard(testBoard3);
        board.nextGeneration();

        // Assert
        String expected = "0100001101100000";
        String actual = board.toString();
        org.junit.Assert.assertEquals(actual,expected);

        // Console
        System.out.println("Expected: " +expected);
        System.out.println("Actual:   " +actual);
    }

    @Test
    public void testNextGeneration4() throws Exception {

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
        System.out.println("Expected: " +expected);
        System.out.println("Actual:   " +actual);
    }



    @Test
    public void testNextGenerationFail1() {

        // Arrange
        byte[][] testBoard5 = {
                { 0, 0, 0, 0 },
                { 0, 0, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 1 },
        };

        // Act
        board.setBoard(testBoard5);
        board.nextGeneration();

        // Assert
        String actual = board.toString();
        String unexpected = "0000001001100001";
        org.junit.Assert.assertNotEquals(unexpected, actual);
        //actual = 0000011001110010

        // Console
        System.out.println("Actual:   "+actual);
        System.out.println("Unexpected: "+unexpected);
    }


    // Glider
    @Test
    public void testNextGenerationFail2() throws Exception  {

        // Arrange
        byte[][] testBoard6 = {
                { 0, 1, 0, 0 },
                { 0, 0, 1, 0 },
                { 1, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };

        // Act
        board.setBoard(testBoard6);
        board.nextGeneration();
        board.nextGeneration();

        // Assert
        String expected = "0100001101100000";
        String actual = board.toString();
        org.junit.Assert.assertEquals(actual,expected);

        // Console
        System.out.println("Expected: " +expected);
        System.out.println("Actual:   " +actual);
    }

*/
}
