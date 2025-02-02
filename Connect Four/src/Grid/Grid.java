package Grid;

public class Grid {
    
    private char[][] grid;

    // board size
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
        // loop through all the possible rows for that column starting at the top
        for (int i = 0; i < ROW_SIZE; i++) {
            // if reached a marked cell mark the cell above it (if not out of bounds)
            if (grid[i][column-1] != '.') {
                // check if above cell is in bounds
                if (i-1 >= 0) {  
                    // mark the cell above it
                    grid[i-1][column-1] = token;
                }
                break;
            }
            // if reached the end of the grid mark that cell
            if (i == ROW_SIZE-1) {
                // mark the most bottom cell
                grid[i][column-1] = token;
                break;
            }
        }
    }

    public boolean isColumnFull(int column) {
        return grid[0][column-1] != '.'; // the top cell in the column is marked
    }
}
