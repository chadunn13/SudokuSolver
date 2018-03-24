package com.sudoku;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Board {
    private int boardSize;
    private int squaresPer;
    public Vector<Vector<Cell>> cells = new Vector();
    public Vector valid_nums = new Vector();

    public Board(int boardSize) {
        this.boardSize = boardSize;
        int num_cells = this.boardSize * this.boardSize;
        for (int i = 0; i < this.boardSize; i++) {
            this.valid_nums.addElement(i + 1);
            //System.out.println((int)(this.valid_nums.elementAt(i)));
        }
        //checkBoard(true);
        this.initializeBoard(boardSize);
    }

    // input: checkType ("box", "row", or "col"), x (corresponding number of box/row/col), num (number to check for), update (should remove from potentials?)
    // output: number of instances of num found
    public int checkNum(String checkType, int x, int num, boolean update) {
        int i;
        int j;
        int total = 0;
        for (i = 0; i < boardSize; i++) {
            for (j = 0; j < cells.size(); j++) {
                if (checkType == "box") {
                    if (cells.elementAt(i).elementAt(j).box == x) {
                        if (cells.elementAt(i).elementAt(j).potentials.contains(num)) {
                            total++;
                            if (update) {
                                cells.elementAt(i).elementAt(j).potentials.remove(num);
                            }
                        }
                    }
                }
                if (checkType == "row") {
                    if (cells.elementAt(i).elementAt(j).row == x) {
                        if (cells.elementAt(i).elementAt(j).potentials.contains(num)) {
                            total++;
                        }
                        if (update) {
                            cells.elementAt(i).elementAt(j).potentials.remove(num);
                        }
                    }
                }
                if (checkType == "col") {
                    if (cells.elementAt(i).elementAt(j).col == x) {
                        if (cells.elementAt(i).elementAt(j).potentials.contains(num)) {
                            total++;
                        }
                        if (update) {
                            cells.elementAt(i).elementAt(j).potentials.remove(num);
                        }
                    }
                }
            }
        }
        return total;
    }

    public void initializeBoard(int num_cells) {
        for (int i = 0; i < this.boardSize; i++) {
            Vector row = new Vector();
            for (int j = 0; j < this.boardSize; j++) {
                int box = this.calculateBox(i, j);
                row.addElement(new Cell(box, i, j, 0, this.boardSize, this));
            }
            this.cells.add(row);
        }
    }

    public void readFromFile(String filename) {
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
                    this.cells.elementAt(row).elementAt(i).setNum(Integer.parseInt(col[i]));
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

    public boolean isValidNum(int num) {
        for (int i = 0; i < this.boardSize; i++) {
            if (num == (int)(this.valid_nums.elementAt(i))) {
                return true;
            }
        }
        return false;
    }

    public int calculateBox(int row, int col) {
        int boxesPerRow = (int)Math.sqrt(this.boardSize);
        int boxRow = row / boxesPerRow;
        int boxCol = col / boxesPerRow;
        return boxRow * boxesPerRow + boxCol;
    }

    public boolean checkBoard(boolean debug) {
        /*
        valid_nums = []
        for i in range(len(board)):
            valid_nums.append(i+1)
        if debug:
            print("valid numbers = ", valid_nums)
        for i in range(len(board)):
            row = set()
            column = set()
            for j in range(len(board[i])):
                if board[i][j].num in valid_nums:
                    row.add(board[i][j])
                if board[j][i].num in valid_nums:
                    column.add(board[j][i].num)
            if debug:
                print("row=\t", row)
                print("col=\t", column)
            if len(row) != len(board) or len(column) != len(board):
                return False
        num_squares = len(board)
        squares_per_row = int(math.sqrt(num_squares))
        for i in range(0, num_squares, squares_per_row):
            for j in range(0, num_squares, squares_per_row):
                current_square = set()
                for x in range(i, i+squares_per_row):
                    for y in range(j, j+squares_per_row):
                        if board[x][y].num in valid_nums:
                            current_square.add(board[x][y].num)
                if debug:
                    print("square=\t", current_square)
                if len(current_square) != len(board):
                    return False
        return True
         */
        int i = 0;
        int j = 0;
        if(debug) {
            System.out.println("valid numbers: " + this.valid_nums.toString());
        }

        boolean validBoard = true;

        for (i = 0; i < this.boardSize; i++) {
            for (j = 0; j < this.boardSize; j++) {
                if (!this.cells.elementAt(i).elementAt(j).checkCell(debug)) {
                    validBoard = false;
                }
            }
        }
        return validBoard;
    }


    public void printBoard() {
        int boxesPerRow = (int)Math.sqrt(this.boardSize);
        System.out.println(boxesPerRow);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.printf("%d", this.cells.elementAt(i).elementAt(j).num);
                if ((j + 1) % boxesPerRow == 0) {
                    System.out.printf(" | ");
                }
            }
            System.out.println();
            if ((i + 1) % boxesPerRow == 0) {
                System.out.printf("------------------------------------------\n");
            }
        }
    }



}
