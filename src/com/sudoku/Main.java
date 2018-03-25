package com.sudoku;

public class Main {

    public static boolean checker(Board board) {
        return true;
    }

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        //Board board1 = new Board(9, 9);
        //test(board1);
        boolean debug = false;
        Board board = new Board(9);
        //board2.printBoard();
        board.readFromFile("inputs/9_easy.csv", debug);
        System.out.println("Starting Board:");
        board.printBoard();
        board.startSolver(debug);
        //board.printBoard();
        // purposefully create an invalid board
        //board.cells.elementAt(0).elementAt(0).setNum(3);
        System.out.println("Finished Board:");
        board.printBoard();
        //System.out.println(board.checkBoard(debug));


    }

}
