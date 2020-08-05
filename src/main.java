/**
 * The main class
 * @author B2RJ and Pierre SABARD
 */


public class main {

    /**
     * Function main
     * @param args Args isn't used
     */
	public static void main(String[] args) throws Exception {

	    int nbGames = 10;
        
        Board p1 = new Board(20, 7);
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
        System.out.println("In " + nbGames + " games,");
        System.out.println("Player 1 has won " + nbJ1 + " times.");
        System.out.println("Player 2 has won " + nbJ2 + " times.");
        if(nbEq > 0) {
            System.out.println("There have been five ties " + nbEq + " ties.");
        }
	}
}