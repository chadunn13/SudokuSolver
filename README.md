# SudokuSolver

STILL A WIP!

Done just for fun/learning, there are more efficient solutions out there (though I didn't look through the exact code as I wanted to start this from scratch without any preconceived notions of the best way to do it)

A Java program to read in unsolved Sudoku puzzles of any square board size (i.e. 4, 9, 16...) and solve it using brute-force techniques.

Reads in unsolved boards (or solved, with the ability to check for validity) as CSV files and prints out the completed board.  Unsolved cells should be initially set to 0

Basic algorithm adds the "potential" numbers to every non-solved cell at the beginning of the runtime, then continues iterating through the board to check for any of the following conditions to satisfy a solution to each cell:
  - If it is the only potential for that cell, it must be the only solution
  - If a cell has a number as a potential that is the only of its kind in that particular row/column/box, it must be the solution
  - If a number in a box can only fit into one column/row, the cells in other boxes in that corresponding row/column are updated to remove that number as a potential

Once a cell is solved, the "potentials" of each other cell in that row, column, or box are updated to remove that solved number, and the iteration continues with this new knowledge until every cell is solved, then does one final check for validity on the entire board.


Future plans (after finalizing the algorithm above:
  - better UI
  - optimizing the solving algorithm
  - extension to non-square even board sizes (8, 10, 12, etc)
  
