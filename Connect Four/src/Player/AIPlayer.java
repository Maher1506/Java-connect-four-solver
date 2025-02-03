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

        //chooseRndMove();
        chooseUnbeatableMove();
        getGrid().displayGrid(); // display grid after action

        System.out.println("AI made its move!");
    }

    // method to choose the best move using minimax and alpha-beta prunning
    private void chooseUnbeatableMove() {
        Move bestMove = minimax(getGrid(), true, 0);
        getGrid().addToken(bestMove.getMove(), getToken()); // mark cell
        System.out.println("AI move: " + bestMove.getMove());
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

        // get the opponnent's token
        char opponentToken;
        if (getToken() == 'x') {
            opponentToken = 'o';
        } else {
            opponentToken = 'x';
        }

        // max player turn (us)
        if (isMaximizingPlayer) {
            Move bestMove = new Move(-1, Integer.MIN_VALUE, depth);

            // loop through all possible avaialbe columns
            for (int i : state.getAvailableColumns()) {
                Grid newState = new Grid(state);  // deep copy the grid
                newState.addToken(i, getToken());  // mark new grid
                // recursively find possible moves
                Move move = minimax(newState, false, depth+1);

                // if new move has higher score OR same score but lower depth
                if ((move.getScore() > bestMove.getScore()) ||
                    (move.getScore() == bestMove.getScore() && move.getDepth() < bestMove.getDepth())) {
                    bestMove = new Move(i, move.getScore(), depth);
                }
            }
            return bestMove;
        }

        // min player turn (opponent)
        else {
            Move bestMove = new Move(-1, Integer.MAX_VALUE, depth);

            // loop through all possible avaialbe columns
            for (int i : state.getAvailableColumns()) {
                Grid newState = new Grid(state); // deep copy the grid
                newState.addToken(i, opponentToken); // mark new grid
                // recursively find possible moves
                Move move = minimax(newState, true, depth+1);

                // if new move has higher score OR same score but lower depth
                if ((move.getScore() < bestMove.getScore()) ||
                    (move.getScore() == bestMove.getScore() && move.getDepth() < bestMove.getDepth())) {
                    bestMove = new Move(i, move.getScore(), depth);
                }
            }
            return bestMove;
        }   
    }

    // method to choose a random column (1-7) inclusive
    private void chooseRndMove() {
        int column = ((int) (Math.random() * 7)) + 1; // choose random column
        getGrid().addToken(column, getToken()); // mark cell
    }
}
