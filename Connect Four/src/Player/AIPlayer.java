package Player;

import GameLogic.Move;
import Grid.Grid;

public class AIPlayer extends Player{

    private int[] columnOrder = {3, 2, 4, 1, 5, 0, 6};

    private long runDuration;
    private long exploredNodes;
    
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
    public void chooseOptimalMove() {
        long startTime = System.nanoTime(); // timer

        Move bestMove = negamax(getGrid(), 21, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 0);
        getGrid().makeMove(bestMove.getMove()); // mark cell

        long endTime = System.nanoTime(); // timer

        // System.out.println("AI move: " + bestMove.getMove());
        // System.out.println("Duration: " + (endTime - startTime));
        runDuration = endTime-startTime; 
    }
    private Move negamax(Grid state, int depth, int alpha, int beta, int color, int currentDepth) {
        exploredNodes++;
        // state.displayGrid();
        // System.out.println(currentDepth);

        // reached terminal state or intended depth
        if (state.isTerminalState() || depth == 0) {
            return heuristicValue(state, color, currentDepth); // return heuristic value
        }

        Move bestMove = new Move(-1, Integer.MIN_VALUE, currentDepth);

        // loop through all possible available moves (exploring the center columns first)
        for (int i : columnOrder) {
            // explore if column is not empty
            if (!state.isColumnFull(i)) {
                //char lastWinnerToken = state.getWinnerToken(); // store winner of original state
                state.makeMove(i);  // move

                // recursively find possible moves
                Move move = negamax(state, depth-1, -beta, -alpha, -color, currentDepth+1);

                state.undoMove(); // undo move

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

    // method to choose a random column (0-6) inclusive
    private void chooseRndMove() {
        int column = ((int) (Math.random() * 7)); // choose random column
        getGrid().makeMove(column); // mark cell
    }

    // getters
    public long getRunDuration() {
        return runDuration;
    }
    public long getExploredNodes() {
        return exploredNodes;
    }
}
