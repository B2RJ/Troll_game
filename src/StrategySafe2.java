import it.ssc.pl.milp.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.LogManager;

import static it.ssc.pl.milp.LP.NaN;


/**
 * Cette classe hérite de la classe Stratégie.<br>
 * Cette classe a été réalisé dans le cadre du projet.
 * Cette classe est caractérisée par :
 * <ul>
 *     <li>Une constante lorsqu'il n'y a pas de résultats trouvés.</li>
 *     <li>Une map de coup joués.</li>
 *     <li>Une chaine de caractère représentant la tête.</li>
 *     <li>Le nombre de pierre jetées par le joueur.</li>
 * </ul>
 * @author Richard BRUNEAU et Pierre SABARD
 */
public class StrategySafe2 extends Strategy {

    /**
     * La constante qui permet de déterminer quand on a pas trouvé de résultat.
     */
    private final double NOT_FOUND = 404;

    /**
     * La map d'historique des coups.
     * Elle permet de ne chercher qu'une seule fois par situation.
     */
    private HashMap<String, Double> map = new HashMap<>();

    /**
     * La chaine de caractère qui rentre dans map.
     */
    private String head;

    /**
     * Le nombre de pierre jetées par le joueur.
     */
    private int rocks;

    /**
     * Le constructeur de la classe StrategiePrudente.
     * Le constructeur iniatilise le nombre de pierre jouée à 1.
     * @param p Le plateau qui contient le nombre de pierre et de case.
     */
    public StrategySafe2(Board p) {
        super(p);
        this.rocks = 1;
    }

    /**
     * Geter de la map.
     * @return La map.
     */
    public HashMap<String, Double> getMap() {
        return this.map;
    }

    /**
     * Seter de la map.
     * @param m La nouvelle map.
     */
    public void setMap(HashMap<String, Double> m) {
        this.map = m;
    }

    /**
     * La fonction qui fait jouer la stratégie.
     * @param pJ1  Le nombre de pierre du joueur 1.
     * @param pJ2  Le nombre de pierre du joueur 2.
     * @param posT La position du Troll.
     * @return Le nombre de pierres. jouées.
     */
    @Override
    public int play(double pJ1, double pJ2, double posT) {
        this.head = pJ1 + "-" + pJ2 + "-" + posT;
        evalGopt(pJ1, pJ2, posT);
        return this.rocks;
    }

    /**
     * La fonction qui recherche les cas simples du tableau.
     * @param pJ1 Le nombre de pierre du joueur 1.
     * @param pJ2 Le nombre de pierre du joueuer 2.
     * @param posT La position du Troll
     * @return Le résultat de l'affrontement. <ul>
     *     <li>1 si le joueur gagne la bataille</li>
     *     <li>-1 si le joueur perd la bataille</li>
     *     <li>0 si il y a égalité</li>
     *     <li>NOT_FOUND si le cas n'est pas trivial</li>
     * </ul>
     */
    public double dico(double pJ1, double pJ2, double posT) {
        //Si un joueur gagne
        int nbBoxes = super.getBoard().getBoxes();
        if (posT == ((nbBoxes - 1)/2)) {
            return 1;
        }
        if (posT == -((nbBoxes - 1)/2)) {
            return -1;
        }

        //Cas J1 n'as plus de pierre
        if (pJ1 == 0) {
            if (posT - pJ2 > 0) {
                return 1;
            } else if (posT - pJ2 == 0) {
                return 0;
            } else {
              return -1;
            }
        }
        //Cas J2 n'a plus de pierre
        if (pJ2 == 0) {
            if (posT + pJ1 > 0) {
                return 1;
            } else if (posT + pJ1 == 0) {
                return 0;
            } else {
              return -1;
            }
        }
        //Cas egalite
        if (pJ1 == pJ2 && posT == 0) {
            return 0;
        }
        return NOT_FOUND;
    }

    /**
     * La fonction qui génère le tableau en fonction des paramètres.
     * @param pJ1 Le nombre de pierre du joueur 1.
     * @param pJ2 Le nombre de pierre du joueur 2.
     * @param posT La position du Troll.
     * @return Un tableau en 3D avec le calcul des gains.
     */
    public double[][][] genGopt(double pJ1, double pJ2, double posT) {
        double[][][] goptTable = new double[(int) pJ1][(int) pJ2][3];
        for (int line = 1; line <= pJ1; line++) {
            for (int column = 1; column <= pJ2; column = column + 2) {
                double t = posT;
                if (column > line) {
                    t = posT -1;
                } else if (column < line) {
                    t = posT +1;
                }
                goptTable[line-1][column-1] = new double[] {pJ1 - line, pJ2 - column, t};
            }
        }
        return goptTable;
    }

