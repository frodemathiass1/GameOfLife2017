//package main.gol.controller;
//
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;
//import main.gol.model.Boards.FixedBoard;
//import main.gol.model.Cell;
//
//import java.util.ArrayList;
//
//
//// TODO: 06.04.2017 :Draw methods are working when called in MC, but GUI controllers is not working.Probably because its FXML objects
//// TODO: ColorPicker, Grid size settings, cell size etc needs to be fixed somehow.....
//
//
//public class Draw {
//
//    //private GraphicsContext gc;
//    private int cellSize = 5;
//    private MainController mc;
//
//    private Color gridColor = Color.LIGHTGREY;
//    private Color cellColor = Color.BLACK;
//    private Color backgroundColor = Color.WHITE;
//
//
//    // Draw grid - Working
//    public void drawGrid(Cell[][] grid, GraphicsContext gc) {
//
//        for (int x = 0; x < grid.length; x++) {
//            for (int y = 0; y < grid[x].length; y++) {
//                drawCell(grid[x][y], gc);
//            }
//        }
//    }
//
//    // Draw board - Working
//    public void drawBoard(FixedBoard board, GraphicsContext gc) {
//
//        Cell[][] grid = board.getGrid();
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
//                drawCell(grid[i][j], gc);
//            }
//        }
//    }
//
//    // Draw cell - Working
//    public void drawCell(Cell cell, GraphicsContext gc) {
//
//        if (cell.getState()) {
//            gc.setFill(Color.BLACK); // ColorPicker values should be set here (Wrong type to use getter)
//            gc.setStroke(Color.LIGHTGRAY);
//        } else {
//            gc.setFill(Color.WHITE);
//            gc.setStroke(Color.LIGHTGRAY);
//        }
//        gc.fillRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
//        gc.strokeRect(cell.getX() * cellSize, cell.getY() * cellSize, cellSize, cellSize);
//    }
//
//
//    // Draw generation - Working
//    public void drawGeneration(ArrayList<Cell> genList, GraphicsContext gc) {
//
//        for (Cell cell : genList) {
//            drawCell(cell, gc);
//        }
//    }
//}
