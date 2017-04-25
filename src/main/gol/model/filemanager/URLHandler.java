package main.gol.model.filemanager;

import main.gol.model.boards.Config;
import main.gol.controller.util.Dialogs;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * URLHandler class handles reading and parsing of URL links as well as
 * population of the gameBoard matrix with the parsed character data.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.0
 */
public class URLHandler {


    private static Charset charset = Charset.forName("US-ASCII");
    private byte[][] theMatrix;


    /**
     * Read and decodePlainText plaintext files from URL and load pattern into gameBoard matrix
     *
     * @param inURL String
     */
    public void readAndParse(String inURL) throws Exception  {

        URL url = new URL(inURL);
        URLConnection conn = url.openConnection();
        System.out.println(url.toString());

        try(  BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(),charset))){

            Config config = new Config();
            byte[][] matrix = new byte[config.getRows()][config.getColumns()];
            Decoder decoder = new Decoder();
            decoder.decodePlainText(reader,matrix);
            theMatrix = matrix; // update global variable
        }


        catch (IOException ioe) {
            System.err.println("Error reading file");
        }
        catch (ArrayIndexOutOfBoundsException oob) {
            System.out.println("Error loading file. (Mismatch) Index out of bounds. ");
            Dialogs dialog = new Dialogs();
            dialog.notFoundException();
        }
    }

    /**
     * @return byte[][] matrix
     */
    public byte[][] getMatrix() {
        return this.theMatrix;
    }

}
