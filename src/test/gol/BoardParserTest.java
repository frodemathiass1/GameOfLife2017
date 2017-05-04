package test.gol;

import main.gol.model.boards.DynamicBoard;
import main.gol.model.filemanager.BoardParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class BoardParserTest {
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
