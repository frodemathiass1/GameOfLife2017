package main.gol.model.Boards;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.gol.model.Cell;

import java.util.*;


public class DynamicBoard  {


    // Cell, grid, background Color
    private Color gridColor = Color.LIGHTGREY;
    private Color cellColor = Color.BLACK;
    private Color backgroundColor = Color.WHITE;

    private GraphicsContext gc;
    private int cellSize;
    private Cell[][] grid;

    List<List<Byte>> bytes;
    List<List<Cell>> listOfCells;
    private final int MAX_COL = 160;
    private final int MAX_ROW = 110;
    private boolean state = false;
    private byte nextState;
    List<Byte> generationList;
    private List<Integer> coord;




    public void setBoard() {

            // GridSize
            int outerSize = 5;
            int innerSize = 5;

            // Master grid
            List<List<Cell>> outer = new ArrayList<>();

            // Inner grid
            List<Cell> inner = null;

            for (int i = 0; i < outerSize; i++){

                inner = new ArrayList<>(innerSize);

                for (int j = 0; j < innerSize; j++){
                    inner.add(new Cell(i, j));
                    System.out.print(j+" ");
                }
                outer.add(inner);
                System.out.println();

            }

        for (List<Cell> c: outer)
        {
            System.out.println(c);
        }


        //System.out.println(cells.get(0).get(0));



//        for (int x = 0; x < MAX_COL; x++) {
//            for (int y = 0; y < MAX_ROW; y++) {
//                Cell cell = new Cell(x, y);
//
//                Iterator it = listOfCells.iterator();
//                while(it.hasNext()){
//
//                    int b = (byte)it.next();
//                    if(b ==1){
//                        cell.setState(true);
//                    }
//                    else if(b == 0){
//                        cell.setState(false);
//                    }
//                    listOfCells.get(x).add(cell);
//                }
//
//                if (board[y][x] == 1) { // flip x and y axis. Why? because that's how it works
//                    cell.setState(true);
//                } else  if(board[y][x] == 0){
//                    cell.setState(false);
//                }
//                grid[x][y] = cell; // setBoard cell grid
//            }
//        }

        // We are working with references so we don't need to update its neighbors unless the map is reinitialized (cell toggles to alive when clicked).
        for (int x = 0; x < listOfCells.size(); x++) {
            for (int y = 0; y < listOfCells.get(x).size(); y++) {
                //grid[x][y].initNeighbors(this);

                // TODO: 06.04.2017 :: initNeighbors in cell class takes fixedBoard as arg
                //System.out.println("Neighbors: " + grid[x][y].getNeighbors());
            }
        }
    }

    /*public void setBoard(int columns, int rows) {

        addRows(rows);
        //fillRows(columns);
        setBoard(listOfCells);
        //System.out.println(board[2][3]);
    }*/

    public void nextGeneration(){
        // implemented from interface
    }


    // Dynamic board constructor
    public DynamicBoard(GraphicsContext gc, int cellSize){

           this.gc = gc;
           this.cellSize = cellSize;
    }



    // Instantiate ListOfList and add rows to list
    public void addRows(int rows){

        listOfCells = new ArrayList<>();
        for(int i = 0; i < rows; i++){

            List<Cell> row = new ArrayList<>();
            this.listOfCells.add(row);
        }
    }

    // Fill rows with (byte) values 0||1
   public void fillRows(int columns){
        for(int x = 0; x < listOfCells.size(); x++){
            for(int y = 0; y < columns; y++){

                this.listOfCells.get(x).add(new Cell(x,y));
            }
        }
    }

    // Instantiate ListOfList and add rows to list
    public void addByteRows(int rows){

        bytes = new ArrayList<>();
        for(int i = 0; i < rows; i++){

            List<Byte> row = new ArrayList<>();
            this.bytes.add(row);
        }
    }

    // Fill rows with (byte) values 0||1
    public void fillByteRows(int columns){
        for(int x = 0; x < bytes.size(); x++){
            for(int y = 0; y < columns; y++){

                this.bytes.get(x).add((byte)0);
            }
        }
    }


    // Print byteGrid
    public void printGrid(){
        for(int i = 0; i < listOfCells.size(); i++){
            System.out.println(listOfCells.get(i));

        }
    }

    // Fancy looper
    public void print(){

        listOfCells.forEach(System.out::println);
    }

   /* // Set method to populate Board with data from Text files
    public void setListOfCells(List<List<Byte>> listOfCells) {
        this.listOfCells = listOfCells;
    }*/





}






