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
        Move bestMove = minimax(getGrid(), 9, Integer.MIN_VALUE, Integer.MAX_VALUE, true, 0);
        getGrid().addToken(bestMove.getMove(), getToken()); // mark cell

        System.out.println("AI move: " + bestMove.getMove());
    }
    private Move minimax(Grid state, int depth, int alpha, int beta, boolean isMaximizingPlayer, int currentDepth) {
        // reached terminal state or intended depth
        if (state.isTerminalState() || depth == 0) {
            return evaluationFunction(state, currentDepth); // return heuristic value
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
            Move bestMove = new Move(-1, Integer.MIN_VALUE, currentDepth);

            // loop through all possible avaialbe columns
            for (int i : state.getAvailableColumns()) {
                Grid newState = new Grid(state);  // deep copy the grid
                newState.addToken(i, getToken());  // mark new grid
                // recursively find possible moves
                Move move = minimax(newState, depth-1, alpha, beta, false, currentDepth+1);

                // if new move has higher score OR same score but lower depth
                if ((move.getScore() > bestMove.getScore()) ||
                    (move.getScore() == bestMove.getScore() && move.getDepth() < bestMove.getDepth())) {
                    bestMove = new Move(i, move.getScore(), currentDepth);
                }
                
                alpha = Math.max(alpha, move.getScore()); // update alpha value
                // pruning condition
                if (beta <= alpha) {
                    break; // skip branch
                }
            }
            return bestMove;
        }

        // min player turn (opponent)
        else {
            Move bestMove = new Move(-1, Integer.MAX_VALUE, currentDepth);

            // loop through all possible avaialbe columns
            for (int i : state.getAvailableColumns()) {
                Grid newState = new Grid(state); // deep copy the grid
                newState.addToken(i, opponentToken); // mark new grid
                // recursively find possible moves
                Move move = minimax(newState, depth-1, alpha, beta, true, currentDepth+1);

                // if new move has higher score OR same score but lower depth
                if ((move.getScore() < bestMove.getScore()) ||
                    (move.getScore() == bestMove.getScore() && move.getDepth() < bestMove.getDepth())) {
                    bestMove = new Move(i, move.getScore(), currentDepth);
                }

                beta = Math.min(alpha, move.getScore()); // update beta value
                // pruning condition
                if (beta <= alpha) {
                    break; // skip branch
                }
            }
            return bestMove;
        }   
    }
    // method to evaluate game states
    private Move evaluationFunction(Grid state, int currentDepth) {
        // game is a tie
        if (state.getWinnerToken() == '\0') {
            return new Move(-1, 0, currentDepth);
        }
        // max player (us) won
        else if (state.getWinnerToken() == getToken()) {
            return new Move(-1, 1, currentDepth);
        }
        // min player won (opponent)
        else {
            return new Move(-1, -1, currentDepth);
        }
    }

    // method to choose a random column (1-7) inclusive
    private void chooseRndMove() {
        int column = ((int) (Math.random() * 7)) + 1; // choose random column
        getGrid().addToken(column, getToken()); // mark cell
    }
}
