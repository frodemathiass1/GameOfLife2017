package main.gol.model.filemanager;

import javafx.stage.FileChooser;
import main.gol.model.boards.Config;
import main.gol.controller.util.Dialogs;

import java.io.*;
import java.nio.charset.Charset;

/**
 * FileHandler class handles reading and parsing of plaintext files from disk as well as
 * population of the gameBoard theMatrix with the parsed character data.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.0
 */
public class FileHandler {

    private static final Charset charset = Charset.forName("US-ASCII");
    private Dialogs dialog = new Dialogs();
    private static File theFile;
    private byte[][] theMatrix;


    /**
     * Constructs a new FileHandler object and new board from parsed from fileData
     */
    public FileHandler() throws Exception{
        choose();
        readAndParse(theFile);
    }

    /**
     * Add fileChooser and select .txt / .cells file
     *
     */
     public void choose() {

         try{
             FileChooser chooser = new FileChooser();
             chooser.setTitle("Select file .txt /.cells");
             FileChooser.ExtensionFilter fileExtensions =
                     new FileChooser.ExtensionFilter(
                             "Text files", "*.txt", "*.cells");
             chooser.getExtensionFilters().add(fileExtensions);
             theFile = chooser.showOpenDialog(null).getAbsoluteFile();
         }
         catch (Exception e){
             System.err.println("Something wrong with file selection");
         }

     }


    /**
     * Read and decodePlainText plaintext files from disk and load pattern into gameBoard theMatrix
     *
     * @param inFile File,
     */
      public void readAndParse(File inFile) throws Exception{

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(inFile), charset))) {

            Config config = new Config();
            byte[][] matrix = new byte[config.getRows()][config.getColumns()];
            Decoder decoder = new Decoder();
            decoder.decodePlainText(reader,matrix);
            theMatrix = matrix; // update global variable

        }
        catch (FileNotFoundException fnf) {

            System.out.println("File not found");
            dialog.notFoundException();

        } catch (IOException ioe) {

            System.err.println("Error reading file");

        } catch (ArrayIndexOutOfBoundsException oob) {

            System.out.println("Error loading file. (Mismatch) Index out of bounds. ");

        }
    }

    /**
     * @return byte[][] theMatrix
     */
    public byte[][] getTheMatrix() {
        return this.theMatrix;
    }

}
