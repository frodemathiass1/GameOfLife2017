package test.gol;

import main.gol.Cell;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class CellTest {

    private final Cell cell = new Cell(0,0);

    @Test
    void toggleStateTest() {
        Assert.assertFalse(cell.getState());
        cell.setState(true);
        Assert.assertTrue(cell.getState());

    }

}