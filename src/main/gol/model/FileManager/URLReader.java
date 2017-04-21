package main.gol.model.FileManager;

import main.gol.controller.MainController;
import main.gol.controller.util.Dialogs;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * URLReader class handles reading and parsing of URL links as well as
 * population of the gameBoard matrix with the parsed character data.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.0
 */
public class URLReader {

    private MainController mc = new MainController();

    // Character set
    private static Charset charset = Charset.forName("US-ASCII");

    // File
    private static File theFile;

    // Matrix byteBoard config
    private int rows = 0;
    private int columns = 0;
    private int defRows = 110;
    private int defCols = 160;

    // Lists and arrays
    private byte[][] matrix;
    private List<List<Integer>> listOfInts;
    private List<List<Byte>> listOfBytes;

    //Dialogs
    private Dialogs dialog;


    /**
     * Read and parse plaintext files from URL and load pattern into gameBoard matrix
     *
     * @param inURL String
     */
    public void readGameBoardFromURL(String inURL) throws Exception {

        URL url = new URL(inURL);
        URLConnection conn = url.openConnection();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        byte[][] matrix = new byte[mc.getRows()][mc.getColumns()];
        ArrayList<Integer> listOfColumnsSizes = new ArrayList<>();

        int y = 0; // iteration handler
        String line;
        while ((line = br.readLine()) != null) {

            if (!line.startsWith("!")) {
                y++;
                listOfColumnsSizes.add(line.length());

                for (int x = 0; x < line.length(); x++) {
                    char dead = '.';
                    if (line.charAt(x) == dead) {
                        matrix[y + 25][x + 25] = 0; // Push cell position
                    }
                    char alive = 'O';
                    if (line.charAt(x) == alive) {
                        matrix[y + 25][x + 25] = 1; // Push cell position
                    }
                    if (line.charAt(x) == ' ') {
                        y++;
                    }
                }
            }
        } // end while loop
        Integer columnsSize = Collections.max(listOfColumnsSizes); //Selects the max value from list of columnSizes
        setColumns(columnsSize); // Update global variable
        setRows(y); // Update global variable
        setMatrix(matrix); // set gameBoard
    }

    /**
     * NOT IN USE ATM.
     * <p>
     * Read file and populate ListOfIntegers and list of Bytes
     * Where to go from here
     */
    public void parseAndPopulateList() {

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(theFile), charset))) {

            listOfBytes = new ArrayList<>();
            listOfInts = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("!")) {
                    line = line.trim();
                    // Add new row, add string to row, then finally add row to list

                    List<Byte> bytes = new ArrayList<>();
                    List<Integer> ints = new ArrayList<>();
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '.') {

                            ints.add(i, 0);
                            bytes.add(i, (byte) 0);

                        } else if (line.charAt(i) == 'O') {

                            ints.add(i, 1);
                            bytes.add(i, (byte) 1);
                        }
                    }
                    this.listOfInts.add(ints);
                    this.listOfBytes.add(bytes);
                }
            } // End while loop
            setListOfInts(listOfInts);
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
            dialog.notFound();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }


    /**
     * Print file,filename and array/list details to console
     */
    public void getInfo() {

        System.out.println(theFile.getPath());
        System.out.println(theFile.getName());
        printListOfInts();
        //printMatrixArray();

    }

    /**
     * Print the populated 2d Array to console (Debugger)
     */
    public void printMatrixArray() {

        for (int y = 0; y < this.matrix.length; y++) {
            for (int x = 0; x < this.matrix[y].length; x++) {
                System.out.print(this.matrix[y][x]);
            }
            System.out.println();
        }
    }

    /**
     * Print the populated List of Lists to console (Debugger)
     */
    public void printListOfInts() {

        Iterator it = this.listOfInts.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

    }

    /**
     * @return File theFile
     */
    public static File getTheFile() {
        return theFile;
    }

    /**
     * @return byte[][] matrix
     */
    public byte[][] getMatrix() {
        return this.matrix;
    }

    /**
     * @return int Rows
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * @return int columns
     */
    public int getColumns() {
        return this.columns;
    }

    /**
     * @param rows int
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * @param columns int
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * @param matrix byte[][]
     */
    public void setMatrix(byte[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * @param listInts list ArrayList
     */
    public void setListOfInts(List<List<Integer>> listInts) {
        this.listOfInts = listInts;
    }


}
