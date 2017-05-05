package main.gol.model.filemanager;

import main.gol.controller.util.Dialogs;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Decoder class reads plaintext Files, RLE files and URLs
 * <p>
 * RLE files are converted to plaintext files before they are decoded.
 * We put so much work into the Decoder class, so this was the best way for us to decode RLE files,
 * given the short time had left to finish the project. It preforms well, but the drawback is of course the temp file.
 * To avoid multiple temp files or files being stored at your hdd after application exit, they are set to delete on exit,
 * but are preferably deleted after use in the try/catch block where they are used.
 *
 * @version 2.0
 */
public class Decoder {

    private static String Name = "No name";
    private static String Origin = "Unknown.";
    private static String Content = "No content in this file.";
    private static String Link = "No links in this file.";
    private BufferedReader reader;



    /**
     * Files is parsed to this method, and the decodeRLE method writes the temp files for further use.
     *
     * @param theFile File
     * @throws Exception e
     */
    public void readAndDecodeFile(File theFile) throws Exception {

        reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(theFile)));
        decodeRLE(reader);
    }

    /**
     * URLs is parsed to this method, and the decodeRLE method writes the temp files for further use.
     *
     * @param inURL String
     * @throws Exception e
     */
    public void readAndDecodeURL(String inURL) throws Exception {

        URL url = new URL(inURL);
        URLConnection conn = url.openConnection();
        reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        decodeRLE(reader);
    }

    /**
     * When Decoder is instantiated, either a file or URL is parsed to this class.
     * <p>
     * This is the main RLE decoder that writes the temp files from the parsed files or URLs.
     * It takes a BufferedReader as a parameter which is decided when you instantiate the Decoder.
     * <p>
     * The plaintext decoder uses '.' for dead cells, and 'O' for live cells.
     * Therefor the StringBuilder builds the matrix after these criteria.
     * In other words, decodeRLE converts RLE files to plaintext files.
     *
     * @param reader BufferedReader
     * @throws Exception e
     */
    public void decodeRLE(BufferedReader reader) throws Exception {

        try {
            // Variables for the loop
            String line;
            int number = 0;
            StringBuilder boardString = new StringBuilder();

            // While loop to read all line.
            while ((line = reader.readLine()) != null) {
                // Set the file info
                if (line.startsWith("#N")) {
                    Name = line.substring(3);
                } else if (line.startsWith("#O")) {
                    Origin = line.substring(3);
                } else if (line.startsWith("#C")) {
                    if (line.startsWith("#C www") || (line.startsWith("#C http"))) {
                        Link = line.substring(3);
                    } else {
                        Content = line.substring(3);
                    }
                }
                // For loop to read all chars in the line.
                for (int i = 0; i < line.length(); i++) {
                    if (!(line.startsWith("#") || line.startsWith("x"))) {
                        if (Character.isDigit(line.charAt(i))) {
                            // For all digits, set the number to add that many elements to the string.
                            if (number == 0) {
                                number = Character.getNumericValue(line.charAt(i));
                            } else if (number > 0){
                                // For digits that has a higher value than 9.
                                number *= 10;
                                number += Character.getNumericValue(line.charAt(i));
                            }
                        }
                        // For all the dead cells, get the number value and add the given number of cells.
                        char dead = 'b';
                        if (line.charAt(i) == dead) {
                            if (number == 0) {
                                boardString.append(".");
                            } else if (number > 0){
                                while (number > 0) {
                                    boardString.append(".");
                                    number--;
                                }
                            }
                        }
                        // For all the live cells, get the number value and add the given number of cells.
                        char alive = 'o';
                        if (line.charAt(i) == alive) {
                            if (number == 0) {
                                boardString.append("O");
                            } else if (number > 0) {
                                while (number > 0) {
                                    boardString.append("O");
                                    number--;
                                }
                            }
                        }
                        // Add a new line to the string at the RLE new line
                        char newline = '$';
                        if (line.charAt(i) == newline) {
                            if (number == 0) {
                                boardString.append("\n");
                            } else if (number > 0) {
                                while (number > 0) {
                                    boardString.append("\n");
                                    number--;
                                }
                            }
                        }
                    }
                }
            }
            // Create the temp file.
            FileWriter tempFile = new FileWriter("temp.gol");
            BufferedWriter out = new BufferedWriter(tempFile);
            out.write(String.valueOf(boardString));
            // Close the output stream after use.
            out.close();
            File temp = new File("temp.gol");
            // Delete on exit as a fail-safe if the handler fails.
            temp.deleteOnExit();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method is a parser for plaintext pattern files.
     *
     * @param reader BufferedReader
     * @param board  byte[][]
     * @throws Exception e
     */
    public void decodeText(BufferedReader reader, byte[][] board) throws Exception {

        int y = 0;
        String line;

        try {
            // Calculate the position of the loaded board
            int colSpacer = (80 - (BoardParser.getCols() / 2));
            int rowSpacer = (48 - (BoardParser.getRows() / 2));
            while ((line = reader.readLine()) != null) {
                // Set the file info
                if (line.startsWith("!Name")) {
                    Name = line.substring(7);
                } else if (line.startsWith("!Author")) {
                    Origin = line.substring(9);
                } else if (line.startsWith("!")) {
                    if (line.startsWith("!www") || (line.startsWith("!http"))) {
                        Link = line.substring(1);
                    } else {
                        Content = line.substring(1);
                    }
                }
                // Decode the file
                if (!line.startsWith("!")) {
                    y++;
                    for (int x = 0; x < line.length(); x++) {
                        char dead = '.';
                        if (line.charAt(x) == dead) {
                            board[y + rowSpacer][x + colSpacer] = 0; // Push cell index position
                        }
                        char alive = 'O';
                        if (line.charAt(x) == alive) {
                            board[y + rowSpacer][x + colSpacer] = 1; // Push cell index position
                        }
                        if (line.charAt(x) == ' ') {
                            y++;
                        }
                    }
                }
            }
            // Reset the spacer calculations
            BoardParser.setCols(0);
            BoardParser.setRows(0);

        } catch (ArrayIndexOutOfBoundsException oob) {
            Dialogs dialog = new Dialogs();
            dialog.oops();
            System.err.println("ArrayIndex out of bounds!");
        }
    }

    /**
     * This method returns the Name of the loaded board as a string.
     *
     * @return String Name
     */
    public String getTheName() {
        return Name;
    }

    /**
     * This method returns the author of the file as a string.
     *
     * @return String Origin
     */
    public String getOrigin() {
        return Origin;
    }

    /**
     * This method returns the Content (Description) of the loaded board as a string.
     *
     * @return String Content
     */
    public String getContent() {
        return Content;
    }

    /**
     * This method returns the Link in the file as a string.
     *
     * @return String Link
     */
    public String getLink() {
        return Link;
    }

    /**
     * This method sets the name of the file.
     *
     * @param name String
     */
    public static void getTheName(String name) {
        Name = name;
    }

    /**
     * This method sets the origin details of the file.
     *
     * @param origin String
     */
    public static void setOrigin(String origin) {
        Origin = origin;
    }

    /**
     * This method sets the content text.
     *
     * @param content String
     */
    public static void setContent(String content) {
        Content = content;
    }

    /**
     * This method sets the file link.
     *
     * @param link String
     */
    public static void setLink(String link) {
        Link = link;
    }
}