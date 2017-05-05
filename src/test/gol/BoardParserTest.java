package test.gol;

import main.gol.model.boards.DynamicBoard;
import main.gol.model.filemanager.BoardParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class BoardParserTest {

    /**
     *  This test checks that the boardParser method works as intended. The method is supposed to parse the
     *  URL. The test checks if it gets the right amount of alive cells according to the pattern that is loaded.
     * @throws Exception if an exception occurred
     */



    @Test
    public void testBoardParser() throws Exception {
        BoardParser boardParser = new BoardParser();
        boardParser.readAndParseURL("http://www.conwaylife.com/patterns/beaconmaker.cells");
        DynamicBoard dynamicBoard = new DynamicBoard(32, 32);
        dynamicBoard.setBoard(boardParser.getTheBoard());

        // Assert
        assertEquals(19, dynamicBoard.getAliveCells());
    }
}
