import java.util.ArrayList;

import it.ssc.pl.milp.ConsType;
import it.ssc.pl.milp.Constraint;
import it.ssc.pl.milp.GoalType;
import it.ssc.pl.milp.LP;
import it.ssc.pl.milp.LinearObjectiveFunction;
import it.ssc.pl.milp.Solution;
import it.ssc.pl.milp.SolutionType;
import it.ssc.pl.milp.Variable;
import static it.ssc.pl.milp.LP.NaN;

import java.util.HashMap;
import java.util.Random;
import java.util.logging.LogManager;


/**
 * This class inherits the Strategy class.<br>
 * This class was created as part of the university project.
 * This class is characterized by :
 * <ul>
 *     <li>A constant when there are no results found.</li>
 *     <li>A map of moves played.</li>
 *     <li>A string representing the head.</li>
 *     <li>The number of stones thrown by the player.</li>
 * </ul>
 * @author B2RJ et Pierre SABARD
 */
public class StrategySafe extends Strategy {

    /**
     * The constant that determines when we haven't found a match.
     */
    private final double NOT_FOUND = 404;

    /**
     * The shot history map.
     * It allows you to search only once per situation.
     */
    private HashMap<String, Double> map = new HashMap<>();

    /**
     * The character string that goes into map.
     */
    private String head;

    /**
     * The number of stones thrown by the player.
     */
    private int rocks;

    /**
     * The builder of the PrudentStrategy class.
     * The builder iniatilizes the number of stones played to 1.
     * @param p The board that contains the number of stones and squares.
     */
    public StrategySafe(Board p) {
        super(p);
        this.rocks = 1;
    }

    /**
     * Geter of the map map.
     * @return The map.
     */
    public HashMap<String, Double> getMap() {
        return this.map;
    }

    /**
     * Seter of the map.
     * @param m The new map.
     */
    public void setMap(HashMap<String, Double> m) {
        this.map = m;
    }

    /**
     * The function that plays strategy.
     * @param pJ1 The number of stones of player 1.
     * @param pJ2 The number of stone of player 2.
     * @param posT The position of the Troll.
     * @return The number of stones. played.
     */
    @Override
    public int play(double pJ1, double pJ2, double posT) {
        this.head = pJ1 + "-" + pJ2 + "-" + posT;
        evalGopt(pJ1, pJ2, posT);
        return this.rocks;
    }

    /**
     * The function that looks for simple cases in the table.
     * @param pJ1 The number of stones of player 1.
     * @param pJ2 The number of stone of player 2.
     * @param posT The position of the Troll
     * @return The result of the confrontation.
     * <ul>
     *  <li> 1 if the player wins the battle </li>
     *  <li>-1 if the player loses the battle </li>
     *  <li> 0 if there's a tie</li>
     *  <li>NOT_FOUND if the case is not trivial</li>
     * </ul>
     */
    public double dico(double pJ1, double pJ2, double posT) {
        //If Player 1 win
        int nbBoxes = super.getBoard().getBoxes();
        if (posT == ((nbBoxes - 1)/2)) {
            return 1;
        }
        if (posT == -((nbBoxes - 1)/2)) {
            return -1;
        }

        //If Player 1 has no more stones
        if (pJ1 == 0) {
            if (posT - pJ2 > 0) {
                return 1;
            } else if (posT - pJ2 == 0) {
                return 0;
            } else {
              return -1;
            }
        }
        //If Player 2 has no more stones
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
     * The function that generates the table according to the parameters.
     * @param pJ1 The number of stone of player 1.
     * @param pJ2 The number of stone of player 2.
     * @param posT The position of the troll.
     * @return A 3D table with the calculation of the winnings.
     */
    public double[][][] genGopt(double pJ1, double pJ2, double posT) {
        double[][][] goptTable = new double[(int) pJ1][(int) pJ2][3];
        for (int line = 1; line <= pJ1; line++) {
            for (int column = 1; column <= pJ2; column++) {
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
     * The function that looks at trivial cases and then solves non-trivial cases.
     * @param pJ1 The number of stone of player 1.
     * @param pJ2 The number of stone of the player 2.
     * @param posT The position of the troll
     * @return The value of the gain.
     */
    public double evalGopt(double pJ1, double pJ2, double posT) {
        double[][][] goptTable = genGopt(pJ1, pJ2, posT);
        double[][] evalTable = new double[(int) pJ1][(int) pJ2];
        
        for (int line = 0; line < goptTable.length; line++) {
            for (int column = 0; column < goptTable[line].length; column++) {
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
     * The function that determines the number of gems to play based on a probability on the possible gems.
     * @param listProb The probability table
     * @param linesNumber The dominated lines
     * @return The number of stone to throw
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
     * The function that eliminates dominated rows and columns.
     * @param evalTable The array to filter.
     * @param linesNumber The list of deleted lines.
     * @return The table without the dominated rows and columns.
     */
    public double[][] dominate(double[][] evalTable, ArrayList<Integer> linesNumber)
    {
        double[][] evalTableWithoutLines = deleteLine(evalTable,linesNumber);
        return deleteColumn(evalTableWithoutLines);
    }

    /**
     * Delete dominated lines.
     * @param evalTable The table to be filtered.
     * @param linesNumber The list of deleted lines.
     * @return The filtered table.
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
     * Deletes columns where Player 2 dominates.
     * @param evalTable The array to be filtered.
     * @return The filtered table.
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
     * The function that handles the simplex
     * @param evalTable The table to be analyzed using the simplex.
     * @return The coordinates of the square to be played.
     */
    public double[] simplexResolver(double[][] evalTable) {
        int nbColumns = 0;
        if (evalTable.length > 0) {
            nbColumns = evalTable[0].length;
            double[][] TableA = new double[nbColumns + 3][evalTable.length];
            //We write the equation for each column
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
            // we add the line x1 + x2 + ... + Xn = 1
            double[] columnA = new double[evalTable.length + 1];
            columnA[0] = 0;
            for (int line = 0; line < evalTable.length; line ++) {
                columnA[line+1] = 1;
            }
            TableA[nbColumns] = columnA;
            // we add the line Upper
            double[] columnUpper = new double[evalTable.length + 1];
            for (int line = 0; line < evalTable.length+1; line ++) {
                columnUpper[line] = NaN;
            }
            TableA[nbColumns+1] = columnUpper;
            // we add the line Lower
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
     * Function that solves simplex.
     * @param A The array that contains coefficients of each xi.
     * @param b The table that contains t representing the maximum gain.
     * @param c The table that contains the gain of each possible throw.
     * @param rel The array of ConsType.
     * @return The resolved simplex.
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
