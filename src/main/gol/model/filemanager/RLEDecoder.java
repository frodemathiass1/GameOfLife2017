package main.gol.model.filemanager;

import java.io.*;

/**
 * RLEDecoder class reads RLE files, and creates a temp file, that is parsed to the main decoder.
 * The temp file is deleted after use, as you can see in the FileHandler class when RLE is selected.
 *
 * We put so much work into the Decoder class, so this was the best way for us to decode RLE files,
 * given the short time had left to finish the project.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 1.0
 */


public class RLEDecoder {

    /**
     * Read and decode RLE files into temp plaintext files for the main decoder to handle
     *
     * @param theFile File
     */
    public RLEDecoder(File theFile) throws Exception {

        // Variables
        String lines;
        int number = 0;

        // Read the parsed file
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(theFile)));
        StringBuilder boardString = new StringBuilder();

        // While loop to read all lines
        while ((lines = reader.readLine()) != null) {
            for (int i = 0; i < lines.length(); i++) {
                if (!(lines.startsWith("#") || lines.startsWith("x"))) {
                    // For all digits, set the number
                    if (Character.isDigit(lines.charAt(i))) {
                        if (number == 0) {
                            number = Character.getNumericValue(lines.charAt(i));
                        } else {
                            number *= 10;
                            number += Character.getNumericValue(lines.charAt(i));
                        }
                    }
                    // For all the dead cells, get number and add cells
                    else if (lines.charAt(i) == 'b' || lines.charAt(i) == 'B') {
                        if (number != 0) {
                            while (number != 0) {
                                boardString.append(".");
                                number--;
                            }
                        } else {
                            boardString.append(".");
                        }
                    }
                    // For all the live cells, get number and add cells
                    else if (lines.charAt(i) == 'o' || lines.charAt(i) == 'O') {
                        if (number != 0) {
                            while (number != 0) {
                                boardString.append("O");
                                number--;
                            }
                        } else {
                            boardString.append("O");
                        }
                    }
                    // Add new line at the RLE char for new lines ($)
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
        // Write temp file to parse to decoder
        try {
            // Create the temp file
            FileWriter tempFile = new FileWriter("temp.gol");
            BufferedWriter out = new BufferedWriter(tempFile);
            out.write(String.valueOf(boardString));
            // Close the output stream after!
            out.close();
            File temp = new File("temp.gol");
            temp.deleteOnExit();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

