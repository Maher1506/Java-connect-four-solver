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
         * avg time: 3593073867 nano(s) = 3.593073867 (s)
         * nodes visited: 15,718,163
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
            grid.generateMasks();
            game.getPlayer1().handleTurn();

            if (game.isGameEnded()) { break; }

            game.getPlayer2().handleTurn();
        }
        
        game.displayEndStats();
    }
    private static void initialGameState(Grid grid) {
        // 44653325(2), 330313(3)
        String moves = "44653325";
        for (int i = 0; i < moves.length(); i++) {
            grid.makeMove(moves.charAt(i) - '0');
        }
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
            System.out.println("Explored nodes: " + ai1.getExploredNodes());
            // System.out.println("Duration: " + ai1.getRunDuration());
        }

        System.out.println("Mean Time (nano): " + totalTime/iterations);
    }
}
