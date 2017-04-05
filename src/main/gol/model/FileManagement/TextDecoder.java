package main.gol.model.FileManagement;

import javafx.stage.FileChooser;
import main.gol.controller.MainController;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class TextDecoder {

    private static Charset charset = Charset.forName("US-ASCII");

    // File
    private static File inFile;

    // Matrix config
    private int rows = 0;
    private int columns = 0;
    private int defRows = 110;
    private int defCols = 160;

    // Lists and arrays
    private byte[][] matrix;
    private List<List<Integer>> listOfInts;
    private List<List<Byte>> listOfBytes;

    //Decode helpers
    char alive = 'O';
    char dead = '.';

    /**
     * Choose file and return it
     * @return inFile
     */
    public File chooseFile(){

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select file .txt /.cells");

        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "Text files", "*.txt", "*.cells");
        chooser.getExtensionFilters().add(fileExtensions);


        inFile = chooser.showOpenDialog(null).getAbsoluteFile();
        return inFile;
    }

    /**
     * Run GOL plaintext files from Web
     *
     * @param inURL String
     * @param matrix byte[][]
     */
    public void runURL(String inURL, byte[][] matrix){
        try {
            URL url = new URL(inURL);
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));


            matrix = new byte[defRows][defCols];
            ArrayList<Integer> lineLengths =new ArrayList<>();

            int y = 0; // loop matrix population handler
            String line;
            while ((line = br.readLine()) != null){
                if (!line.startsWith("!") ) {

                    y++;
                    lineLengths.add(line.length());

                    for(int x = 0; x < line.length(); x++){
                        if (line.charAt(x) == dead){
                            matrix[y+ 25][x+25] = 0; // Pushes the cells position in array
                        }
                        if(line.charAt(x)== alive){
                            matrix[y+25][x+ 25] = 1;
                        }
                        if(line.charAt(x) == ' '){
                            y++;
                        }
                    }
                }
            } // end while loop
            Integer x_MAX = Collections.max(lineLengths);
            setColumns(x_MAX); // Update global variable
            setRows(y);
            setMatrix(matrix);
        }
        catch (FileNotFoundException fnf){
            fnf.printStackTrace();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    /**
     * Run GOL plaintext from chosen text file
     *
     * @param inFile File,
     * @param matrix byte[][]
     */
    public void runFile(File inFile,byte[][] matrix){

        try{
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(inFile),charset));

            matrix = new byte[this.defRows][this.defCols];
            ArrayList<Integer> lineLengths =new ArrayList<>();

            int y = 0; // loop matrix population handler
            String line;
            while ((line = br.readLine()) != null){
                if (!line.startsWith("!") ) {

                    y++;
                    lineLengths.add(line.length());

                    for(int x = 0; x < line.length(); x++){
                        if (line.charAt(x) == '.'){
                            matrix[y + 25][x + 25] = 0;
                        }
                        if(line.charAt(x)== 'O'){
                            matrix[y + 25][x + 25] = 1;
                        }
                        if(line.charAt(x) == ' '){
                            y++;
                        }
                    }
                }
            } // end while loop
            Integer x_MAX = Collections.max(lineLengths);
            setColumns(x_MAX); // Update global variable
            setRows(y);
            setMatrix(matrix);
        }
        catch(FileNotFoundException fnf){
            fnf.printStackTrace();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Read file and populate ListOfIntegers and list of Bytes
     * Where to go from here
     */
    public void parseAndPopulateList(){

        try{
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(inFile),charset));

            listOfBytes = new ArrayList<>();
            listOfInts = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null){
                if (!line.startsWith("!")) {
                    line = line.trim();
                    // Add new row, add string to row, then finally add row to list

                    List<Byte> bytes = new ArrayList<>();
                    List<Integer> ints = new ArrayList<>();
                    for (int i = 0; i < line.length(); i++){
                        if ( line.charAt(i) == '.'){

                            ints.add(i,0);
                            bytes.add(i, (byte) 0);

                        }
                        else if (line.charAt(i) == 'O'){

                            ints.add(i,1);
                            bytes.add(i, (byte) 1);
                        }
                    }
                    this.listOfInts.add(ints);
                    this.listOfBytes.add(bytes);
                }
            }
        }
        catch (FileNotFoundException fnf){
            fnf.printStackTrace();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        setListOfInts(listOfInts);
    }

    /**
     * Read full file
     */
    public void readFile(){
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(
                            new File(inFile.getPath())));

            String line;
            while ((line = reader.readLine()) != null){
                    System.out.println(line);
            }
        }
        catch (FileNotFoundException fnf){
            fnf.printStackTrace();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Print file,filename and array/list details to console
     */
    public void getInfo(){

        System.out.println(inFile.getPath());
        System.out.println(inFile.getName());
        printListOfInts();
        printMatrixArray();

    }

    /**
     * Print the populated 2d Array to console (Debugger)
     */
    public void printMatrixArray(){

        for(int y = 0; y < this.matrix.length; y++){
            for(int x = 0; x < this.matrix[y].length; x++){
                System.out.print(this.matrix[y][x]);
            }
            System.out.println();
        }
    }

    /**
     * Print the populated List of Lists to console (Debugger)
     */
    public void printListOfInts(){

        Iterator it = this.listOfInts.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }

    }

    /**
     *
     * @return File inFile
     */
    public static File getInFile() {
        return inFile;
    }

    /**
     *
     * @return byte[][] matrix
     */
    public byte[][] getMatrix(){
        return this.matrix;
    }

    /**
     *
     * @return int Rows
     */
    public int getRows(){
        return this.rows;
    }

    /**
     *
     * @return int columns
     */
    public int getColumns(){
        return this.columns;
    }

    /**
     *
     * @param rows int
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     *
     * @param columns int
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     *
     * @param matrix byte[][]
     */
    public void setMatrix(byte[][] matrix) {
        this.matrix = matrix;
    }

    /**
     *
     * @param listInts list ArrayList
     */
    public void setListOfInts(List<List<Integer>> listInts) {
        this.listOfInts = listInts;
    }
}
