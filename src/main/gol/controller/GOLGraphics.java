package main.gol.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.gol.model.Boards.FixedBoard;
import main.gol.model.Cell;

import java.util.ArrayList;


public class GOLGraphics  {

  /*  private MainController mc;
    @FXML private Canvas canvas;
    private GraphicsContext gc;
    private FixedBoard board;
    private int cellSize = 5;

    //@FXML private ColorPicker cellColor, gridColor, backgroundColor;
    //@FXML private Canvas canvas;  // is this allowed? due to fxml fx:controller mapping??

    private Color gridColor = Color.LIGHTGREY;
    private Color cellColor = Color.BLACK;
    private Color backgroundColor = Color.WHITE;

    public GOLGraphics(MainController mc, Canvas canvas, GraphicsContext gc, FixedBoard board, int cellSize) {
        this.mc = mc;
        this.canvas = canvas;
        this.gc = gc;
        this.board=board;
        this.cellSize = cellSize;
    }


    //private ArrayList<Cell> generationList;


    // ******************************************* //
    //                                             //
//
//    // GOLGraphics constructor
//    public GOLGraphics() {
//
//        getGraphicContext();
//    }


    public void getGraphicContext(){


        gc = canvas.getGraphicsContext2D();
    }






    // Draw board
    public void drawBoard(FixedBoard board) {

        Cell[][] grid = board.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                drawCell(grid[i][j]);
            }
        }
    }

    // Draw cell
    public void drawCell(Cell cell) {

        if (cell.getState()){
            gc.setFill(cellColor);
            gc.setStroke(gridColor);
        }
        else {
            gc.setFill(backgroundColor);
            gc.setStroke(Color.WHITE);
        }
          gc.fillRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
        gc.strokeRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
    }

    // Draw generation

    public void drawGeneration(ArrayList<Cell> genList){
        int count = 0;
        for (Cell cell : genList) {
            drawCell(cell);
            System.out.println(genList.get(count));
            count++;
        }
    }

    *//****************************************//*

    // Setters & Getters
    public void setCellColor(Color color){
        this.cellColor = color;
    }

    public void setGridColor(Color color){
        this.gridColor = color;
    }

    public void setBcColor(Color color){
        this.backgroundColor = color;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getCellSize(){
        return this.cellSize;
    }*/
}
