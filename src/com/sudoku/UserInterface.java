package com.sudoku;
import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;


public class UserInterface extends JFrame {
    private int boardSize;
    private int boxSizeRow;
    private int boxSizeCol;
    private int canvasWidth;
    private int canvasHeight;
    private int boardWidth;
    private int boardHeight;

    private static final int CELL_SIZE = 40;
    public static final Color OPEN_CELL_BGCOLOR = Color.WHITE;
    public static final Color OPEN_CELL_TEXT_YES = Color.BLACK;  // RGB
    public static final Color OPEN_CELL_TEXT_NO = Color.RED;
    public static final Color CLOSED_CELL_BGCOLOR = Color.DARK_GRAY; // RGB
    public static final Color CLOSED_CELL_TEXT = Color.BLACK;
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

    private JTextField f[][]= new JTextField[9][9];
    private JPanel p[][]= new JPanel [3][3];

    private JTextField[][] tfCells;

    public Cell[][] cells;



    public UserInterface(int boardSize) {
        this.boardSize = boardSize;
        if (Math.sqrt(this.boardSize) % 1 == 0 ) {
            this.boxSizeCol = (int)Math.sqrt(this.boardSize);
            this.boxSizeRow = this.boxSizeCol;
        }
        this.boardWidth = CELL_SIZE * this.boardSize;
        this.boardHeight = CELL_SIZE * this.boardSize;
        this.canvasWidth = this.boardWidth;
        this.canvasHeight = this.boardHeight + 100;
        this.initUI();
        //this.Gui2();
        processPuzzle();
    }


    public void initUI() {
        PrintStream originalOutput = System.out;
        PrintStream uiPS = new UIPrintStream(originalOutput);
        System.setOut(uiPS);
        setTitle("Sudoku Solver");

        setSize(this.canvasWidth, this.canvasHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.tfCells = new JTextField[this.boardSize][this.boardSize];

        Container cp = getContentPane();
        cp.setLayout(new GridLayout(this.boardSize, this.boardSize));


        JInternalFrame boardFrame = new JInternalFrame();


        for (int row = 0; row < this.boardSize; ++row) {
            for (int col = 0; col < this.boardSize; ++col) {
                tfCells[row][col] = new JTextField(); // Allocate element of array
                boardFrame.add(tfCells[row][col]);// ContentPane adds JTextField
                tfCells[row][col].setText("");     // set to empty string
                tfCells[row][col].setEditable(true);
                tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
                tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
                tfCells[row][col].setFont(FONT_NUMBERS);
            }
        }

        // Set the size of the content-pane and pack all the components
        //  under this container.
        boardFrame.setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        this.getContentPane().add(boardFrame);
        //pack();



        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Handle window closing
        setTitle("Sudoku Solver");
        setVisible(true);
    }


    public void processPuzzle() {
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

    public void Gui2(){
        //super("Sudoku");

        for(int x=0; x<=8; x++){
            for(int y=0; y<=8; y++){
                f[x][y]=new JTextField(1);
            }
        }

        for(int x=0; x<=2; x++){
            for(int y=0; y<=2; y++){
                p[x][y]=new JPanel(new GridLayout(3,3));
            }
        }

        setLayout(new GridLayout(3,3,5,5));

        for(int u=0; u<=2; u++){
            for(int i=0; i<=2; i++){
                for(int x=0; x<=2; x++ ){
                    for(int y=0; y<=2; y++){
                        p[u][i].add(f[y+u*3][x+i*3]);
                    }
                }
                add(p[u][i]);
            }
        }
    }


    private class UIPrintStream extends PrintStream {
        public UIPrintStream (OutputStream out) {
            super(out, true);
        }
        @Override
        public void print(String s)
        {//do what ever you like
            super.print(s);
        }
    }
}