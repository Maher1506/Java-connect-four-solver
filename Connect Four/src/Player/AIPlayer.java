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
        Move bestMove = minimax(getGrid(), true);
        getGrid().addToken(bestMove.getMove(), getToken()); // mark cell
        getGrid().setLastMove(bestMove.getScore()); // set new last move = current move made
    }
    private Move minimax(Grid state, boolean isMaximizingPlayer) {
        // reached terminal state
        if (state.isTerminalState()) {
            
        }
    }

    // method to choose a random column (1-7) inclusive
    private void chooseRndMove() {
        int column = ((int) (Math.random() * 7)) + 1; // choose random column
        getGrid().addToken(column, getToken()); // mark cell
        getGrid().setLastMove(column); // set new last move = current move made
    }
}
