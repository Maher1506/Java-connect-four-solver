package GameLogic;

import Grid.Grid;
import Player.Player;

public class Game {
    
    private Grid grid;
    private Player player1;
    private Player player2;

    private Player winnerPlayer;

    public Game(Player p1, Player p2, Grid grid) {
        player1 = p1;
        player2 = p2;
        this.grid = grid;
    }

    public boolean isGameEnded() {
        return grid.isTerminalState();
    }

    // method to display player details at the start
    public void displayStats() {
        player1.display();
        player2.display();
    }

    // getters
    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }
}
