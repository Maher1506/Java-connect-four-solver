import GameLogic.Game;
import Grid.Grid;
import Player.AIPlayer;
import Player.HumanPlayer;

public class Main {
    
    public static void main(String[] args) {
        
        // depth: 15 | iterations: 70 | avg time: 1057538270 nano(s) = 1.057538270 (s)
        benchMarkAI(70);

        // Grid grid = new Grid();
        // HumanPlayer p1 = new HumanPlayer("p1", 'x', grid);
        // HumanPlayer p2 = new HumanPlayer("p2", 'o', grid);
        // AIPlayer ai1 = new AIPlayer("AI", 'o', grid);

        // Game game = new Game(p1, ai1, grid);

        // // display starting info
        // game.displayStats();
        // grid.displayGrid();

        // // alternate between player turns
        // while (!game.isGameEnded()) {
        //     game.getPlayer1().handleTurn();

        //     if (game.isGameEnded()) { break; }

        //     game.getPlayer2().handleTurn();
        // }
        
        // game.displayEndStats();
    }

    private static void benchMarkAI(int iterations) {
        long totalTime = 0;
        int warmups = 20;

        for (int i = 0; i < warmups + iterations; i++) {
            Grid grid = new Grid();
            AIPlayer ai1 = new AIPlayer("AI", 'o', grid);

            grid.addToken(4, 'x');
            ai1.chooseOptimalMove();

            if (i >= warmups) {
                totalTime += ai1.time;
            }
        }
        System.out.println("AVG: " + totalTime/iterations);
    }
}
