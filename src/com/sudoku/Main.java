package com.sudoku;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        int boardSize = 9;




        EventQueue.invokeLater(() -> {
            UserInterface ui = new UserInterface(boardSize);
            ui.setVisible(true);
        });

        /* for reading in from file

        */
    }
}
