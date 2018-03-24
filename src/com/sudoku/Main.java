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
        Board board = new Board(9);
        //board2.printBoard();
        board.readFromFile("inputs/board9.csv");
        //board.printBoard();
        // purposefully create an invalid board
        //board.cells.elementAt(0).elementAt(0).setNum(3);
        board.printBoard();
        System.out.println(board.checkBoard(false));


    }

}
