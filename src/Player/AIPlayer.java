package Player;

import java.util.HashMap;

import Enums.MoveFlag;
import GameLogic.MoveEntry;
import Grid.Grid;

public class AIPlayer extends Player{

    private int[] columnOrder = {3, 2, 4, 1, 5, 0, 6};
    private HashMap<Long, MoveEntry> transpositionTable = new HashMap<>();

    private long runDuration;
    private long exploredNodes;
    private int aiMove;
    
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

        MoveEntry bestMove = negamax(getGrid(), 18, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 0);
        
        // MoveScore bestMove = new MoveScore(-1, Integer.MIN_VALUE);
        // int maxDepth = 18;
        // for (int d = 1; d <= maxDepth; d++) {
        //     MoveScore move = negamax(getGrid(), d, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 0);
            
        //     if (move.getMove() != -1) {
        //         bestMove = move;
        //         System.out.println(bestMove.getMove());
        //     }
        // }
        
        getGrid().makeMove(bestMove.getMove()); // mark cell

        long endTime = System.nanoTime(); // timer

        // System.out.println("AI move: " + bestMove.getMove());
        runDuration = endTime-startTime; 
        aiMove = bestMove.getMove();
    }
    private MoveEntry negamax(Grid state, int depth, int alpha, int beta, int color, int currentDepth) {
        exploredNodes++;
        // state.displayGrid();
        // System.out.println("Depht: " + currentDepth);

        int originalAlpha = alpha;
        long hash = state.getZobristHash();

        // Trans Table lookup
        if (transpositionTable.containsKey(hash)) {
            MoveEntry entry = transpositionTable.get(hash);

            if (entry.getDepth() >= depth) {
                if (entry.getFlag() == MoveFlag.EXACT) {
                    return entry;
                } else if (entry.getFlag() == MoveFlag.LOWERBOUND) {
                    alpha = Math.max(alpha, entry.getScore());
                } else if (entry.getFlag() == MoveFlag.UPPERBOUND) {
                    beta = Math.min(beta, entry.getScore());
                }

                if (beta <= alpha) return new MoveEntry(-1, entry.getScore(), depth);
            }
        }

        // reached terminal state or intended depth
        if (state.isTerminalState() || depth == 0) {
            return heuristicValue(state, color, depth); // return heuristic value
        }

        MoveEntry bestMove = new MoveEntry(-1, Integer.MIN_VALUE, depth);

        // loop through all possible available moves (exploring the center columns first)
        for (int i : columnOrder) {
            // explore if column is not empty
            if (!state.isColumnFull(i)) {
                state.makeMove(i);  // move
                // recursively find possible moves
                MoveEntry move = negamax(state, depth-1, -beta, -alpha, -color, currentDepth+1);
                state.undoMove(); // undo move

                int negatedScore = -move.getScore(); // negate score (instead of -negamax())

                // if new move has higher score OR same score but lower depth
                if ((negatedScore > bestMove.getScore())) {
                    bestMove = new MoveEntry(i, negatedScore, depth);
                }
                
                alpha = Math.max(alpha, negatedScore); // update alpha value
                // pruning condition
                if (beta <= alpha) {
                    break; // skip branch
                }
            }
        }

        // store in Trans Table
        if (bestMove.getScore() <= originalAlpha) {
            bestMove.setFlag(MoveFlag.UPPERBOUND);
        } else if (bestMove.getScore() >= beta) {
            bestMove.setFlag(MoveFlag.LOWERBOUND);
        } else {
            bestMove.setFlag(MoveFlag.EXACT);
        }
        transpositionTable.put(hash, bestMove); // add best move to hash

        return bestMove; 
    }
    // method to evaluate the heuristic value for game states
    private MoveEntry heuristicValue(Grid state, int color, int depth) {
        // game is a tie
        if (state.getWinnerToken() == '\0') {
            return new MoveEntry(-1, color * 0, depth);
        }
        // max player (us) won
        else if (state.getWinnerToken() == getToken()) {
            return new MoveEntry(-1, color * (1000 - state.getMoveCounter()), depth);
        }
        // min player won (opponent)
        else {
            return new MoveEntry(-1, color * (-1000 + state.getMoveCounter()), depth);
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
    public int getAIMove() {
        return aiMove;
    }
}
