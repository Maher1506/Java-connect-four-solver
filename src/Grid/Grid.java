package Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private long zobristHash;                // current hash of the board
 
    // zobrist table     (long[row][column][players])
    private static final long[][][] ZOBRIST_TABLE = new long[6][7][2];

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
        zobristHash = 0;

        // initialize zobrist table with random values
        Random rand = new Random();
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COLUMN_SIZE; col++) {
                for (int player = 0; player < 2; player++) {
                    ZOBRIST_TABLE[row][col][player] = rand.nextLong();
                }
            }
        }
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
        int row = height[column] % 7;       // row of the inserted position
        int player = moveCounter & 1;       // determine player turn (0 or 1)
        long move = 1L << height[column];   // get the next avaialble position

        bitboards[player] ^= move;          // assign move based on player's turn

        moveHistory.push(column);           // add move to history
        moveCounter++;                      // increment number of moves made

        zobristHash ^= ZOBRIST_TABLE[row][column][player]; // update hash for state

        height[column]++; // increment height of column
    }

    // undos a move
    public void undoMove() {
        int column = moveHistory.pop();     // get last move
        int player = --moveCounter & 1;     

        height[column]--;  // decrement height for the column of the prev move

        int row = height[column] % 7;       // get row of last move  
        long move = 1L << height[column]; // get move and decrement height

        bitboards[player] ^= move;          // remove move from board

        zobristHash ^= ZOBRIST_TABLE[row][column][player];  // restore hash for state
    }

    public List<Long> generateMasks() {
        List<Long> masks = new ArrayList<>();
    
        // Horizontal masks
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col <= 3; col++) { // only 0–3
                long mask = 0L;
                for (int i = 0; i < 4; i++) {
                    mask |= 1L << ((col + i) * 7 + row);
                }
                masks.add(mask);
            }
        }
    
        // Vertical masks
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row <= 2; row++) { // only 0–2 (to fit 4 cells)
                long mask = 0L;
                for (int i = 0; i < 4; i++) {
                    mask |= 1L << (col * 7 + (row + i));
                }
                masks.add(mask);
            }
        }
    
        // Positive diagonal ("/")
        for (int col = 0; col <= 3; col++) {
            for (int row = 3; row < 6; row++) {
                long mask = 0L;
                for (int i = 0; i < 4; i++) {
                    mask |= 1L << ((col + i) * 7 + (row - i));
                }
                masks.add(mask);
            }
        }
    
        // Negative diagonal ("\\")
        for (int col = 0; col <= 3; col++) {
            for (int row = 0; row <= 2; row++) {
                long mask = 0L;
                for (int i = 0; i < 4; i++) {
                    mask |= 1L << ((col + i) * 7 + (row + i));
                }
                masks.add(mask);
            }
        }

        // visualize masks
        // for (Long mask : masks) {
        //     printBitboard(mask, 'x');
        // }

        return masks;
    }    

    // checks whether the board is full or not (Tie)
    public boolean isFull() {
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
        return checkDirection(bitboard, 1) ||  // Horizontal
               checkDirection(bitboard, 7) ||  // Vertical
               checkDirection(bitboard, 6) ||  // Diagonal /
               checkDirection(bitboard, 8);    // Diagonal \
    }
    
    private boolean checkDirection(long bitboard, int shift) {
        long bb = bitboard & (bitboard >> shift);
        return (bb & (bb >> (2 * shift))) != 0;
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
    public long getZobristHash() {
        return zobristHash;
    }
    public int getMoveCounter() {
        return moveCounter;
    }
    public Long getAIBitboard() {
        return bitboards[1];
    }
    public Long getPlayerBitboard() {
        return bitboards[0];
    }

    // FOR DEBUGGING
    public void displayBitboards() {
        System.out.println("Player 1 (X) Bitboard:");
        printBitboard(bitboards[0], 'x');
        
        System.out.println("\nPlayer 2 (O) Bitboard:");
        printBitboard(bitboards[1], 'o');
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
