package main.gol.model.FileManager;

import javafx.stage.FileChooser;
import main.gol.controller.MainController;
import main.gol.controller.util.Dialogs;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * FileReader class handles reading and parsing of plaintext files from disk as well as
 * population of the gameBoard matrix with the parsed character data.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.0
 */
public class FileReader extends Reader {

    private MainController mc = new MainController();

    // Character set
    private static final Charset charset = Charset.forName("US-ASCII");

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

    //Decode helpers
    private char alive = 'O';
    private char dead = '.';

    //Dialogs
    private Dialogs dialog;


    // Methods that MUST be implemented from abstract class 'Reader'
    // Not quite sure how to use this.......
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return 0;
        // dont know how to use this
    }

    @Override
    public void close() throws IOException {
        // dont know how to use this
    }


    /**
     * Add fileChooser and select .txt / .cells file
     *
     */
    public void chooseFile() {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select file .txt /.cells");

        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "Text files", "*.txt", "*.cells");

        chooser.getExtensionFilters().add(fileExtensions);
        theFile = chooser.showOpenDialog(null).getAbsoluteFile();
    }

    /**
     * Read and parse plaintext files from disk and load pattern into gameBoard matrix
     *
     * @param inFile File,
     */
    public void readGameBoardFromDisk(File inFile) {

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(inFile), charset))) {

            byte[][] matrix = new byte[mc.getRows()][mc.getColumns()];
            ArrayList<Integer> lineLengths = new ArrayList<>();

            int y = 0; // iteration handler
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("!")) {

                    y++;
                    lineLengths.add(line.length());

                    for (int x = 0; x < line.length(); x++) {
                        if (line.charAt(x) == '.') {
                            matrix[y + 25][x + 25] = 0;
                        }
                        if (line.charAt(x) == 'O') {
                            matrix[y + 25][x + 25] = 1;
                        }
                        if (line.charAt(x) == ' ') {
                            y++;
                        }
                    }
                }
            } // end while loop
            Integer x_MAX = Collections.max(lineLengths);
            setColumns(x_MAX); // Update global variable
            setRows(y);
            setMatrix(matrix);
        } catch (FileNotFoundException fnf) {
            //fnf.printStackTrace();
            System.out.println("File not found");
            dialog.notFound();
        } catch (IOException ioe) {
            //ioe.printStackTrace();
            System.out.println("Error reading file");
        } catch (ArrayIndexOutOfBoundsException oob) {
            //oob.printStackTrace();
            System.out.println("Error loading file");
        }
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
