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
        chooseOptimalMove();
        getGrid().displayGrid(); // display grid after action

        System.out.println("AI made its move!");
    }

    // method to choose the best move using minimax and alpha-beta prunning
    private void chooseOptimalMove() {
        long startTime = System.currentTimeMillis(); // timer

        Move bestMove = negamax(getGrid(), 15, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 0);
        getGrid().addToken(bestMove.getMove(), getToken()); // mark cell

        System.out.println("AI move: " + bestMove.getMove());

        long endTime = System.currentTimeMillis(); // timer
        System.out.println("Duration: " + (endTime - startTime)); 
    }
    // depth: 15 | best time: >= 1100 ms
    private Move negamax(Grid state, int depth, int alpha, int beta, int color, int currentDepth) {
        // reached terminal state or intended depth
        if (state.isTerminalState() || depth == 0) {
            return heuristicValue(state, color, currentDepth); // return heuristic value
        }

        // store prevous state data
        int lastMoveRow = state.getLastMoveRow();
        int lastMoveColumn = state.getLastMoveColumn();
        char lastWinnerToken = state.getWinnerToken();

        Move bestMove = new Move(-1, Integer.MIN_VALUE, currentDepth);

        // loop through all possible avaialbe columns
        for (int i : state.getAvailableColumns()) {
            char currentPlayerToken = (color == 1 ? getToken() : getOpponentToken());
            state.addToken(i, currentPlayerToken);  // move

            // recursively find possible moves
            Move move = negamax(state, depth-1, -beta, -alpha, -color, currentDepth+1);

            state.undoMove(i, lastMoveRow, lastMoveColumn, lastWinnerToken); // undo move

            int negatedScore = -move.getScore(); // negate score (instead of -negamax())

            // if new move has higher score OR same score but lower depth
            if ((negatedScore > bestMove.getScore()) ||
                (negatedScore == bestMove.getScore() && move.getDepth() < bestMove.getDepth())) {
                bestMove = new Move(i, negatedScore, currentDepth);
            }
            
            alpha = Math.max(alpha, negatedScore); // update alpha value
            // pruning condition
            if (beta <= alpha) {
                break; // skip branch
            }
        }
        return bestMove; 
    }
    // method to evaluate the heuristic value for game states
    private Move heuristicValue(Grid state, int color, int currentDepth) {
        // game is a tie
        if (state.getWinnerToken() == '\0') {
            return new Move(-1, color * 0, currentDepth);
        }
        // max player (us) won
        else if (state.getWinnerToken() == getToken()) {
            return new Move(-1, color * 1, currentDepth);
        }
        // min player won (opponent)
        else {
            return new Move(-1, color * -1, currentDepth);
        }
    }

    // method to choose a random column (1-7) inclusive
    private void chooseRndMove() {
        int column = ((int) (Math.random() * 7)) + 1; // choose random column
        getGrid().addToken(column, getToken()); // mark cell
    }
}
