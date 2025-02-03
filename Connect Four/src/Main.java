import GameLogic.Game;
import Grid.Grid;
import Player.HumanPlayer;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid();
        HumanPlayer p1 = new HumanPlayer("p1", 'x', grid);
        HumanPlayer p2 = new HumanPlayer("p2", 'o', grid);

        Game game = new Game(p1, p2, grid);

        game.displayStats();
        grid.displayGrid();

        // alternate between player turns
        while (true) {
            p1.handleTurn();
            p2.handleTurn();
        }
    }
}
