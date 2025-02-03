package Grid;

public class Grid {
    
    private char[][] grid;
    private char winnerToken;
    private int lastMove = 1; // stores the column of the last move made on the grid

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

    // checks whether a player won the game by checking the neighbors
    // of the last inserted token (the player can only win from their last move)
    private boolean isGameWon() {
        // find the top most token in the column of the last move
        for (int i = 0; i < ROW_SIZE; i++) {
            // found the last move
            if (grid[i][lastMove-1] != '.') {
                // check if the last move results in a winning condition
                if (checkCellNeighbors(i, lastMove-1, grid[i][lastMove-1])) {
                    winnerToken = grid[i][lastMove-1];
                    return true;
                }
                break;
            }
        }
        return false;
    }

    // check if the requested cell is part of any winning streak
    private boolean checkCellNeighbors(int row, int column, char token) {
        // counts the number of consecutive same tokens found
        int streakCounter = 1; // accounts for the token itself

        return checkHorizontal(row, column, token, streakCounter) ||
               checkVertical(row, column, token, streakCounter) ||
               checkRightDiagonal(row, column, token, streakCounter);
    }

    // checks if the cell is part of a horizontal winning streak
    private boolean checkHorizontal(int row, int column, char token, int streakCounter) {
        int i; // index

        // move left until a different token is found or the reached the left end of the board
        i = column-1; // start at the cell left to the current cell
        while (i >= 0 && grid[row][i] == token) {
            streakCounter++; // same token found
            i--; // move left
        }

        // check if reached a winning streak before checking the right side
        if (streakCounter >= 4) {
            return true;
        }

        // check for the right side of the cell
        i = column+1; // start at the cell right to the current cell
        while (i < COLUMN_SIZE && grid[row][i] == token) {
            streakCounter++;
            i++; // move right
        }

        // check if reached a winning streak
        if (streakCounter >= 4) {
            return true;
        } else {
            return false;
        }
    }

    // checks if the cell is part of a vertical winning streak
    private boolean checkVertical(int row, int column, char token, int streakCounter) {
        int i; // index

        // noo need to check upwards since it's impossible to place a token and have
        // other tokens above it

        // check downwards until a different token is found or the end of the board
        i = row + 1; // start at the immediate lower cell
        while (i < ROW_SIZE && grid[i][column] == token) {
            streakCounter++;
            i++; // move down
        }

        // check if a winning streak is found
        if (streakCounter >= 4) {
            return true;
        } else {
            return false;
        }
    }

    // cehcks all the diagonals in this form '\'
    private boolean checkRightDiagonal(int row, int column, char token, int streakCounter) {
        int i; // row index
        int j; // column index

        // check the top left part until a different token or end of board reached
        // start at the immediate top left cell
        i = row - 1;
        j = column - 1;
        while ((i >= 0 && j >= 0) && grid[i][j] == token) {
            streakCounter++;
            // move up left
            i--;
            j--;
        }

        // check if streak found before checking the bottom right part
        if (streakCounter >= 4) {
            return true;
        }

        // check the bottom right part
        // start at the immediate bottom right cell
        i = row + 1;
        j = column + 1;
        while ((i < ROW_SIZE && j < COLUMN_SIZE) && grid[i][j] == token) {
            streakCounter++;
            // move down right
            i++;
            j++;
        }

        // check if a streak is found
        if (streakCounter >= 4) {
            return true;
        } else {
            return false;
        }
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
    
    // setters
    public void setLastMove(int lastMove) {
        this.lastMove = lastMove;
    }
}
