package main.gol.model.filemanager;

import javafx.stage.FileChooser;

import java.io.*;

/**
 * FileHandler class lets you choose a file, and gets the file type of the chosen file.
 * theFileType and theFile gets updated with the correct file type values,
 * and getters is used by other classes to retrieve the values.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 2.0
 */
public class FileHandler {

    private File theFile;
    private String theFileType;

    /**
     * fileSelect lets you choose a file and gets the file type value.
     */
    public void fileSelect() {

        choose();
        fileSelectType(this.theFile);
    }

    /**
     * This method opens a new fileChooser and let you select .txt / .cells / .rle files.
     */
    public void choose()  {

        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select File .txt /.cells /.rle");
            FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter
                    ("Text File / RLE File", "*.txt", "*.cells", "*.rle");
            chooser.getExtensionFilters().add(fileExtensions);
            this.theFile = chooser.showOpenDialog(null).getAbsoluteFile();
        } catch (Exception e) {
            System.err.println("Something went wrong with the file selection");
        }
    }

    /**
     * This method checks the file type (RLE or plaintext), and sets theFileType to the correct value.
     * RLEDecoder is instantiated if RLE file is selected.
     * Getters is used to retrieve the file tpye in other classes.
     *
     * @param theFile File
     */
    public void fileSelectType(File theFile)  {

        try {
            String[] fileType = theFile.getName().split("[.]");
            if (fileType[1].contains("txt") || fileType[1].contains("cells")) {
                System.out.println("Text File Loaded");
                this.theFileType = "Text File";
            } else if (fileType[1].contains("rle")) {
                System.out.println("RLE File Loaded");
                this.theFileType = "RLE File";
                // Instantiate the RLEDecoder and parse the file
                RLEDecoder RLE = new RLEDecoder();
                RLE.RLEDecodeFile(this.theFile);
            }
        } catch (Exception e) {
            System.err.println("Error:" + e);
        }
    }

    /**
     * This method sets the file.
     *
     * @param theFile File
     */
    public void setTheFile(File theFile) {
        this.theFile = theFile;
    }

    /**
     * This method returns theFile.
     *
     * @return File theFile
     */
    public File getTheFile() {
        return this.theFile;
    }

    /**
     * This method returns the fileType as a string.
     *
     * @return String theFileType
     */
    public String getTheFileType() {
        return this.theFileType;
    }
}