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
        Board board2 = new Board(9);
        //board2.printBoard();
        board2.readFromFile("inputs/board2.csv");
        board2.printBoard();
        // purposefully create an invalid board
        board2.cells.elementAt(0).elementAt(0).setNum(5);
        board2.printBoard();
        System.out.println(board2.checkBoard(true));


    }

}
