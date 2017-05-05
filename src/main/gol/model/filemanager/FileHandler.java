package main.gol.model.filemanager;

import javafx.stage.FileChooser;
import main.gol.controller.util.Dialogs;

import java.io.*;

/**
 * FileHandler class lets you choose a file, and gets the file type of the chosen file.
 * <p>
 * theFileType and theFile gets updated with the correct file type values,
 * and getters is used by other classes to retrieve the values.
 *
 * @version 2.0
 */
public class FileHandler extends Thread {

    private static File theFile;
    private static String theFileType;

    /**
     * fileSelect lets you choose a file and gets the file type value.
     */
    public void chooseAndSelectType() {

        choose();
        if (!(theFile == null)) {
            fileSelectType(theFile);
        }
    }

    /**
     * This method opens a new fileChooser and let you select .txt / .cells / .rle files.
     */
    public void choose() {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select File .txt /.cells /.rle");
        FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter
                ("Text File / RLE File", "*.txt", "*.cells", "*.rle");
        chooser.getExtensionFilters().add(fileExtensions);
        theFile = chooser.showOpenDialog(null);
    }

    /**
     * This method checks the file type (RLE or plaintext), and sets theFileType to the correct value.
     * RLEDecoder is instantiated if RLE file is selected.
     * Getters is used to retrieve the file tpye in other classes.
     *
     * @param theFile File
     */
    public void fileSelectType(File theFile) {

        try {
            String[] fileType = theFile.getName().split("[.]");
            if (fileType[1].contains("txt") || fileType[1].contains("cells")) {
                theFileType = "Text File";
            } else if (fileType[1].contains("rle")) {
                theFileType = "RLE File";
                Decoder RLE = new Decoder();
                RLE.readAndDecodeFile(FileHandler.theFile);
            }
        } catch (Exception e) {
            Dialogs dialog = new Dialogs();
            dialog.fileError();
        }
    }

    /**
     * This method sets the file.
     *
     * @param theFile File
     */
    public void setTheFile(File theFile) {
        FileHandler.theFile = theFile;
    }

    /**
     * This method returns theFile.
     *
     * @return File theFile
     */
    public File getTheFile() {
        return theFile;
    }

    /**
     * This method returns the fileType as a string.
     *
     * @return String theFileType
     */
    public String getTheFileType() {
        return theFileType;
    }
}