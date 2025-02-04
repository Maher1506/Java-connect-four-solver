import GameLogic.Game;
import Grid.Grid;
import Player.AIPlayer;
import Player.HumanPlayer;

public class Main {
    
    public static void main(String[] args) {
        Grid grid = new Grid();
        HumanPlayer p1 = new HumanPlayer("p1", 'x', grid);
        HumanPlayer p2 = new HumanPlayer("p2", 'o', grid);
        AIPlayer ai1 = new AIPlayer("AI", 'o', grid);

        Game game = new Game(p1, ai1, grid);

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
}
