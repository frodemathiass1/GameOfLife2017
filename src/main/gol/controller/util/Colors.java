package main.gol.controller.util;

import javafx.scene.paint.Color;


public class Colors {


    // Cell, gridLine, background Color
    private Color gridLine = Color.LIGHTGRAY;
    private Color cell = Color.BLACK;
    private Color bc = Color.WHITESMOKE;


    public void reset(){
        setBc(Color.WHITE);
        setCell(Color.BLACK);
        setGridLine(Color.LIGHTGRAY);
 }

  

    public Color getGridLine() {
        return gridLine;
    }

    public void setGridLine(Color gridLine) {
        this.gridLine = gridLine;
    }

    public Color getCell() {
        return cell;
    }

    public void setCell(Color cell) {
        this.cell = cell;
    }

    public Color getBc() {
        return bc;
    }

    public void setBc(Color bc) {
        this.bc = bc;
    }

}
