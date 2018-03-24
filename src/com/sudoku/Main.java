package com.sudoku;

public class Main {

    public static boolean checker(Board board) {
        return true;
    }

    public static void main(String[] args) {
        //Board board1 = new Board(9, 9);
        //test(board1);
        Board board2 = new Board(9);
        //board2.printBoard();
        board2.readFromFile("inputs/board2.csv");
        board2.printBoard();
        board2.cells.elementAt(0).elementAt(0).setNum(5);
        board2.printBoard();
        //test(board2);
        System.out.println(board2.checkBoard(true));
    }

    public static void test(Board board) {
        System.out.println(board.isValidNum(5));
        System.out.println(board.isValidNum(10));
        System.out.println(board.isValidNum(1));
        System.out.println(board.isValidNum(9));
        System.out.println(board.isValidNum(0));
        System.out.println(board.isValidNum(4));


    }

}
