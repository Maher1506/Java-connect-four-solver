package Grid;

import java.util.Stack;

public class Grid {

    /*
          6 13 20 27 34 41 48   55 62     Additional row
        +---------------------+ 
        | 5 12 19 26 33 40 47 | 54 61     top row
        | 4 11 18 25 32 39 46 | 53 60
        | 3 10 17 24 31 38 45 | 52 59
        | 2  9 16 23 30 37 44 | 51 58
        | 1  8 15 22 29 36 43 | 50 57
        | 0  7 14 21 28 35 42 | 49 56 63  bottom row
        +---------------------+
     */
    
    private long[] bitboards;                // bitboards[0] = 'x', bitboards[1] = 'o'
    private Stack<Integer> moveHistory;      // stack of all prevous moves
    private int[] height;                    // represents height of board at any given time
    private int moveCounter;                 // number of made moves until now
    private char winnerToken;                // determines winner of the grid

    // player tokens
    private static final char PLAYER_1_TOKEN = 'x';
    private static final char PLAYER_2_TOKEN = 'o';

    // board dimensions
    private static final int ROW_SIZE = 6;
    private static final int COLUMN_SIZE = 7;

     // ANSI color codes
     public static final String RESET = "\u001B[0m"; 
     public static final String RED = "\u001B[31m";  
     public static final String YELLOW = "\u001B[33m";   

    // default constructor
    public Grid() {
        bitboards = new long[2];
        moveHistory = new Stack<>();
        height = new int[]{0, 7, 14, 21, 28, 35, 42};
        moveCounter = 0;
    }

    // method to display the grid ()
    public void displayGrid() {
        // loop through the grid to display its contents
        for (int row = ROW_SIZE-1; row >= 0; row--) {
            System.out.print("| ");
            for (int col = 0; col < COLUMN_SIZE; col++) {
                int bitPosition = col * COLUMN_SIZE + row;

                
                // player1's token
                if ((bitboards[0] & (1L << bitPosition)) != 0) {
                    System.out.print(RED + PLAYER_1_TOKEN + RESET + " | ");
                } 
                // player2's token
                else if ((bitboards[1] & (1L << bitPosition)) != 0) {
                    System.out.print(YELLOW + PLAYER_2_TOKEN + RESET + " | ");
                }
                // cell is empty
                else {
                    System.out.print(". | ");
                }
            }
            System.out.println();
        }

        // display column numbers for easier gameplay
        for (int i = 0; i < COLUMN_SIZE; i++) {
            System.out.print("  " + i + " ");
        }
        System.out.println();

        //displayBitboards();
    }

    // method to add a token in the grid
    public void makeMove(int column) {
        long move = 1L << height[column]++; // get move and increment height
        bitboards[moveCounter & 1] ^= move; // assign move based on player's turn
        moveHistory.push(column);           // add move to history
        moveCounter++;                      // increment number of moves made
    }

    // undos a move
    public void undoMove() {
        //winnerToken = prevWinnerToken;    // return to prevous winner

        int column = moveHistory.pop();       // get last move
        long move = 1L << --height[column];   // get move and decrement height
        bitboards[--moveCounter & 1] ^= move; // assign move based on player's turn and decrement counter
    }

    // checks whether the board is full or not (Tie)
    private boolean isFull() {
        long fullMask = (1L << 5) | (1L << 12) | (1L << 19) | 
                        (1L << 26) | (1L << 33) | (1L << 40) | (1L << 47);
            
        return ((bitboards[0] | bitboards[1]) & fullMask) == fullMask;
    }

    // checks whether a player won the game or not
    private boolean isGameWon() {
        if (isWin(bitboards[0])) {
            winnerToken = PLAYER_1_TOKEN;
            return true;
        } else if (isWin(bitboards[1])) {
            winnerToken = PLAYER_2_TOKEN;
            return true;
        } else {
            return false;
        }
    }

    // check if a bitboard is a winning one
    private boolean isWin(long bitboard) {
        // horizontal, vertical, diagonal /, diagonal \
        int[] directions = {1, 7, 6, 8};
        long bb;
        for (int dir : directions) {
            bb = bitboard & (bitboard >> dir);
            if ((bb & (bb >> (2 * dir))) != 0 ) return true;
        }
        return false;
    }

    // checks whether the game ended or not
    public boolean isTerminalState() {
        return isGameWon() || isFull();
    }

    // check if the requested column contains an empty cell or not
    public boolean isColumnFull(int column) {
        long TOP = 0b1000000_1000000_1000000_1000000_1000000_1000000_1000000L;
        if ((TOP & (1L << height[column])) == 0) { // the top cell in the column is marked
            return false;
        }
        return true;  
    }

    // getters
    public char getWinnerToken() {
        return winnerToken;
    }

    // FOR DEBUGGING
    public void displayBitboards() {
        System.out.println("Player 1 (X) Bitboard:");
        printBitboard(bitboards[0], 'X');
        
        System.out.println("\nPlayer 2 (O) Bitboard:");
        printBitboard(bitboards[1], 'O');
    }
    
    private void printBitboard(long bitboard, char token) {
        for (int row = ROW_SIZE - 1; row >= 0; row--) { // Start from top row
            for (int col = 0; col < COLUMN_SIZE; col++) {
                int bitPosition = col * COLUMN_SIZE + row; // Correct column-major order
    
                if ((bitboard & (1L << bitPosition)) != 0) {
                    System.out.print(token + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
