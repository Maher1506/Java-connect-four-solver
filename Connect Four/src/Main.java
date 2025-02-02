import Grid.Grid;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid();

        grid.displayGrid();
        grid.addToken(1, 'x');
        grid.displayGrid();
    }
}
