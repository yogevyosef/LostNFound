/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package Controller;

import java.util.Arrays;

public class HungarianAlgo {
    private final double[][] priorityMatrix;
    private final int rows, cols, dim;
    private final double[] labelByFound, labelByLost;
    private final int[] minSlackFoundByLost;
    private final double[] minSlackValueByLost;
    private final int[] matchLostByFound, matchFoundByLost;
    private final int[] parentFoundByCommittedLost;
    private final boolean[] committedFounds;


    /*********************************************************************************************
     Function Name: HungarianAlgo - C'tor
     Input: double[][] priorityMatrix
     Output: none
     Description: Construct an instance of the algorithm
     ********************************************************************************************/
    public HungarianAlgo(double[][] priorityMatrix) {
        this.dim = Math.max(priorityMatrix.length, priorityMatrix[0].length);
        this.rows = priorityMatrix.length;
        this.cols = priorityMatrix[0].length;
        this.priorityMatrix = new double[this.dim][this.dim];
        // make a copy of the matrix with zeros rows in the bottom
        for (int found = 0; found < this.dim; found++) {
            if (found < priorityMatrix.length)
                this.priorityMatrix[found] = Arrays.copyOf(priorityMatrix[found], this.dim);
            else
                this.priorityMatrix[found] = new double[this.dim];

        }
        labelByFound = new double[this.dim];
        labelByLost = new double[this.dim];
        minSlackFoundByLost = new int[this.dim];
        minSlackValueByLost = new double[this.dim];
        committedFounds = new boolean[this.dim];
        parentFoundByCommittedLost = new int[this.dim];
        matchLostByFound = new int[this.dim];
        Arrays.fill(matchLostByFound, -1);
        matchFoundByLost = new int[this.dim];
        Arrays.fill(matchFoundByLost, -1);
    }


    /*********************************************************************************************
     Function Name: execute
     Input: none
     Output: int[]
     Description: execute the algorithm - return the minimum matching of lost and founds
     ********************************************************************************************/
    public int[] execute() {
        reduce();
        computeInitialFeasibleSolution();
        greedyMatch();

        int found = fetchUnmatchedWorker();
        while (found < dim) {
            initializePhase(found);
            executePhase();
            found = fetchUnmatchedWorker();
        }
        int[] result = Arrays.copyOf(matchLostByFound, rows);
        for (found = 0; found < result.length; found++) {
            if (result[found] >= cols) {
                result[found] = -1;
            }
        }
        return result;
    }


    /*********************************************************************************************
     Function Name: initializePhase
     Input: int found
     Output: none
     Description: execute the algorithm - return the minimum matching of lost and founds
     ********************************************************************************************/
    protected void initializePhase(int found) {
        Arrays.fill(committedFounds, false);
        Arrays.fill(parentFoundByCommittedLost, -1);
        committedFounds[found] = true;
        for (int lost = 0; lost < dim; lost++) {
            minSlackValueByLost[lost] = priorityMatrix[found][lost] - labelByFound[found]
                    - labelByLost[lost];
            minSlackFoundByLost[lost] = found;
        }
    }


    /*********************************************************************************************
     Function Name: executePhase
     Input: none
     Output: none
     Description: execute a single phase of the hungarian algorithm
     ********************************************************************************************/
    protected void executePhase() {
        while (true) {
            int minSlackWorker = -1, minSlackJob = -1;
            double minSlackValue = Double.POSITIVE_INFINITY;
            for (int lost = 0; lost < dim; lost++) {
                if (parentFoundByCommittedLost[lost] == -1) {
                    if (minSlackValueByLost[lost] < minSlackValue) {
                        minSlackValue = minSlackValueByLost[lost];
                        minSlackWorker = minSlackFoundByLost[lost];
                        minSlackJob = lost;
                    }
                }
            }
            if (minSlackValue > 0) {
                updateLabeling(minSlackValue);
            }
            parentFoundByCommittedLost[minSlackJob] = minSlackWorker;
            if (matchFoundByLost[minSlackJob] == -1) {
                /*
                 * An augmenting path has been found.
                 */
                int committedJob = minSlackJob;
                int parentWorker = parentFoundByCommittedLost[committedJob];
                while (true) {
                    int temp = matchLostByFound[parentWorker];
                    match(parentWorker, committedJob);
                    committedJob = temp;
                    if (committedJob == -1) {
                        break;
                    }
                    parentWorker = parentFoundByCommittedLost[committedJob];
                }
                return;
            } else {
                /*
                 * Update slack values since we increased the size of the committed
                 * workers set.
                 */
                int worker = matchFoundByLost[minSlackJob];
                committedFounds[worker] = true;
                for (int lost = 0; lost < dim; lost++) {
                    if (parentFoundByCommittedLost[lost] == -1) {
                        double slack = priorityMatrix[worker][lost] - labelByFound[worker]
                                - labelByLost[lost];
                        if (minSlackValueByLost[lost] > slack) {
                            minSlackValueByLost[lost] = slack;
                            minSlackFoundByLost[lost] = worker;
                        }
                    }
                }
            }
        }
    }


