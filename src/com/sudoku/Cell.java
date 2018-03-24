package com.sudoku;
import java.util.*;

public class Cell {
    int row;
    int col;
    int num;
    int boardSize;
    int box;
    public Vector potentials;
    Board board;

    //constructor
    public Cell(int box, int row, int col, int num, int boardSize, Board board) {
        this.row = row;
        this.col = col;
        this.num = num;
        this.boardSize = boardSize;
        this.board = board;
        this.box = box;
        this.potentials = new Vector();
    }

    // set cell number to input
    // prevents already solved cells from being overwritten
    public void setNum(int num) {
        if(this.num < 0) {
            System.out.println("ERROR, number already set to " + this.num);
        }
        else {
            this.num = num;
        }

    }

    // check the cell's box/row/col to ensure that its num is the only one to fit
    // TODO: add num as argument to allow checking of potentials
    // TODO: also change checking method from >1 to something more robust
    public boolean checkCell(boolean debug) {
        if (this.num == 0) {
            if (debug) {
                System.out.println("Checking empty cell");
            }
            return true;
        }
        if (this.board.checkNum("box", this.box, this.num, false) > 1) {
            if (debug) {
                System.out.println("multiple found in box");
            }
            return false;
        }
        if (this.board.checkNum("row", this.row, this.num, false) > 1) {
            if (debug) {
                System.out.println("multiple found in row");
            }
            return false;
        }
        if (this.board.checkNum("col", this.col, this.num, false) > 1) {
            if (debug) {
                System.out.println("multiple found in col");
            }
            return false;
        }
        return true;
    }
}