    /**
     * La fonction qui regarde les cas triviaux puis resout les cas non triviaux.
     * @param pJ1 Le nombre de pierre du joueur 1.
     * @param pJ2 Le nombre de pierre du joueur 2.
     * @param posT La position du troll
     * @return La valeur du gain.
     */
    public double evalGopt(double pJ1, double pJ2, double posT) {
        //Initialement
        double[][][] goptTable = genGopt(pJ1, pJ2, posT);
        double[][] evalTable = new double[(int) pJ1][(int) pJ2];
        
        for (int line = 0; line < goptTable.length; line++) {
            for (int column = 1; column < goptTable[line].length; column = column + 2) {
                double j1 = goptTable[line][column][0];
                double j2 = goptTable[line][column][1];
                double pos = goptTable[line][column][2];
                double resDico = dico(j1, j2, pos);
                if (resDico != NOT_FOUND) {
                    evalTable[line][column] = resDico;
                } else {
                    if (this.map.containsKey(j1 +","+ j2+","+ pos)) {
                        evalTable[line][column] = map.get(j1 +","+ j2+","+ pos);
                    } else {
                        evalTable[line][column] = evalGopt(j1, j2, pos);
                        this.map.put(j1 +","+ j2+","+ pos, evalTable[line][column]);
                    }
                }
            }
        }
        ArrayList<Integer> linesNumber = new ArrayList<>();
        double[][] withoutDomine = dominate(evalTable, linesNumber);
        double[] listeProb = simplexResolver(withoutDomine);
        
        if (listeProb.length > 0) {
            double t = listeProb[0];
            if (this.head.equals(pJ1 + "-" + pJ2 + "-" + posT)) {
                this.rocks = throwRocks(listeProb, linesNumber);
            }
            return t;
        } else {
            return -4;
        }
    }

    /**
     * La fonction qui détermine le nombre de pierres à jouer selon une proba sur les pierres possible
     * @param listeProb Le tableau de probabiliter
     * @param linesNumber Les lignes dominées
     * @return Le nombre de pierre à jeter
     */
    public int throwRocks(double[] listeProb, ArrayList<Integer> linesNumber) {
        double[] weights = new double[listeProb.length -1];
        for(int i = 1; i < listeProb.length ; i++) {
            weights[i-1] = Math.round(listeProb[i] * 1000.0);
        }
        ArrayList<Integer> probaTable = new ArrayList<>();
        for(int w = 0 ; w < weights.length ; w++) {
            for(int i = 0; i < weights[w]; i++) {
                probaTable.add((linesNumber.get(w) + 1));
            }   
        }

        Random random = new Random();
        int rock = random.nextInt(probaTable.size() + 1) ;
        if(rock >= probaTable.size()) {
            rock = probaTable.size() - 1;
        }
        return probaTable.get(rock);
    }

    /**
     * La fonction qui élimine les lignes et colonnes dominées.
     * @param evalTable Le tableau à filtrer.
     * @param linesNumber La liste des lignes supprimées.
     * @return Le tableau sans les lignes et colonnes dominées
     */
    public double[][] dominate(double[][] evalTable, ArrayList<Integer> linesNumber)
    {
        double[][] evalTableWithoutLines = deleteLine(evalTable,linesNumber);
        return deleteColumn(evalTableWithoutLines);
    }

    /**
     * Supprimer les lignes dominées.
     * @param evalTable Le tableau à filtrer.
     * @param linesNumber La liste de lignes supprimées.
     * @return Le tableau filtré.
     */
    public double[][] deleteLine(double[][]evalTable, ArrayList<Integer> linesNumber) {
        ArrayList<Integer> LinesToDelete = new ArrayList<>();
        for (int line = 0; line < evalTable.length; line ++) {
            boolean deleteLine = false;
            boolean finished = false;
            int counter = 0;
            while (!finished && counter < evalTable.length) {
                if (counter != line && !LinesToDelete.contains(counter)) {
                    boolean delete = true;
                    for (int column = 0; column < evalTable[line].length; column ++) {
                        double value =  evalTable[line][column];
                        double valueBis = evalTable[counter][column];
                        if (value > valueBis) {
                            delete = false;
                            break;
                        }
                    }
                    if (delete) {
                        deleteLine = true;
                        finished = true;
                    }
                }
                counter ++;
            }
            if (deleteLine) {
                LinesToDelete.add(line);
            } else {
                linesNumber.add(line);
            }
        }
        //Construction du tableau que l'on retourne
        double[][] result;
        if (LinesToDelete.size() != 0) {
            result = new double[evalTable.length - LinesToDelete.size()][];
            int countLine = 0;
            for(int line = 0; line < evalTable.length; line ++) {
                if (!LinesToDelete.contains(line)) {
                    result[countLine] = evalTable[line];
                    countLine++;
                }
            }
        } else {
            result = evalTable;
        }
        return result;
    }