    /*********************************************************************************************
     Function Name: fetchUnmatchedWorker
     Input: none
     Output: int
     Description: return the first unmatched found
     ********************************************************************************************/
    protected int fetchUnmatchedWorker() {
        int found;
        for (found = 0; found < dim; found++) {
            if (matchLostByFound[found] == -1) {
                break;
            }
        }
        return found;
    }


    /*********************************************************************************************
     Function Name: reduce
     Input: none
     Output: none
     Description: reduce the matrix by subtracting the smallest element of each row and column from
     all elements of the row and column
     ********************************************************************************************/
    protected void reduce() {
        // reduce from rows
        for (int found = 0; found < dim; found++) {
            double min = Double.POSITIVE_INFINITY;
            for (int lost = 0; lost < dim; lost++) {
                if (priorityMatrix[found][lost] < min) {
                    min = priorityMatrix[found][lost];
                }
            }
            for (int lost = 0; lost < dim; lost++) {
                priorityMatrix[found][lost] -= min;
            }
        }
        // reduce from columns
        double[] min = new double[dim];
        for (int lost = 0; lost < dim; lost++) {
            min[lost] = Double.POSITIVE_INFINITY;
        }
        for (int found = 0; found < dim; found++) {
            for (int lost = 0; lost < dim; lost++) {
                if (priorityMatrix[found][lost] < min[lost]) {
                    min[lost] = priorityMatrix[found][lost];
                }
            }
        }
        for (int found = 0; found < dim; found++) {
            for (int lost = 0; lost < dim; lost++) {
                priorityMatrix[found][lost] -= min[lost];
            }
        }
    }


    /*********************************************************************************************
     Function Name: computeInitialFeasibleSolution
     Input: none
     Output: none
     Description: Compute an initial feasible solution by assigning zero labels to the founds
     and by assigning to each lost a label equal to the minimum cost among its incident edges.
     ********************************************************************************************/
    protected void computeInitialFeasibleSolution() {
        for (int lost = 0; lost < dim; lost++) {
            labelByLost[lost] = Double.POSITIVE_INFINITY;
        }
        for (int found = 0; found < dim; found++) {
            for (int lost = 0; lost < dim; lost++) {
                if (priorityMatrix[found][lost] < labelByLost[lost]) {
                    labelByLost[lost] = priorityMatrix[found][lost];
                }
            }
        }
    }


    /*********************************************************************************************
     Function Name: greedyMatch
     Input: none
     Output: none
     Description: find a valid matching by greedily selecting among zero-cost matchings.
     ********************************************************************************************/
    protected void greedyMatch() {
        for (int found = 0; found < dim; found++) {
            for (int lost = 0; lost < dim; lost++) {
                if (matchLostByFound[found] == -1 && matchFoundByLost[lost] == -1
                        && priorityMatrix[found][lost] - labelByFound[found] - labelByLost[lost] == 0) {
                    match(found, lost);
                }
            }
        }
    }


    /*********************************************************************************************
     Function Name: match
     Input: int found, int lost
     Output: none
     Description: helper method to record a matching between found and lost
     ********************************************************************************************/
    protected void match(int found, int lost) {
        matchLostByFound[found] = lost;
        matchFoundByLost[lost] = found;
    }


    /*********************************************************************************************
     Function Name: updateLabeling
     Input: double slack
     Output: none
     Description: Update labels with the specified slack by adding the slack value for committed
     founds and by subtracting the slack value for committed lost.
     In addition, update the minimum slack values appropriately.
     ********************************************************************************************/
    protected void updateLabeling(double slack) {
        for (int found = 0; found < dim; found++) {
            if (committedFounds[found]) {
                labelByFound[found] += slack;
            }
        }
        for (int lost = 0; lost < dim; lost++) {
            if (parentFoundByCommittedLost[lost] != -1) {
                labelByLost[lost] -= slack;
            } else {
                minSlackValueByLost[lost] -= slack;
            }
        }
    }
}
