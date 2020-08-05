import java.util.HashMap;

/**
 * This class is in charge of running a game. <br>
 *     One game is characterized by:
 *     <ul>
 *         <li>A board.</li>
 *         <li>A strategy for each player.</li>
 *         <li>A number of stones for each player.</li>
 *         <li>The position of the Troll.</li>
 *         <li>The number of boxes.</li>
 *         <li>An interger that indicates the winner.
 *         <ul>
 *             <li>0 in case of a tie.</li>
 *             <li>1 if player 1 wins.</li>
 *             <li>2 if player 2 wins.</li>
 *         </ul>
 *         </li>
 *         <li>Une map qui contient l'historique des position.</li>
 *         <li>Le nombre de partie qui ont déjà eu lieu.</li>
 *     </ul>
 * @see Board
 * @see Strategy
 * @author B2RJ et Pierre SABARD
 */

public class Game {
    /**
     * The board that contains the number of stones and the size.
     */
    private Board board;

    /**
     * The strategy of each player
     */
    private Strategy strategy1, strategy2;

    /**
     * The number of stones.
     */
    private double rockJ1, rockJ2;

    /**
     * The troll's position
     */
    private double positionT;

    /**
     * The size of the tray.
     * Equivalent to p.getBoxes()
     */
    private double nbBox;

    /**
     * The integer that determines who won.
     * <ul>
     *    <li>0 in case of tie.</li>
     *    <li>1 if player 1 wins.</li>
     *    <li>2 if player 2 wins.</li>
     * </ul>
     */
    private int winner;

    /**
     * L'historique des coups joués.
     */
    private HashMap<String, Double> map = new HashMap<String, Double>();

    /**
     * The history of the moves played.
     */
    private int nbGames;

    /**
     * The builder of the class part.
     * @param p The tray. Contains the number of stones and the number of squares.
     * @param s1 Player 1's strategy
     * @param s2 Player 2's strategy
     */
    public Game(Board p, Strategy s1, Strategy s2) {
        this.board = p;
        this.strategy1 = s1;
        this.strategy2 = s2;
        this.rockJ1 = p.getRocks();
        this.rockJ2 = p.getRocks();
        this.positionT = 0;
        this.nbBox = p.getBoxes();
        this.winner = 0;
        this.nbGames = 0;
    }

    /**
     * Allows you to reset the game. This function is useful when
     * throws several games one after the other.
     */
    public void initGame() {
        this.rockJ1 = this.board.getRocks();
        this.rockJ2 = this.board.getRocks();
        this.positionT = 0;
        this.nbBox = this.board.getBoxes();
        this.winner = 0;
    }

    /**
     * Getter of victory
     * @return the winner.
     * <ul>
     * <li> 0 in case of a tie.</li>
     * <li> 1 if player 1 wins.</li>
     * <li> 2 if player 2 wins.</li>
     * </ul>
     */
    public int getWinner() {
        return this.winner;
    }

    /**
     * The function that brings each strategy into play
     * @param pJ1 The number of stones of player 1
     * @param pJ2 The number of stones of player 2
     * @param posT The troll's position
     * @return A two-square board that contains the number of stones played by each player.
     */
    public int[] lauchPlay(double pJ1, double pJ2, double posT) {
        int[] throwRocks = new int[2];
        throwRocks[0] = this.strategy1.play(pJ1, pJ2, posT);
        throwRocks[1] = this.strategy2.play(pJ1, pJ2, posT);
        
        return throwRocks;
    }

    /**
     * This function manages the game.
     */
    public void startGame() {
        if (this.nbGames > 0) {
            if (this.strategy1 instanceof StrategySafe) {
                ((StrategySafe) this.strategy1).setMap(this.map);
            }
            if (this.strategy2 instanceof StrategySafe) {
                ((StrategySafe) this.strategy2).setMap(this.map);
            }
        }
        int i = 0;
        while(gameOver()) {
            double pJ1 = this.rockJ1;
            double pJ2 = this.rockJ2;
            double posT = this.positionT;
            int[] result;

            result = lauchPlay(pJ1, pJ2, posT);
            double newPj1 = result[0];
            double newPj2 = result[1];

            //System.out.println("pj1: "+ newPj1 + " pj2: " + newPj2);
            if (i==0) {
                if(this.strategy1 instanceof StrategySafe) {
                    //Question 1
                    // Permet de trouver la valeur Gopt dans la situation demandé
                    //System.out.println(((StrategySafe) this.strategy1).evalGopt(23,20, -1));
                    //Affiche -0.1664669487064999

                    // Permet de trouver la Gopt avec le x le plus petit pour Gopt est inférieur à 0
                    //System.out.println(((StrategySafe) this.strategy1).evalGopt(20,16, -1));
                    // Affiche 0.2283533416504434
                    // System.out.println(((StrategySafe) this.strategy1).evalGopt(20,17, -1));
                    // Affiche -0.039592568673246745
                    // Donc, le minimum pour x avec un gain < 0 est 17.

                    //Question 3
                    //System.out.println("GAIN: " + ((StrategySafe) this.strategy1).evalGopt(20,20, 0));
                    //Affiche 0.0

                    //Question 4
                    //System.out.println("GAIN: " + ((StrategySafe) this.strategy1).evalGopt(20,20, 0));
                    //Affiche -0.02054972972976948
                }
                if(this.strategy1 instanceof StrategySafe2) {
                    System.out.println(((StrategySafe2) this.strategy1).evalGopt(20,20, -1));
                }
            }
            i++;
            
            this.rockJ1 = pJ1 - newPj1;
            this.rockJ2 = pJ2 - newPj2;
            if (newPj1 > newPj2) {
                this.positionT++;
            }else if (newPj2 > newPj1) {
                this.positionT--;
            }
            //Question 4b
            if(newPj1 > newPj2 + 2) {
               // this.positionT++;
            }
            //System.out.println("pj1: "+ newPj1 + " pj2: " + newPj2 + " troll :" + this.positionT);
        }
        this.nbGames++ ;
    }

    /**
     * This function controls whether the game is over.
     * If the game is over, the function indicates who won.
     * @return The boolean that indicates if the game is over.
     */
    public boolean gameOver() {
        //If Player 1 has no more stones
        if (this.rockJ1 <= 0) {
            if (this.positionT - this.rockJ2 > 0) {
                this.winner = 1;
            } else if (this.positionT - this.rockJ2 == 0) {
                this.winner = 0;
            } else {
                this.winner = 2;
            }
            return false;
        //If Player 2 has no more stones
        } else if (this.rockJ2 <= 0) {
            if (this.positionT + this.rockJ1 > 0) {
                this.winner = 1;
            } else if (this.positionT + this.rockJ1 == 0) {
                this.winner = 0;
            } else {
                this.winner = 2;
            }
            return false;
        //Player 1 win
        } else if ( this.positionT == ((this.nbBox - 1)/2)) {
            this.winner = 1;
            return false;
        //Player 2 win
        } else if ( this.positionT == -((this.nbBox - 1)/2) ) {
            this.winner = 2;
            return false;
        }
        return true;
    }
}
