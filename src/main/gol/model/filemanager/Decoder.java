package main.gol.model.filemanager;

import main.gol.controller.util.Dialogs;

import java.io.BufferedReader;

public class Decoder {

    public byte[][] decodePlainText(BufferedReader reader, byte[][] board) throws Exception {

        int y = 0;
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("!")) {
                    y++;
                    for (int x = 0; x < line.length(); x++) {
                        char dead = '.';
                        if (line.charAt(x) == dead) {
                            board[y + 25][x + 25] = 0; // Push cell index position
                        }
                        char alive = 'O';
                        if (line.charAt(x) == alive) {
                            board[y + 25][x + 25] = 1; // Push cell index position
                        }
                        if (line.charAt(x) == ' ') {
                            y++;
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException oob) {
            Dialogs dialog = new Dialogs();
            dialog.oops();
            System.err.println("ArrayIndex out of bounds!");
        }
        return board;
    }
}
