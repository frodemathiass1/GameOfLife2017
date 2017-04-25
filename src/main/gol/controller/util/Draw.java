package main.gol.controller.util;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.scene.BoundsAccessor;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import main.gol.model.boards.Config;
import main.gol.model.Cell;

import java.util.ArrayList;
import java.util.List;


public class Draw {

    private GraphicsContext context;
    private Colors colors;
    private Config config;

    /**
     * Draw constructor. Takes the context and pre-defined colors from Colors class as argument
     * to draw the content to canvas.
     *
     * @param context GraphicsContext
     * @param colors Colors
     */
    public Draw(GraphicsContext context, Colors colors){
        this.context = context;
        this.colors = colors;
        config = new Config();
    }

    /**
     * This method draws the Grid to the canvas. Takes a "ListOfLists" as inputParameter
     * and the Graphic getContext.
     *
     * @param grid List<List<Cell>>
     */
    public void grid(List<List<Cell>> grid) {

        for (int x = 0; x < grid.size(); x++) {
            for (int y = 0; y < grid.get(x).size(); y++) {
                cell(grid.get(x).get(y));
            }
        }
    }

    /**
     * Draw method for the old fixed board
     *
     * @deprecated
     * @param grid Cell[][]
     */
    public void fixedGrid(Cell[][] grid) {

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                cell(grid[x][y]);
            }
        }
    }


    /**
     * This method draws a single cell on the Board grid by toggling it alive/dead
     *
     * @param cell Cell
     */
    public void cell(Cell cell) {

        int dim = config.cellSize();
        int x = cell.getX();
        int y = cell.getY();

        if (cell.getState()) {
            context.setFill(colors.getCell());
            context.setStroke(colors.getGridLine());
            //context.setLineWidth(0.2);
        } else {
            context.setFill(colors.getBc());
            //context.setLineWidth(0.2);
            context.setStroke(colors.getGridLine());

        }
          context.fillRect(x * dim, y * dim, dim, dim);
        context.strokeRect(x * dim, y * dim, dim, dim);

    }


    /**
     * This method draws next generation of Cells.
     *
     * @param gen ArrayList<Cell>
     */
    public void generation(ArrayList<Cell> gen) {

        for (Cell cell : gen) {
            cell(cell);
        }
    }
}
