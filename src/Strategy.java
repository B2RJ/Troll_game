/**
 * This class is the mother class for the strategies. <br>
 * A strategy is characterized by a board.
 * @see Board
 * @author Richard BRUNEAU et Pierre SABARD
 */


public abstract class Strategy {

    /**
     * The board that contains the number of stones and the size.
     */
    private Board board;

    /**
     * The constructor
     * @param p The board that contains the number of stones and the size.
     */
    public Strategy(Board p) {
        this.board = p;
    }

    /**
     * The function that plays strategy.
     * @param pJ1 The number of stones of player 1.
     * @param pJ2 The number of stone of player 2.
     * @param posT The position of the Troll.
     * @return The number of stones. played.
     */
    abstract int play(double pJ1, double pJ2, double posT);

    /**
     * Le Getter of board
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Le Setter of board
     * @param board The new board
     */
    public void setBoard(Board board) {
        this.board = board;
    }
}
