package Grid;

public class Grid {
    
    private char[][] grid;
    private char winnerToken;

    // board dimensions
    private static final int ROW_SIZE = 6;
    private static final int COLUMN_SIZE = 7;

    public Grid() {
        grid = new char[ROW_SIZE][COLUMN_SIZE];

        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COLUMN_SIZE; j++) {
                grid[i][j] = '.';
            }
        }
    }

    // method to display the grid
    public void displayGrid() {
        // loop through the grid to display its contents
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COLUMN_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        // display column numbers for easier gameplay
        for (int i = 1; i <= COLUMN_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    // method to add a token in the grid
    public void addToken(int column, char token) {
        // loop through all the possible rows for that column starting at the bottom
        for (int i = ROW_SIZE-1; i >= 0; i--) {
            // if reached an empty cell mark the cell
            if (grid[i][column-1] == '.') {
                grid[i][column-1] = token; // mark the cell
                break;
            }
        }
    }

    // checks whether the board is full or not (Tie)
    private boolean isFull() {
        // loop through all cells in grid
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COLUMN_SIZE; j++) {
                // if any empty cell is found
                if (grid[i][j] == '.') {
                    return false;
                }
            }
        }
        return true; // no empty cells found
    }

    // checks whether a player won the game
    private boolean isGameWon() {
        // loop through all cells
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COLUMN_SIZE; j++) {
                // if a marked cell is found
                if (grid[i][j] != '.') {
                    // check the neighbors of the cell
                    if (checkCellNeighbors(i, j, grid[i][j])) {
                        winnerToken = grid[i][j];
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // check if the requested cell is part of any winning streak
    private boolean checkCellNeighbors(int row, int column, char token) {
        // row of the cell
        return true;
    }

    // checks whether the game ended or not
    public boolean isTerminalState() {
        return isGameWon() || isFull();
    }

    // check if the requested column contains an empty cell or not
    public boolean isColumnFull(int column) {
        return grid[0][column-1] != '.'; // the top cell in the column is marked
    }

    // getters
    public char getWinnerToken() {
        return winnerToken;
    }
}
