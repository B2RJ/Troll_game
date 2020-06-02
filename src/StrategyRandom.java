import java.util.Random;

/**
 * Cette classe hérite de la classe Stratégie.<br>
 * Cette classe a été réalisé dans le cadre du projet.
 * Cette classe permet de réaliser des affrontements face à la stratégie prudente
 * @author Richard BRUNEAU et Pierre SABARD
 */
public class StrategyRandom extends Strategy{

    /**
     * Le constructeur de la classe stratégie aléatoire
     * @param p Le plateau
     */
    public StrategyRandom(Board p) {
        super(p);
    }

    /**
     * Permet de savoir combien de pierre peut lancer le joueur 2.
     * @param pJ1 Le nombre de pierre du joueur 1.
     * @param pJ2 Le nombre de pierre du joueur 2.
     * @param posT La position du Troll.
     * @return Le nombre de pierre à lancer
     */
    @Override
    int play(double pJ1, double pJ2, double posT) {
        Random random = new Random();
        int min = 1;
        int max = (int)pJ2;
        return random.nextInt(max - min + 1) + min;
    }
}