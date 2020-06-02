/**
 * Cette classe est la classe mère des classes stratégies. <br>
 * Une stratégie est caractérié par un plateau.
 * @see Board
 * @author Richard BRUNEAU et Pierre SABARD
 */


public abstract class Strategy {

    /**
     * Le plateau qui contient le nombre de pierre et la taille.
     */
    private Board board;

    /**
     * Le constructeur de la classe.
     * @param p Le plateau qui contient le nombre de pierre et la taille.
     */
    public Strategy(Board p) {
        this.board = p;
    }

    /**
     * La fonction qui fait jouer chaque stratégie.
     * @param pJ1 Le nombre de pierre du joueur 1.
     * @param pJ2 Le nombre de pierre du joueur 2.
     * @param posT La position du Troll.
     * @return Le nombre de pierres jetées.
     */
    abstract int play(double pJ1, double pJ2, double posT);

    /**
     * Le Getter du plateau.
     * @return Le plateau.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Le Setter du plateau.
     * @param board Le nouveau plateau.
     */
    public void setBoard(Board board) {
        this.board = board;
    }
}
