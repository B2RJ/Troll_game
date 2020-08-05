import java.util.Scanner;

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

        Scanner sc = new Scanner(System.in);
        int choice = 0;
        while (choice < 1 || choice > 6) {
            System.out.println("What kind of games do you want to play");
            System.out.println("1 : IA Safe vs IA Safe");
            System.out.println("2 : IA Safe vs IA Random");
            System.out.println("3 : IA Random vs IA Random");
            System.out.println("4 : IA Safe vs Human");
            System.out.println("5 : IA Random vs Human");
            //Read the choice
            choice = sc.nextInt();
            if (choice < 1 || choice > 6) {
                System.out.println("Hmm, something wrong");
            }
        }
        System.out.println("You choose: " + choice);

        int nbGames = 10;
        Board b1 = new Board(20, 7);

        Strategy s1, s2;


        switch(choice) {
            case 1:
                s1 = new StrategySafe(b1);
                s2 = new StrategySafe(b1);
                break;
            case 2:
                s1 = new StrategySafe(b1);
                s2 = new StrategyRandom(b1);
                break;
            case 3:
                s1 = new StrategyRandom(b1);
                s2 = new StrategyRandom(b1);
                break;
            case 4:
                s1 = new StrategySafe(b1);
                s2 = new StrategyHuman(b1);
                break;
            default:
                s1 = new StrategyRandom(b1);
                s2 = new StrategyHuman(b1);
                break;
        }

        int nbJ1 = 0;
        int nbJ2 = 0;
        int nbEq = 0;
        
        Game game = new Game(b1, s1, s2);
        System.out.print("[");
        for (int i = 0; i < nbGames; i++) {
            System.out.print(".");
            game.initGame();
            game.startGame();
            switch (game.getWinner()) {
                case 0:
                    nbEq++;
                    System.out.println("Tie");
                    break;
                case 1:
                    nbJ1++;
                    System.out.println("Player 1 win");
                    break;
                case 2:
                    System.out.println("Player 2 win");
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