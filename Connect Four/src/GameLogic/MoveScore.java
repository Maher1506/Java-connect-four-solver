package GameLogic;

public class MoveScore {
    private int move; // column
    private int score; // minimax score

    public MoveScore(int move, int score) {
        this.move = move;
        this.score = score;
    }
    
    // getters
    public int getMove() {
        return move;
    }
    public int getScore() {
        return score;
    }
}
