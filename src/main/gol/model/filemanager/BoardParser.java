package main.gol.model.filemanager;

import main.gol.controller.util.Dialogs;
import main.gol.model.boards.Config;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * The BoardParser class gets files and URLs, and parses them to the main decoder.
 * <p>
 * It is separated into tree different methods. parseURL, parseFile an parser.
 * The first two is to separate files and URLs, and the third is the parser itself.
 *
 * @version 1.1
 */
public class BoardParser {

    private static final Charset charset = Charset.forName("US-ASCII");
    private final Dialogs dialog = new Dialogs();
    private byte[][] theBoard;
    public static int rows = 0;
    public static int cols = 0;

    /**
     * parseURL gets the URL as a string, and parse it along to the parser method.
     * <p>
     * To accomplish this, the string is converted to a URL, and a connection is opened.
     * BufferedReader is instantiated so the parser can read the content of the URL.
     *
     * @param inURL String
     * @throws Exception e
     */
    public void parseURL(String inURL) throws Exception {

        URL url = new URL(inURL);
        URLConnection countConn = url.openConnection();
        URLConnection conn = url.openConnection();

        // Count what is to be approx rows and cols of the board
        try {
            BufferedReader count = new BufferedReader(
                    new InputStreamReader(countConn.getInputStream(), charset));
            int lines = 0;
            cols = count.readLine().length() * 2; // This just works! Length always seems to be half the real length.
            while (count.readLine() != null) {
                lines++;
            }
            rows = lines - 3; // Most plaintext files have 3 lines of info, so remove those.
        } catch (IOException ioe) {
            System.out.println("Error occurred calculating rows and cols in this URL.");
        }

        // Parse the URL
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), charset));
            parser(reader);
        } catch (IOException ioe) {
            dialog.urlError();
        }
    }

    /**
     * parseFile gets the file, and parse it along to the parser method.
     * <p>
     * To accomplish this, BufferedReader is instantiated so the parser can read the content of the file.
     *
     * @param inFile File
     * @throws Exception e
     */
    public void parseFile(File inFile) throws Exception {

        // Count what is to be rows and cols of the board
        try {
            BufferedReader count = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(inFile), charset));
            int lines = 0;
            cols = count.readLine().length();
            while (count.readLine() != null) {
                lines++;
            }
            rows = lines;
        } catch (IOException ioe) {
            System.out.println("Error occurred calculating rows and cols in this file.");
        }

        // Parse the board
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(inFile), charset));
            parser(reader);
        } catch (IOException ioe) {
            dialog.fileError();
        }
    }

    /**
     * This is the main parser method.
     * <p>
     * Config and byte is instantiated to correctly parse the matrix along to the main decoder.
     * theBoard is updated as a global value, and a getter is used by other classes to create the new board.
     *
     * @param reader BufferedReader
     */
    private void parser(BufferedReader reader) {

        try {
            Config config = new Config();
            byte[][] board = new byte[config.getRows()][config.getColumns()];
            Decoder decoder = new Decoder();
            decoder.TXTDecode(reader, board);
            theBoard = board; // update global variable
        } catch (FileNotFoundException fnf) {
            dialog.notFoundException();
        } catch (IOException ioe) {
            System.err.println("Error reading file");
        } catch (ArrayIndexOutOfBoundsException oob) {
            System.err.println("Error loading file. (Mismatch) Index out of bounds. ");
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }

    /**
     * This method returns the gameBoard byte[][] array
     *
     * @return byte[][] theBoard
     */
    public byte[][] getTheBoard() {
        return this.theBoard;
    }

    public static int getRows() {
        return rows;
    }

    public static int getCols() {
        return cols;
    }

    public static void setRows(int rows) {
        BoardParser.rows = rows;
    }

    public static void setCols(int cols) {
        BoardParser.cols = cols;
    }
}
