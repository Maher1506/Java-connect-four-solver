import GameLogic.Game;
import Grid.Grid;
import Player.AIPlayer;
import Player.HumanPlayer;

public class Main {
    
    public static void main(String[] args) {
        
        /* STATS
         * depth: 18 
         * move: column 4
         * iterations: 70
         * avg time: 5120168455 nano(s) = 5120168455 (s) 
         * nodes visited: 12,704,647
        */
        //benchMarkAI(70);

        gameLoop();        
    }

    private static void gameLoop() {
        Grid grid = new Grid();
        HumanPlayer p1 = new HumanPlayer("p1", 'x', grid);
        HumanPlayer p2 = new HumanPlayer("p2", 'o', grid);
        AIPlayer ai1 = new AIPlayer("AI", 'o', grid);

        Game game = new Game(p1, ai1, grid);

        //initialGameState(grid);

        // display starting info
        game.displayStats();
        grid.displayGrid();

        // alternate between player turns
        while (!game.isGameEnded()) {
            game.getPlayer1().handleTurn();

            if (game.isGameEnded()) { break; }

            game.getPlayer2().handleTurn();
        }
        
        game.displayEndStats();
    }
    private static void initialGameState(Grid grid) {
        grid.makeMove(4);
        grid.makeMove(4);
        grid.makeMove(6);
        grid.makeMove(5);
        grid.makeMove(3);
        grid.makeMove(3);
        grid.makeMove(2);
        grid.makeMove(5);
    }

    private static void benchMarkAI(int iterations) {
        long totalTime = 0;
        int warmups = 20;

        for (int i = 0; i < warmups + iterations; i++) {
            Grid grid = new Grid();
            AIPlayer ai1 = new AIPlayer("AI", 'o', grid);

            grid.makeMove(4);
            ai1.chooseOptimalMove();

            if (i >= warmups) {
                totalTime += ai1.getRunDuration();
            }
            // System.out.println("Explored nodes: " + ai1.getExploredNodes());
            // System.out.println("Duration: " + ai1.getRunDuration());
        }

        System.out.println("Mean Time (nano): " + totalTime/iterations);
    }
}
