package main.gol.model.Boards;

import main.gol.model.FileManagement.TextDecoder;
import sun.jvm.hotspot.runtime.Bytes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class DynamicBoard {



    List<List<Byte>> listOfCells;
    private final int MAX_COL = 160;
    private final int MAX_ROW = 110;
    private byte state = 0;
    private byte nextState;
    List<Byte> generationList;



    private List<Integer> coord;


    // Dynamic board constructor
    public DynamicBoard(int rows, int columns){

            addRows(rows);
            fillRows(columns);
    }



    // Instantiate ListOfList and add rows to list
    public void addRows(int rows){

        listOfCells = new ArrayList<>();
        for(int i = 0; i < rows; i++){

            List<Byte> row = new ArrayList<>();
            this.listOfCells.add(row);
        }
    }

    // Fill rows with (byte) values 0||1
    public void fillRows(int columns){
        for(int x = 0; x < listOfCells.size(); x++){
            for(int y = 0; y < columns; y++){

                this.listOfCells.get(x).add((byte)0);
            }
        }
    }

    // Print byteGrid
    public void printGrid(){
        for(int i = 0; i < listOfCells.size(); i++){
            System.out.println(listOfCells.get(i));

        }
    }

    // Set method to populate Board with data from Text files
    public void setListOfCells(List<List<Byte>> listOfCells) {
        this.listOfCells = listOfCells;
    }



    class Coordinate{

        final int x;
        final int y;

        public Coordinate(int x, int y){

            this.x = x;
            this.y = y;
        }

        Coordinate move(Coordinate vector){
            return new Coordinate( x + vector.x, y + vector.y);
        }
    }


    Collection<Coordinate> coordinates = Arrays.asList(
            new Coordinate(-1,-1),
            new Coordinate(-1, 0),
            new Coordinate(-1,+1),
            new Coordinate( 0,-1),
            new Coordinate( 0,+1),
            new Coordinate(+1,-1),
            new Coordinate(+1, 0),
            new Coordinate(+1,+1)
    );

    Collection<Coordinate> getNearCoordinates(Coordinate origin){

        return coordinates.stream()

                .map(origin :: move)

                .collect(Collectors.toList());
    }


}






