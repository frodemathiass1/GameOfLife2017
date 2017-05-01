package main.gol.model.filemanager;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * RLEDecoder class reads RLE files and URLs, and creates a temp file that is later parsed to the main decoder.
 * The temp file is deleted after use, as you can see in the FileHandler or URLHandler class when RLE is selected.
 *
 * We put so much work into the Decoder class, so this was the best way for us to decode RLE files,
 * given the short time had left to finish the project. It preforms well, but the drawback is of course the temp file.
 * To avoid multiple temp files or files being stored at your hdd after application exit, they are set to delete on exit,
 * but are preferably deleted after use in the try/catch block where they are used.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.2
 */
public class RLEDecoder {

    /**
     * File is parsed to this method, and the RLEDecode method writes the temp file for further use.
     *
     * @param theFile File
     */
    public void RLEDecodeFile(File theFile) throws Exception {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(theFile)));
        RLEDecode(reader);
    }

    /**
     * URL is parsed to this method, and the RLEDecode method writes the temp file for further use.
     *
     * @param inURL String
     */
    public void RLEDecodeURL(String inURL) throws Exception {

        URL url = new URL(inURL);
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        RLEDecode(reader);
    }

    /**
     * When URLDecoder is instantiated, either a file or URL is parsed to this class.
     * This is the main RLE decoder that writes the temp file from the parsed file or URL.
     * It takes a BufferedReader as a parameter which is decided when you instantiate the RLEDecoder.
     *
     * The main decoder uses '.' for dead cells, and 'O' for live cells (plaintext files).
     * Therefor the StringBuilder builds the matrix after these criteria.
     * In other words, RLEDecode converts RLE files to plaintext files.
     *
     * @param reader BufferedReader
     */
    public void RLEDecode(BufferedReader reader) throws Exception {

        try {
            // Variables for the loop
            String lines;
            int number = 0;
            StringBuilder boardString = new StringBuilder();

            // While loop to read all lines.
            while ((lines = reader.readLine()) != null) {
                // For loop to read all chars in the lines.
                for (int i = 0; i < lines.length(); i++) {
                    if (!(lines.startsWith("#") || lines.startsWith("x"))) {
                        // For all digits, set the number to add that many elements to the string.
                        if (Character.isDigit(lines.charAt(i))) {
                            // For single digits.
                            if (number == 0) {
                                number = Character.getNumericValue(lines.charAt(i));
                            } else {
                                // For digits that has a higher value than  9.
                                number *= 10;
                                number += Character.getNumericValue(lines.charAt(i));
                            }
                        }
                        // For all the dead cells, get the number value and add cells.
                        else if (lines.charAt(i) == 'b' || lines.charAt(i) == 'B') {
                            if (number != 0) {
                                while (number != 0) {
                                    boardString.append(".");
                                    number--;
                                }
                            } else {
                                // Or add just one if no number is present.
                                boardString.append(".");
                            }
                        }
                        // For all the live cells, get the number value and add cells.
                        else if (lines.charAt(i) == 'o' || lines.charAt(i) == 'O') {
                            if (number != 0) {
                                while (number != 0) {
                                    boardString.append("O");
                                    number--;
                                }
                            } else {
                                // Or add just one if no number is present.
                                boardString.append("O");
                            }
                        }
                        // Add a new line to the string at the RLE new lines char '$'
                        else if (lines.charAt(i) == '$') {
                            if (number != 0) {
                                while (number != 0) {
                                    boardString.append("\n");
                                    number--;
                                }
                            } else {
                                boardString.append("\n");
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
}