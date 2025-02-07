package GameLogic;

import Enums.MoveFlag;

public class MoveEntry {
    private int move;   // column
    private int score;  // minimax score
    private int depth;  // depth of move
    private MoveFlag flag;

    public MoveEntry(int move, int score, int depth) {
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
    public MoveFlag getFlag() {
        return flag;
    }

    // setters
    public void setFlag(MoveFlag flag) {
        this.flag = flag;
    }
}
