package com.sudoku;

public class Main {

    public static boolean checker(Board board) {
        return true;
    }

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        long startTime = System.nanoTime();
        boolean debug = false;

        Board board = new Board(16);
        board.readFromFile("inputs/16_extreme.csv", debug);

        System.out.println("Starting Board:");
        board.printBoard();

        board.startSolver(debug);

        System.out.println("Finished Board:");
        board.printBoard();

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Total running time: " + totalTime / 1_000_000_000.0 + " seconds");
    }
}
