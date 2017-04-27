package main.gol.model.filemanager;

import javafx.stage.FileChooser;
import main.gol.model.boards.Config;
import main.gol.controller.util.Dialogs;

import java.io.*;
import java.nio.charset.Charset;

/**
 * FileHandler class handles reading and parsing of plaintext and RLE files from disk as well as
 * population of the gameBoard from theMatrix with the parsed character data.
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
     * Constructs a new FileHandler object and a new board from parsed fileData
     */
    public FileHandler() throws Exception {

        // Modify to support predefined files ort file chooser.
    }

    public void FileSelectType(File theFile) {

        // Temp file chooser. Maybe use switch in FileHandler()
        //choose();

        try {
            String[] fileType = theFile.getName().split("[.]");
            if (fileType[1].contains("txt") || fileType[1].contains("cells")) {
                System.out.println("Text File Loaded");
                readAndParse(this.theFile);
            } else if (fileType[1].contains("rle")) {
                System.out.println("RLE File Loaded");
                RLEDecoder RLE = new RLEDecoder(this.theFile);
                // Parse and delete the temp file
                File temp = new File("temp.gol");
                readAndParse(temp);
                temp.delete();
            }
        } catch (Exception e) {
            System.err.println("Error");
        }
    }

    /**
     * Add fileChooser and select .txt / .cells file
     *
     */
    public File choose() {

        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select File .txt /.cells /.rle");
            FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter
                    ("Text File / RLE File", "*.txt", "*.cells", "*.rle");
            chooser.getExtensionFilters().add(fileExtensions);
            theFile = chooser.showOpenDialog(null).getAbsoluteFile();
        } catch (Exception e) {
            System.err.println("Something went wrong with the file selection");
        }

        FileSelectType(this.theFile);

        return theFile;
    }

    /**
     * Read and decodePlainText plaintext files from disk and load pattern into gameBoard theMatrix
     *
     * @param inFile File
     */
    public void readAndParse(File inFile) throws Exception {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile), charset))) {
            Config config = new Config();
            byte[][] matrix = new byte[config.getRows()][config.getColumns()];
            Decoder decoder = new Decoder();
            decoder.decodePlainText(reader, matrix);
            theMatrix = matrix; // update global variable
        } catch (FileNotFoundException fnf) {
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
