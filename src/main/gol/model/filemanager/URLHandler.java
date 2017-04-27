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
    Dialogs dialog = new Dialogs();

    /**
     * Read and decodePlainText plaintext files from URL and load pattern into gameBoard matrix
     *
     * @param inURL String
     */
    public void readAndParse(String inURL) throws Exception {

        URL url = new URL(inURL);
        URLConnection conn = url.openConnection();
        System.out.println(inURL);


        try {
            String[] fileType = url.getFile().split("[.]");
            int itemCount = fileType.length;
            if (fileType[itemCount - 1].contains("txt") || fileType[itemCount - 1].contains("cells")) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(),charset))) {
                    Config config = new Config();
                    byte[][] matrix = new byte[config.getRows()][config.getColumns()];
                    Decoder decoder = new Decoder();
                    decoder.decodePlainText(reader, matrix);
                    theMatrix = matrix; // update global variable
                } catch (IOException ioe) {
                    System.err.println("Error reading file");
                } catch (ArrayIndexOutOfBoundsException oob) {
                    System.out.println("Error loading file. (Mismatch) Index out of bounds. ");
                    dialog.notFoundException();
                }
            } else if (fileType[itemCount - 1].contains("rle")) {
                // Write temp file to parse to decoder
                try {

                    // The rest of this code is duplicated, so this should be converted to generic codes!

                    // Create the temp file
                    FileWriter tempFile = new FileWriter("temp.gol");
                    BufferedWriter out = new BufferedWriter(tempFile);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(),charset));
                    StringBuilder boardString = new StringBuilder();
                    String lines;
                    int number = 0;
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
                    out.write(String.valueOf(boardString));
                    // Close the output stream after!
                    out.close();
                    File temp = new File("temp.gol");
                    temp.deleteOnExit();
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
                // Parse and delete the temp file
                File temp = new File("temp.gol");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(temp), charset))) {
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
                temp.delete();
            }
        } catch (Exception e) {
            dialog.urlError();
            System.err.println("Error trying to read URL");
            System.out.println(e);
        }
    }

    /**
     * @return byte[][] matrix
     */
    public byte[][] getMatrix() {
        return this.theMatrix;
    }
}
