/**
 * Cette classe s'occupe de gérer le plateau. <br>
 *     Une plateau est caractérisé par :
 *     <ul>
 *         <li>Un nombre de pierres au début de la partie.</li>
 *         <li>Un nombre de cases.</li>
 *     </ul>
 * @author Richard BRUNEAU et Pierre SABARD
 */

public class Board {

    /**
     * Le nombre de pierre de chaque joueur.
     */
    private int rocks;

    /**
     * Le nombre de case sur le plateau.
     */
    private int boxes;

    /**
     * Le constructeur par défaut d'un plateau.
     */
    public Board()
    {
        this.rocks = 15;
        this.boxes = 5;
    }

    /**
     * Le constructeur avec des paramètres choisis.
     * @param r Le nombre de pierres.
     * @param b Le nombre de cases.
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
     * Getter du nombre de pierres.
     * @return Le nombre de pierres.
     */
    public int getRocks() {
        return rocks;
    }

    /**
     * Setter du nombre de pierres.
     * @param rocks Le nouveau nombre de pierres.
     * @deprecated Ne sert à rien.
     */
    public void setRocks(int rocks) {
        this.rocks = rocks;
    }

    /**
     * Getter du nombre de cases.
     * @return Le nombre de cases
     */
    public int getBoxes() {
        return boxes;
    }

    /**
     * Setter du nombre de cases
     * @param boxes Le nouveau nombre de cases.
     */
    public void setBoxes(int boxes) {
        this.boxes = boxes;
    }
}