    /**
     * Supprime les colonnes ou J2 domine.
     * @param evalTable Le tableau à filtrer.
     * @return Le tableau filtré.
     */
    public double[][] deleteColumn(double[][]evalTable) {
        ArrayList<Integer> ColumnsToDelete = new ArrayList<>();
        int nbColumns = 0;
        if (evalTable.length > 0) {
            nbColumns = evalTable[0].length;
            for (int column = 0; column < nbColumns; column ++) {
                boolean deleteColumn = false;
                boolean finished = false;
                int counter = 0;
                while (!finished && counter < nbColumns) {
                    if (counter != column && !ColumnsToDelete.contains(counter)) {
                        boolean delete = true;
                        for (int line = 0; line < evalTable.length; line ++) {
                            double value =  evalTable[line][column];
                            double valueBis = evalTable[line][counter];
                            if ((value * -1) > (valueBis * -1)) {
                                delete = false;
                                break;
                            }
                        }
                        if (delete) {
                            deleteColumn = true;
                            finished = true;
                        }
                    }
                    counter ++;
                }
                if (deleteColumn) {
                    ColumnsToDelete.add(column);
                }
            }
        }
        //Construction du tableau que l'on retourne
        double[][] result;
        if (ColumnsToDelete.size() != 0) {
            result = new double[evalTable.length][nbColumns - ColumnsToDelete.size()];
            for(int line = 0; line < evalTable.length; line ++) {
                int countColumn = 0;
                for(int column = 0; column < evalTable[line].length; column++) {
                    if (!ColumnsToDelete.contains(column)) {
                        result[line][countColumn] = evalTable[line][column];
                        countColumn++;
                    }  
                }
            }
        } else {
            result = evalTable;
        }
        return result;
    }

    /**
     * La fonction qui gère le simplex
     * @param evalTable Le tableau à analyser à l'aide du simplex.
     * @return Les coodonnées de la case à jouer.
     */
    public double[] simplexResolver(double[][] evalTable) {
        int nbColumns = 0;
        if (evalTable.length > 0) {
            nbColumns = evalTable[0].length;
            double[][] TableA = new double[nbColumns + 3][evalTable.length];
            //On ecrit les equations pour chaque colonnes
            for (int column = 0; column < nbColumns; column ++) {
                int counter = 0;
                while (counter < nbColumns) {
                    double[] columnA = new double[evalTable.length + 1];
                    columnA[0] = -1;
                    for (int line = 0; line < evalTable.length; line ++) {
                        double value =  evalTable[line][column];
                        columnA[line+1] = value;
                    }
                    counter ++;
                    TableA[column] = columnA;
                }
            }
            // on ajoute la ligne x1 + x2 + ... + Xn = 1 
            double[] columnA = new double[evalTable.length + 1];
            columnA[0] = 0;
            for (int line = 0; line < evalTable.length; line ++) {
                columnA[line+1] = 1;
            }
            TableA[nbColumns] = columnA;
            // on ajoute la ligne Upper 
            double[] columnUpper = new double[evalTable.length + 1];
            for (int line = 0; line < evalTable.length+1; line ++) {
                columnUpper[line] = NaN;
            }
            TableA[nbColumns+1] = columnUpper;
            // on ajoute la ligne Lower 
            double[] columnLower = new double[evalTable.length + 1];
            columnLower[0] = NaN;
            for (int line = 1; line < evalTable.length+1; line ++) {
                columnLower[line] = 0;
            }
            TableA[nbColumns+2] = columnLower;
            
            double[] TableB = new double[nbColumns + 3];
            for (int line = 0; line < nbColumns; line ++) {
                TableB[line] = 0;
            }
            TableB[nbColumns] = 1;
            TableB[nbColumns+1] = NaN;
            TableB[nbColumns+2] = NaN;
            
            double[] TableC = new double[evalTable.length + 1];
            TableC[0] = 1;
            for (int line = 1; line < evalTable.length + 1; line ++) {
                TableC[line] = 0;
            }
            
            ConsType[] TableConsType = new ConsType[nbColumns + 3];
            for (int line = 0; line < nbColumns; line ++) {
                TableConsType[line] = ConsType.GE;
            }
            TableConsType[nbColumns] = ConsType.EQ;
            TableConsType[nbColumns+1] = ConsType.UPPER;
            TableConsType[nbColumns+2] = ConsType.LOWER;

            try {
                return simplex(TableA, TableB, TableC, TableConsType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new double[] {-3.0};
    }

    /**
     * Fonction qui résout le simplex.
     * @param A Le tableau qui contient coefficients de chaque xi.
     * @param b Le tableau qui contient t représentant le gain maximum.
     * @param c Le tableau qui contient le gain de chaque lancé possible.
     * @param rel Le tableau de ConsType.
     * @return Le simplex résolu.
     * @throws Exception
     */
    public double[] simplex(double[][] A, double[] b, double[] c, ConsType[] rel) throws Exception{

        double[] solutions;
        LinearObjectiveFunction f = new LinearObjectiveFunction(c, GoalType.MAX);
 
        ArrayList< Constraint > constraints = new ArrayList<>();
        for(int i=0; i < A.length; i++) {
            constraints.add(new Constraint(A[i], rel[i], b[i]));
        }
        
        LogManager.getLogManager().reset();
        LP lp = new LP(f,constraints);
        SolutionType solution_type=lp.resolve();
 
        if(solution_type==SolutionType.OPTIMUM) { 
            Solution solution=lp.getSolution();
            solutions = new double[solution.getVariables().length];
            int counter = 0;
            for(Variable var : solution.getVariables()) {
                solutions[counter] = var.getValue();
                counter++;
            }
            return solutions;
        }
        return new double[]{-2.0};
    }
}
