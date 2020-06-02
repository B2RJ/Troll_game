/**
 * La classe principale
 * @author Richard BRUNEAU et Pierre SABARD
 */


public class main {

    /**
     * Fonction qui gère le programme
     * @param args Ici, args n'est pas utilisé
     */
	public static void main(String[] args) throws Exception {

	    int nbGames = 100;
        
        Board p1 = new Board(15, 5);
        Strategy s1 = new StrategySafe(p1);
        Strategy s2 = new StrategyRandom(p1);
        
        int nbJ1 = 0;
        int nbJ2 = 0;
        int nbEq = 0;
        
        Game game = new Game(p1, s1, s2);
        System.out.print("[");
        for (int i = 0; i < nbGames; i++) {
            System.out.print(".");
            game.initGame();
            game.startGame();
            switch (game.getWinner()) {
                case 0:
                    nbEq++;
                    break;
                case 1:
                    nbJ1++;
                    break;
                case 2:
                    nbJ2++;
                    break;
            }
        }
        System.out.println("]");
        System.out.println("En " + nbGames + " parties,");
        System.out.println("Le joueur 1 a gagné " + nbJ1 + " fois.");
        System.out.println("Le joueur 2 a gagné " + nbJ2 + " fois.");
        if(nbEq > 0) {
            System.out.println("Il y a eu " + nbEq + " fois égalité.");
        }
	}
}