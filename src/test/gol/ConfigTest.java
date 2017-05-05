package test.gol;

import main.gol.model.boards.Config;
import org.junit.Test;

import static org.junit.Assert.*;


public class ConfigTest {

    Config config;

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

    @Test
    public void testColumns(){

        // Arrange
        config = new Config();

        // Act
        config.setColumns(100);

        // Assert
        assertEquals(100, config.getColumns());
    }

    @Test
    public void testRows(){

        // Arrange
        config = new Config();

        // Act
        config.setRows(50);

        // Assert
        assertEquals(50, config.getRows());
    }

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