import java.util.Random;

/**
 * This class inherits from the Strategy class.<br>
 * This class has been completed as part of the project.
 * This class is used to carry out confrontations in the face of the cautious strategy.
 * @author B2RJ and Pierre SABARD
 */
public class StrategyRandom extends Strategy{

    /**
     * The constructor
     * @param p The board
     */
    public StrategyRandom(Board p) {
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
        Random random = new Random();
        int min = 1;
        int max = (int)pJ2;
        return random.nextInt(max - min + 1) + min;
    }
}