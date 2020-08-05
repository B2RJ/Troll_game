/**
 * This class is in charge of managing the set. *
 * A tray is characterized by:
 * <ul>
 *  <li> A number of stones at the beginning of the game. </li>
 *  <li> A number of boxes.</li>
 * </ul>
 * @author B2RJ and Pierre SABARD
 */

public class Board {

    /**
     * The number of stones thrown by the player.
     */
    private int rocks;

    /**
     * The number of case on the board.
     */
    private int boxes;

    /**
     * The default constructor
     */
    public Board()
    {
        this.rocks = 15;
        this.boxes = 5;
    }

    /**
     * The constructor with selected parameters.
     * @param r The number of stones.
     * @param b The number of squares.
     */
    public Board(int r, int b)
    {
        this.rocks = r;
        if (b % 2 != 0) {
            b++;
        }
        this.boxes = b;
    }

    /**
     * Getter of stones number
     * @return The number of stones.
     */
    public int getRocks() {
        return rocks;
    }

    /**
     * Setter of the number of stones.
     * @param rocks The new number of rocks.
     * @deprecated Is useless.
     */
    public void setRocks(int rocks) {
        this.rocks = rocks;
    }

    /**
     * Getter of the number of boxes.
     * @return The number of boxes
     */
    public int getBoxes() {
        return boxes;
    }

    /**
     * Setter of the number cases
     * @param boxes The new number of cases.
     */
    public void setBoxes(int boxes) {
        this.boxes = boxes;
    }
}
