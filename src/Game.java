import java.util.HashMap;

/**
 * Cette classe s'occupe de gérer une partie. <br>
 *     Une partie est caractérisé par :
 *     <ul>
 *         <li>Un plateau.</li>
 *         <li>Une stratégie pour chaque joueur.</li>
 *         <li>Un nombre de pierre pour chaque joueur.</li>
 *         <li>La position du Troll.</li>
 *         <li>Le nombre de case.</li>
 *         <li>Un entier qui indique le vainqueur.
 *         <ul>
 *             <li>0 en cas d'égalité.</li>
 *             <li>1 si le joueur 1 gagne.</li>
 *             <li>2 si le joueur 2 gagne.</li>
 *         </ul>
 *         </li>
 *         <li>Une map qui contient l'historique des position.</li>
 *         <li>Le nombre de partie qui ont déjà eu lieu.</li>
 *     </ul>
 * @see Board
 * @see Strategy
 * @author Richard BRUNEAU et Pierre SABARD
 */

public class Game {
    /**
     * Le plateau qui contient le nombre de pierre et la taille.
     */
    private Board board;

    /**
     * La stratégie du joueur.
     */
    private Strategy strategy1, strategy2;

    /**
     * Le nombre de pierre du joueur.
     */
    private double rockJ1, rockJ2;

    /**
     * La position du Troll.
     */
    private double positionT;

    /**
     * La taille du plateau.
     * Equivalent à p.getBoxes()
     */
    private double nbBox;

    /**
     * L'entier qui détermine qui a gagné.
     * <ul>
     *    <li>0 en cas d'égalité.</li>
     *    <li>1 si le joueur 1 gagne.</li>
     *    <li>2 si le joueur 2 gagne.</li>
     * </ul>
     */
    private int winner;

    /**
     * L'historique des coups joués.
     */
    private HashMap<String, Double> map = new HashMap<String, Double>();

    /**
     * Le nombre de parties déjà effectuées.
     */
    private int nbGames;

    /**
     * Le constructeur de la classe partie.
     * @param p Le plateau. Contient le nombre de pierres et le nombre de case
     * @param s1 La stratégie du joueur 1
     * @param s2 La stratégie du joueur 2
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
     * Permet de ré-initialiser la partie. Cette fonction est utile quand on
     * lance plusieurs parties les unes après les autres.
     */
    public void initGame() {
        this.rockJ1 = this.board.getRocks();
        this.rockJ2 = this.board.getRocks();
        this.positionT = 0;
        //this.rockJ1 = 20;
        //this.rockJ2 = 20;
        //this.positionT = -1;
        this.nbBox = this.board.getBoxes();
        this.winner = 0;
    }

    /**
     * Getter de victoire
     * @return le vainqueur.
     * <ul>
     * <li>0 en cas d'égalité.</li>
     * <li>1 si le joueur 1 gagne.</li>
     * <li>2 si le joueur 2 gagne.</li>
     * </ul>
     */
    public int getWinner() {
        return this.winner;
    }

    /**
     * La fonction qui fait jouer chaque strategie
     * @param pJ1 Le nombre de pierres du joueur 1
     * @param pJ2 Le nombre de pierres du joueur 2
     * @param posT La position du troll
     * @return Un tableau de deux cases qui contient le nombre de pierres joué par chaque joueur.
     */
    public int[] lauchPlay(double pJ1, double pJ2, double posT) {
        int[] throwRocks = new int[2];
        throwRocks[0] = this.strategy1.play(pJ1, pJ2, posT);
        throwRocks[1] = this.strategy2.play(pJ1, pJ2, posT);
        
        return throwRocks;
    }

    /**
     * Cette fonction gère la partie.
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
     * Cette fonction gère si la partie est finie.
     * En cas de fin de partie, la fonction indique qui a gagné.
     * @return Le booléen qui indique si la partie est fini.
     */
    public boolean gameOver() {
        //Cas J1 n'a plus de pierres
        if (this.rockJ1 <= 0) {
            if (this.positionT - this.rockJ2 > 0) {
                this.winner = 1;
            } else if (this.positionT - this.rockJ2 == 0) {
                this.winner = 0;
            } else {
                this.winner = 2;
            }
            return false;
        //Cas J2 n'as plus de pierre
        } else if (this.rockJ2 <= 0) {
            if (this.positionT + this.rockJ1 > 0) {
                this.winner = 1;
            } else if (this.positionT + this.rockJ1 == 0) {
                this.winner = 0;
            } else {
                this.winner = 2;
            }
            return false;
        //Cas J1 gagne
        } else if ( this.positionT == ((this.nbBox - 1)/2)) {
            this.winner = 1;
            return false;
        //Cas J2 gagne
        } else if ( this.positionT == -((this.nbBox - 1)/2) ) {
            this.winner = 2;
            return false;
        }
        return true;
    }
}
