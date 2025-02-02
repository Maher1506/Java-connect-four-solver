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
        grid[3][2] = 'o';
        grid[3][1] = 'x';
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
}
