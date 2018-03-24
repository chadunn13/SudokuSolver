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

    //constructor for known cell
    public Cell(int box, int row, int col, int num, int boardSize, Board board) {
        this.row = row;
        this.col = col;
        this.num = num;
        this.boardSize = boardSize;
        this.board = board;
        this.box = box;
        this.potentials = new Vector();
        //this.valid_nums = []
        //for i in range(max_num):
        //this.valid_nums.append(i + 1)
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        if(this.num < 0) {
            System.out.println("ERROR, number already set to " + this.num);
        }
        else {
            this.num = num;
        }

    }

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
