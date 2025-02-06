package Player;

import java.util.InputMismatchException;
import java.util.Scanner;

import Grid.Grid;

public class HumanPlayer extends Player {

    // input related
    private Scanner sc = new Scanner(System.in);
    
    public HumanPlayer(String name, char token, Grid grid) {
        super(name, token, grid);
    }

    @Override
    // method to take input from player and take action in game
    public void handleTurn() {
        System.out.println("Player " + getName() + "'s turn: (" + getToken() + ")");

        int column = getInput(); // handle input
        getGrid().makeMove(column); // mark cell
        getGrid().displayGrid(); // display grid after action
    }

    // take input for the column and check if valid
    private int getInput() {
        int column;
        while (true) {
            System.out.println("Enter a column (0-6): ");
            try {
                column = sc.nextInt();

                if (column > 6 || column < 0) {
                    System.out.println("OUT OF BOUNDS");
                    getGrid().displayGrid();
                } else if (getGrid().isColumnFull(column)) { // column entered is full
                    System.out.println("COLUMN IS FULL");
                    getGrid().displayGrid();
                } else { // correct input
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("INCORRECT INPUT (ENTER AN INTEGER)");
                sc.nextLine();
                getGrid().displayGrid();
            }
        }
        return column;
    }
}
