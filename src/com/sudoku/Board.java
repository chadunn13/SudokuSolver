package com.sudoku;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Board {
    // length of board side
    private int boardSize;
    // length of each box side
    private int squaresPer;

    // vector of row vectors, called with cells[row_num][col_num]
    public Vector<Vector<Cell>> cells = new Vector();

    // list of valid numbers that can be input, populated in constructor
    public Vector valid_nums = new Vector();

    // constructor
    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.squaresPer = (int)Math.sqrt(this.boardSize);
        for (int i = 0; i < this.boardSize; i++) {
            this.valid_nums.addElement(i + 1);
        }
        this.initializeBoard();
    }

    public boolean startSolver(boolean debug) {
        this.addInitialPotentials(debug);

        int i;
        int j;
        int k;

        while (!this.checkBoard(false)) {
            boolean updated = false;
            for (i = 0; i < this.boardSize; i++) {
                for (j = 0; j < this.boardSize; j++) {
                    if (this.cells.elementAt(i).elementAt(j).num == 0) {
                        // check if potential count == 1, if so only possible solution so set it
                        if (this.cells.elementAt(i).elementAt(j).potentials.size() == 1) {
                            this.cells.elementAt(i).elementAt(j).setNum((int)this.cells.elementAt(i).elementAt(j).potentials.firstElement(), true, debug);
                            updated = true;
                            continue;
                        }
                        // check if only potential of its kind in its row/box/col
                        for (k = 0; k < this.cells.elementAt(i).elementAt(j).potentials.size(); k++) {
                            int num = (int)this.cells.elementAt(i).elementAt(j).potentials.elementAt(k);
                            if (this.cells.elementAt(i).elementAt(j).checkCellPotentials(num, debug)) {
                                this.cells.elementAt(i).elementAt(j).setNum(num, true, debug);
                                updated = true;
                                break;
                            }
                        }
                    }
                }
            }
            for (i = 0; i < this.boardSize; i++) {
                for (k = 0; k < this.boardSize; k++) {
                    int numIndexes = this.checkBoxSingleRowCol("row", i, k, debug);
                    if (numIndexes != -1) {
                        this.updateOtherBoxes("row", i, numIndexes, k, debug);
                        updated = true;
                    }
                    numIndexes = this.checkBoxSingleRowCol("col", i, k, debug);
                    if (numIndexes != -1) {
                        this.updateOtherBoxes("col", i, numIndexes, k, debug);
                        updated = true;
                    }
                }
            }
            if (!updated) {
                System.out.println("cannot update any more");
                return this.checkBoard(false);
            }
        }
        return true;
    }

    public int checkBoxSingleRowCol(String checkType, int box, int num, boolean debug) {
        int i;
        int j;
        int total = 0;
        int index = -1;
        for (i = 0; i < this.boardSize; i++) {
            for (j = 0; j < this.boardSize; j++) {
                if (this.cells.elementAt(i).elementAt(j).box == box) {
                    if (this.cells.elementAt(i).elementAt(j).potentials.contains(num)) {
                        //total++;
                        if (checkType == "row") {
                            if (total == 0) {
                                total++;
                                index = this.cells.elementAt(i).elementAt(j).row;
                            }
                            if (total == 1) {
                                if (this.cells.elementAt(i).elementAt(j).row == index) {
                                    continue;
                                }
                                else {
                                    return -1;
                                }
                            }
                        }
                        if (checkType == "col") {
                            if (total == 0) {
                                total++;
                                index = this.cells.elementAt(i).elementAt(j).col;
                            }
                            if (total == 1) {
                                if (this.cells.elementAt(i).elementAt(j).col == index) {
                                    continue;
                                }
                                else {
                                    return -1;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (total == 1) {
            return index;
        }
        else {
            return -1;
        }
    }

    public void updateOtherBoxes(String checkType, int currentBox, int index, int num, boolean debug) {
        int i;
        int j;
        for (i = 0; i < this.cells.size(); i++) {
            for (j = 0; j < this.cells.elementAt(i).size(); j++) {
                if (this.cells.elementAt(i).elementAt(j).box == currentBox) {
                    continue;
                }
                if (checkType == "row") {
                    if (this.cells.elementAt(i).elementAt(j).row == index) {
                        if (this.cells.elementAt(i).elementAt(j).potentials.contains(num)) {
                            this.cells.elementAt(i).elementAt(j).potentials.removeElement(num);
                        }
                    }

                }
                if (checkType == "col") {
                    if (this.cells.elementAt(i).elementAt(j).col == index) {
                        if (this.cells.elementAt(i).elementAt(j).potentials.contains(num)) {
                            this.cells.elementAt(i).elementAt(j).potentials.removeElement(num);
                        }
                    }

                }

            }
        }
    }

    // input: checkType ("box", "row", or "col"), x (corresponding number of box/row/col), num (number to check for), update (should remove from potentials?)
    // output: number of instances of num found
    public int checkNum(String checkType, int x, int num, boolean update, boolean debug) {
        int i;
        int j;
        int total = 0;
        for (i = 0; i < this.cells.size(); i++) {
            for (j = 0; j < this.cells.elementAt(i).size(); j++) {
                if (checkType == "box") {
                    if (this.cells.elementAt(i).elementAt(j).box == x) {
                        if (debug) {
                            System.out.printf("%s %d %d, %d %d, %d\n", checkType, x, num, i, j, total);
                        }
                        if (this.cells.elementAt(i).elementAt(j).num == num) {
                            total++;
                        }
                        if (update) {
                            if (this.cells.elementAt(i).elementAt(j).potentials.contains(num)) {
                                this.cells.elementAt(i).elementAt(j).potentials.removeElement(num);
                            }
                        }
                    }
                }
                if (checkType == "row") {
                    if (this.cells.elementAt(i).elementAt(j).row == x) {
                        if (this.cells.elementAt(i).elementAt(j).num == num) {
                            if (debug) {
                                System.out.printf("%s %d %d, %d %d, %d\n", checkType, x, num, i, j, total);
                            }
                            if (this.cells.elementAt(i).elementAt(j).num == num) {
                                total++;
                            }
                        }
                        if (update) {
                            if (this.cells.elementAt(i).elementAt(j).potentials.contains(num)) {
                                this.cells.elementAt(i).elementAt(j).potentials.removeElement(num);
                            }
                        }
                    }
                }
                if (checkType == "col") {
                    if (this.cells.elementAt(i).elementAt(j).col == x) {
                        if (debug) {
                            System.out.printf("%s %d %d, %d %d, %d\n", checkType, x, num, i, j, total);
                        }
                        if (this.cells.elementAt(i).elementAt(j).num == num) {
                            total++;
                        }
                        if (update) {
                            if (this.cells.elementAt(i).elementAt(j).potentials.contains(num)) {
                                this.cells.elementAt(i).elementAt(j).potentials.removeElement(num);
                            }
                        }
                    }
                }
            }
        }

        return total;
    }

    public int checkPotentials(String checkType, int x, int num, boolean debug) {
        int i;
        int j;
        int total = 0;
        for (i = 0; i < this.cells.size(); i++) {
            for (j = 0; j < this.cells.elementAt(i).size(); j++) {
                if (checkType == "box") {
                    if (this.cells.elementAt(i).elementAt(j).box == x) {
                        if (debug) {
                            System.out.printf("%s %d %d, %d %d, %d\n", checkType, x, num, i, j, total);
                        }
                        if (this.cells.elementAt(i).elementAt(j).potentials.contains(num)) {
                            total++;
                        }
                    }
                }
                if (checkType == "row") {
                    if (this.cells.elementAt(i).elementAt(j).row == x) {
                        if (this.cells.elementAt(i).elementAt(j).num == num) {
                            if (debug) {
                                System.out.printf("%s %d %d, %d %d, %d\n", checkType, x, num, i, j, total);
                            }
                            if (this.cells.elementAt(i).elementAt(j).potentials.contains(num)) {
                                total++;
                            }
                        }
                    }
                }
                if (checkType == "col") {
                    if (this.cells.elementAt(i).elementAt(j).col == x) {
                        if (debug) {
                            System.out.printf("%s %d %d, %d %d, %d\n", checkType, x, num, i, j, total);
                        }
                        if (this.cells.elementAt(i).elementAt(j).potentials.contains(num)) {
                            total++;
                        }
                    }
                }
            }
        }

        return total;
    }

    // creates an empty board (all cells = 0) given the input boardSize
    public void initializeBoard() {
        for (int i = 0; i < this.boardSize; i++) {
            Vector row = new Vector();
            for (int j = 0; j < this.boardSize; j++) {
                int box = this.calculateBox(i, j);
                row.addElement(new Cell(box, i, j, 0, this.boardSize, this));
            }
            this.cells.add(row);
        }
    }

    // called externally, takes in a CSV filepath as a string and sets the board data structure to reflect that input
    // TODO: need to add error checking
    public void readFromFile(String filename, boolean debug) {
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(filename));
            int row = 0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] col = line.split(cvsSplitBy);
                for (int i = 0; i < col.length; i++) {
                    if (isValidNum(Integer.parseInt(col[i]))) {
                        this.cells.elementAt(row).elementAt(i).setNum(Integer.parseInt(col[i]), false, debug);
                    }
                    else {
                        this.cells.elementAt(row).elementAt(i).setNum(0, false, debug);
                    }
                }
                row++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // adds potentials for all unsolved cells at start of runtime
    public void addInitialPotentials(boolean debug) {
        int i;
        int j;
        for (i = 0; i < this.boardSize; i++) {
            for (j = 0; j < this.boardSize; j++) {
                if (this.cells.elementAt(i).elementAt(j).num == 0) {
                    for (int k = 1; k <= this.boardSize; k++) {
                        if (this.cells.elementAt(i).elementAt(j).addPotential(k, false)) {
                            if (debug) {
                                System.out.println("added " + k + " to " + i + "," + j);
                            }
                        }
                    }
                }
            }
        }
    }

    // checks if a number is valid
    // pretty useless as of now, will be helpful for error checking the board read in from file and if/when a proper UI is added
    public boolean isValidNum(int num) {
        for (int i = 0; i < this.boardSize; i++) {
            if (num == (int)(this.valid_nums.elementAt(i))) {
                return true;
            }
        }
        return false;
    }

    // returns the box index based on the row and column of the cell that calls it
    public int calculateBox(int row, int col) {
        int boxRow = row / this.squaresPer;
        int boxCol = col / this.squaresPer;
        return boxRow * this.squaresPer + boxCol;
    }

    // verifies that the board is valid
    public boolean checkBoard(boolean debug) {
        int i;
        int j;
        if (debug) {
            System.out.println("valid numbers: " + this.valid_nums.toString());
        }

        boolean validBoard = true;

        for (i = 0; i < this.boardSize; i++) {
            for (j = 0; j < this.boardSize; j++) {
                if (!this.cells.elementAt(i).elementAt(j).checkCell(debug)) {
                    validBoard = false;
                    // if error found and no need to print out every cell check, immediately return false
                    if (!debug && !validBoard) {
                        return validBoard;
                    }
                }
                if (this.cells.elementAt(i).elementAt(j).num == 0) {
                    validBoard = false;
                }
            }
        }
        return validBoard;
    }


    // prints out the board in a nice format with separate boxes
    public void printBoard() {
        String temp = new String(new char[boardSize * 2 + squaresPer * 2 + 1]).replace("\0", "-");
        System.out.printf("%s\n", temp);
        for (int i = 0; i < boardSize; i++) {
            System.out.printf("| ");
            for (int j = 0; j < boardSize; j++) {
                System.out.printf("%d ", this.cells.elementAt(i).elementAt(j).num);
                if ((j + 1) % this.squaresPer == 0 && (j + 1) != this.boardSize) {
                    System.out.printf("| ");
                }
            }
            System.out.println("|");
            if ((i + 1) % this.squaresPer == 0) {
                System.out.printf("%s\n", temp);
            }
        }
    }



}
