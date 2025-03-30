package Player;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import Enums.MoveFlag;
import GameLogic.MoveEntry;
import Grid.Grid;
import Grid.OpeningBook;

public class AIPlayer extends Player{

    private int[] columnOrder = {3, 2, 4, 1, 5, 0, 6};
    private HashMap<Long, MoveEntry> transpositionTable = new HashMap<>();
    private OpeningBook openingBook;

    // for benchmark
    private long runDuration;
    private long exploredNodes;
    private int aiMove;
    
    public AIPlayer(String name, char token, Grid grid) {
        super(name, token, grid);
        openingBook = new OpeningBook();
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

        // get the move history
        String moveHistoryString = getGrid().getMoveHistory().stream().map(Object::toString)
                                        .collect(Collectors.joining(","));
        // move sequence is found in the opeing book                               
        if (openingBook.getOpeningBook().containsKey(moveHistoryString)) {
            int move = openingBook.getOpeningBook().get(moveHistoryString);
            getGrid().makeMove(move);
        } 
        // use ai algorithm if not in opening book
        else {
            MoveEntry bestMove = negamax(getGrid(), 3, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 0);
            getGrid().makeMove(bestMove.getMove()); // mark cell
        }
        
        // MoveScore bestMove = new MoveScore(-1, Integer.MIN_VALUE);
        // int maxDepth = 18;
        // for (int d = 1; d <= maxDepth; d++) {
        //     MoveScore move = negamax(getGrid(), d, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 0);
            
        //     if (move.getMove() != -1) {
        //         bestMove = move;
        //         System.out.println(bestMove.getMove());
        //     }
        // }
        

        long endTime = System.nanoTime(); // timer

        // System.out.println("AI move: " + bestMove.getMove());
        runDuration = endTime-startTime; 
        //aiMove = bestMove.getMove();
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
            return evaulateState(state, color, depth); // return heuristic value
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

                // if new move has higher score
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
    // method to evaluate the all game states
    private MoveEntry evaulateState(Grid state, int color, int depth) {
        int score = 0;
        Long currentPlayerBoard = (color == 1) ? state.getAIBitboard() : state.getPlayerBitboard();
        Long opponentBoard = (color == 1) ? state.getPlayerBitboard() : state.getAIBitboard();
        currentPlayerBoard = state.getAIBitboard();
        opponentBoard = state.getPlayerBitboard();

        // System.out.println("Depth: " + depth);;
        // state.printBitboard(currentPlayerBoard, 'C');
        // state.printBitboard(opponentBoard, 'A');
        
        // central control
        // int centerCol = 3;
        // for (int row = 0; row < 6; row++) {
        //     int bitIndex = centerCol * 7 + row;
        //     if (((currentPlayerBoard >> bitIndex) & 1L) == 1) score += 3;
        //     if (((opponentBoard >> bitIndex) & 1L) == 1) score -= 3;
        // }

        // // positional column weights
        // int[] columnScores = {3, 4, 5, 7, 5, 4, 3}; // adjust as needed
        // for (int col = 0; col < 7; col++) {
        //     for (int row = 0; row < 6; row++) {
        //         int bitIndex = col * 7 + row;
        //         if (((currentPlayerBoard >> bitIndex) & 1L) == 1) score += columnScores[col];
        //         if (((opponentBoard >> bitIndex) & 1L) == 1) score -= columnScores[col];
        //     }
        // }


        List<Long> masks = state.generateMasks();
        // loop through all mask
        for (Long mask : masks) {
            int aiCount = Long.bitCount(currentPlayerBoard & mask); // number of ai tokens in the mask
            int playerCount = Long.bitCount(opponentBoard & mask); // number of player tokens in the mask

            if (aiCount > 0 && playerCount > 0) 
                continue;
            
            // max player (us) won
            if (aiCount == 4)
                return new MoveEntry(-1, color * (100 - state.getMoveCounter()), depth);
            // min player won (opponent)
            if (playerCount == 4)
                return new MoveEntry(-1, color * (-100 + state.getMoveCounter()), depth);

            // advantage for max player (us)
            if (aiCount == 3 && playerCount == 0)
                score += 5;
            else if (aiCount == 2 && playerCount == 0)
                score += 2;

            // advantage for min player (opponnet) (threat for us)
            if (playerCount == 3 && aiCount == 0)
                score -= 50;
            else if (playerCount == 2 && aiCount == 0)
                score -= 2;
        }

        // Hardcoded check for immediate horizontal threat
        long hcMask = 2113665l;
        int count = Long.bitCount(hcMask & opponentBoard);
        if (count == 3) {
            System.out.println("HIGH THREAT");
            score -= 1000;
        }
        //state.printBitboard(hcMask, 'H');

        return new MoveEntry(-1, color * (score - state.getMoveCounter()), depth);
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
