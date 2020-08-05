import java.util.Scanner;

/**
 * This class inherits from the Strategy class.<br>
 * This class is used to game with humans
 * @author B2RJ
 */
public class StrategyHuman extends Strategy{

    /**
     * The constructor
     * @param p The board
     */
    public StrategyHuman(Board p) {
        super(p);
    }

    /**
     * Allows you to know how many stones player 2 can throw.
     * @param pJ1 The number of stone of player 1.
     * @param pJ2 The number of stone of the player 2.
     * The position of the troll.
     * The number of stones to be thrown.
     */
    @Override
    int play(double pJ1, double pJ2, double posT) {
        int stones = -1;

        while (stones < 0 || stones > pJ2) {
            System.out.println("You have " + pJ2 + " stones and the troll is in " + posT);
            System.out.println("How many stones do you play ?");

            Scanner sc = new Scanner(System.in);
            stones = sc.nextInt();
        }
        return stones;
    }
}