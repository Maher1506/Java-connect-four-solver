package Player;

import GameLogic.Move;
import Grid.Grid;

public class AIPlayer extends Player{
    
    public AIPlayer(String name, char token, Grid grid) {
        super(name, token, grid);
    }

    @Override
    public void handleTurn() {
        System.out.println("Player " + getName() + "'s turn: (" + getToken() + ")");

        chooseRndMove();
        getGrid().displayGrid(); // display grid after action

        System.out.println("AI made its move!");
    }

    // method to choose the best move using minimax and alpha-beta prunning
    private void chooseUnbeatableMove() {
        Move bestMove = minimax(getGrid(), true, 0);
        getGrid().addToken(bestMove.getMove(), getToken()); // mark cell
    }
    private Move minimax(Grid state, boolean isMaximizingPlayer, int depth) {
        // reached terminal state
        if (state.isTerminalState()) {
            // game is a tie
            if (state.getWinnerToken() == '\0') {
                return new Move(-1, 0, depth);
            }
            // max player (us) won
            else if (state.getWinnerToken() == getToken()) {
                return new Move(-1, 1, depth);
            }
            // min player won (opponent)
            else {
                return new Move(-1, -1, depth);
            }
        }

        // max player turn (us)
        // through all possible collumns that has space for a move
        for (int i = 1; i < 8; i++) {
            // check if column is available
            if (!getGrid().isColumnFull(i)) {
                // move the set last move in add token
            }
        }
    }

    // method to choose a random column (1-7) inclusive
    private void chooseRndMove() {
        int column = ((int) (Math.random() * 7)) + 1; // choose random column
        getGrid().addToken(column, getToken()); // mark cell
    }
}
