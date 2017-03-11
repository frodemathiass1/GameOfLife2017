package test.gol;

import main.gol.Cell;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;


/**
 * Created by frodemathiassen on 11.03.2017.
 */
class CellTest {

    private Cell cell = new Cell(0,0);

    @Test
    void toggleIsAliveTest() {
        Assert.assertFalse(cell.isAlive());
        cell.setAlive(true);
        Assert.assertTrue(cell.isAlive());
    }

}