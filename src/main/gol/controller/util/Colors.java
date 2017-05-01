package main.gol.controller.util;

import javafx.scene.paint.Color;

/**
 * This class is used for setting default colors and for manipulation of Board colors
 * triggered by the GUI colorPickers.
 *
 * @version 1.0
 */
public class Colors {

    // Cell, gridLine & background Color
    private Color gridLine = Color.LIGHTGRAY;
    private Color cell = Color.BLACK;
    private Color background = Color.WHITESMOKE;

    /**
     * This method is used for resetting all the Board colors to default values.
     */
    public void resetAll() {

        setBackground(Color.WHITE);
        setCell(Color.BLACK);
        setGridLine(Color.LIGHTGRAY);
    }

    /**
     * This method returns the gridLine color.
     * @return Color
     */
    public Color getGridLine() {
        return gridLine;
    }

    /**
     * This method returns the Cell color.
     * @return Color
     */
    public Color getCell() {
        return cell;
    }

    /**
     * This method returns the background color.
     * @return Color
     */
    public Color getBackground() {
        return background;
    }

    /**
     *
     * @param gridLine Color
     */
    public void setGridLine(Color gridLine) {
        this.gridLine = gridLine;
    }

    /**
     * This method sets the Cell color.
     * @param cell Color
     */
    public void setCell(Color cell) {
        this.cell = cell;
    }

    /**
     * This method sets the background color.
     * @param background Color
     */
    public void setBackground(Color background) {
        this.background = background;
    }
}