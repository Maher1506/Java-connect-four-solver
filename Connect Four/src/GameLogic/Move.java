package GameLogic;

public class Move {
    private int move; // column
    private int score; // minimax score
    private int depth; // depth in game tree

    public Move(int move, int score, int depth) {
        this.move = move;
        this.score = score;
        this.depth = depth;
    }

    // getters
    public int getMove() {
        return move;
    }
    public int getScore() {
        return score;
    }
    public int getDepth() {
        return depth;
    }
}
