package Player;

import java.util.HashMap;

import GameLogic.MoveScore;
import Grid.Grid;

public class AIPlayer extends Player{

    private int[] columnOrder = {3, 2, 4, 1, 5, 0, 6};
    private HashMap<Long, MoveScore> transpositionTable = new HashMap<>();

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

        MoveScore bestMove = negamax(getGrid(), 18, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 0);
        getGrid().makeMove(bestMove.getMove()); // mark cell

        long endTime = System.nanoTime(); // timer

        // System.out.println("AI move: " + bestMove.getMove());
        runDuration = endTime-startTime; 
    }
    private MoveScore negamax(Grid state, int depth, int alpha, int beta, int color, int currentDepth) {
        exploredNodes++;
        // state.displayGrid();
        // System.out.println("Depht: " + currentDepth);

        // if the state's score is already in the trans table
        long hash = state.getZobristHash();
        if (transpositionTable.containsKey(hash)) {
            return transpositionTable.get(hash);
        }

        // reached terminal state or intended depth
        if (state.isTerminalState() || depth == 0) {
            MoveScore heuristic = heuristicValue(state, color);
            transpositionTable.put(hash, heuristic); // add state to trans table
            return heuristic; // return heuristic value
        }

        MoveScore bestMove = new MoveScore(-1, Integer.MIN_VALUE);

        // loop through all possible available moves (exploring the center columns first)
        for (int i : columnOrder) {
            // explore if column is not empty
            if (!state.isColumnFull(i)) {
                //char lastWinnerToken = state.getWinnerToken(); // store winner of original state
                state.makeMove(i);  // move

                // recursively find possible moves
                MoveScore move = negamax(state, depth-1, -beta, -alpha, -color, currentDepth+1);

                state.undoMove(); // undo move

                int negatedScore = -move.getScore(); // negate score (instead of -negamax())

                // if new move has higher score OR same score but lower depth
                if ((negatedScore > bestMove.getScore())) {
                    bestMove = new MoveScore(i, negatedScore);
                }
                
                alpha = Math.max(alpha, negatedScore); // update alpha value
                // pruning condition
                if (beta <= alpha) {
                    break; // skip branch
                }
            }
        }
        transpositionTable.put(hash, bestMove); // add best move to hash
        return bestMove; 
    }
    // method to evaluate the heuristic value for game states
    private MoveScore heuristicValue(Grid state, int color) {
        // game is a tie
        if (state.getWinnerToken() == '\0') {
            return new MoveScore(-1, color * 0);
        }
        // max player (us) won
        else if (state.getWinnerToken() == getToken()) {
            return new MoveScore(-1, color * (100 - state.getMoveCounter()));
        }
        // min player won (opponent)
        else {
            return new MoveScore(-1, color * (state.getMoveCounter() - 100));
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
