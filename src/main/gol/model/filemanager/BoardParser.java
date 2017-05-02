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
 * It is separated into tree different methods. ParseURL, ParseFile an Parser.
 * The first two is to separate files and URLs, and the third is the parser itself.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.1
 */
public class BoardParser {

    private static final Charset charset = Charset.forName("US-ASCII");
    private final Dialogs dialog = new Dialogs();
    private byte[][] theBoard;

    /**
     * ParseURL gets the URL as a string, and parse it along to the Parser method.
     * <p>
     * To accomplish this, the string is converted to a URL, and a connection is opened.
     * BufferedReader is instantiated so the Parser can read the content of the URL.
     *
     * @param inURL String
     * @throws Exception e
     */
    public void ParseURL(String inURL) throws Exception {

        URL url = new URL(inURL);
        URLConnection conn = url.openConnection();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(),charset));
        Parser(reader);
    }

    /**
     * ParseFile gets the file, and parse it along to the Parser method.
     * <p>
     * To accomplish this, BufferedReader is instantiated so the Parser can read the content of the file.
     *
     * @param inFile File
     * @throws Exception e
     */
    public void ParseFile(File inFile) throws Exception {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(inFile), charset));
        Parser(reader);
    }

    /**
     * This is the main parser method.
     * <p>
     * Config and byte is instantiated to correctly parse the matrix along to the main decoder.
     * theBoard is updated as a global value, and a getter is used by other classes to create the new board.
     *
     * @param reader BufferedReader
     */
    public void Parser(BufferedReader reader)  {

        try {
            Config config = new Config();
            byte[][] board = new byte[config.getRows()][config.getColumns()];
            Decoder decoder = new Decoder();
            decoder.decodePlainText(reader, board);
            theBoard = board; // update global variable

        } catch (FileNotFoundException fnf) {
            System.out.println("File not found");
            dialog.notFoundException();
        } catch (IOException ioe) {
            System.err.println("Error reading file");
        } catch (ArrayIndexOutOfBoundsException oob) {
            System.out.println("Error loading file. (Mismatch) Index out of bounds. ");
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
}
