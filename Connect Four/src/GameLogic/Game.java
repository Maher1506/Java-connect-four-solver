package GameLogic;

import Grid.Grid;
import Player.Player;

public class Game {
    
    private Grid grid;
    private Player player1;
    private Player player2;

    public Game(Player p1, Player p2, Grid grid) {
        player1 = p1;
        player2 = p2;
        this.grid = grid;
    }

    // check whether the game is over or not
    public boolean isGameEnded() {
        return grid.isTerminalState();
    }

    // displays the winner or a tie
    public void displayEndStats() {
        System.out.println("Game Ended!");
        char winnerToken = grid.getWinnerToken();

        if (winnerToken == player1.getToken()) { // player 1 won
            System.out.println("Player " + player1.getName() + " Won! (" + player1.getToken() + ")");
        } else if (winnerToken == player2.getToken()) { // player 2 won
            System.out.println("Player " + player2.getName() + " Won! (" + player2.getToken() + ")");
        } else { // game is a tie
            System.out.println("Game is a Tie!");
        }   
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
