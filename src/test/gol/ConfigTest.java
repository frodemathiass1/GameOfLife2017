package test.gol;

import main.gol.model.boards.Config;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTest {

    Config config;

    /**
     * This test makes sure the setDefault-method sets the default board configuration correctly.
     * @throws Exception if exception occurred.
     */
    @Test
    public void testSetDefault() throws Exception {

        // Arrange
        config = new Config();

        // Act
        config.setDefault();

        // Assert
        assertEquals(160, config.getColumns());
        assertEquals(104, config.getRows());
        assertEquals(5, config.getCellSize());
    }

    /**
     * This test makes sure the setColumns and getColumns methods work as intended. setColumns is supposed to set
     * an amount of columns and getColumns is supposed to return the amount of set columns.
     */
    @Test
    public void testColumns(){

        // Arrange
        config = new Config();

        // Act
        config.setColumns(100);

        // Assert
        assertEquals(100, config.getColumns());
    }

    /**
     * This test makes sure the setRows and getRows methods work as intended. setRows is supposed to set
     * an amount of rows and getRows is supposed to return the amount of set rows.
     */
    @Test
    public void testRows(){

        // Arrange
        config = new Config();

        // Act
        config.setRows(50);

        // Assert
        assertEquals(50, config.getRows());
    }

    /**
     * This test makes sure the setCellSize and getCellSize methods work as intended. setCellSize is supposed to set
     * the cell size and getCellSize is supposed to return the cell size.
     */
    @Test
    public void testCellSize(){

        // Arrange
        config = new Config();

        // Act
        config.setCellSize(25);

        // Assert
        assertEquals(25,config.getCellSize());
    }
}