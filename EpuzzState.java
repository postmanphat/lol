/**
 * EpuzzState.java
 *
 * state in a 8 puzzle problem
 * @author Adam Bird
 */

import java.util.*;

public class EpuzzState extends SearchState {

    private final int[][] initialState;
    private int[][] currentState;
    private int localCost;
    private int estRemCost;
    private enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }
    /**
     * constructor
     *
     * @param initialState initial state of space to search for target from
     */
    public EpuzzState(int[][] initState) {
        this.initialState = initState;
        this.currentState = initState;
        this.estRemCost = getEstRemCost(initialState);
        this.localCost = 1;
    }

    public int[][] getInitialState() {
        return initialState;
    }

    public int[][] getCurrentState() {
        return currentState;
    }

    public boolean goalP(Search searcher) {
        EpuzzSearch EPsearcher = (EpuzzSearch) searcher;
        int[][] target = EPsearcher.getTarget();
        return equal(currentState, target);
    }

    public ArrayList<SearchState> getSuccessors(Search searcher) {
        EpuzzSearch EPsearcher = (EpuzzSearch) searcher;
        int[][] target = EPsearcher.getTarget();

        // the list of puzzle states
        ArrayList<EpuzzState> EPlis = new ArrayList<EpuzzState>();
        ArrayList<SearchState> slis = new ArrayList<SearchState>();

        //tile check code goes here
        int[] zeroIndeces = findZero(currentState);

        //int[][] stateCopy = initialState;

        //Checks if zero tile is in top/middle row
        if (zeroIndeces[0] <= 1) {
            EpuzzState newState = new EpuzzState(moveTile(zeroIndeces, Direction.DOWN));
//            newState.estRemCost = getEstRemCost(newState.getInitialState());
            EPlis.add(newState);
        }
        //Checks if zero in middle/bottom row
        if (zeroIndeces[0] >= 1) {
            EpuzzState newState = new EpuzzState(moveTile(zeroIndeces, Direction.UP));
            EPlis.add(newState);
        }
        //Checks if zero in left/middle column
        if (zeroIndeces[1] <= 1) {
            EpuzzState newState = new EpuzzState(moveTile(zeroIndeces, Direction.RIGHT));
//            newState.estRemCost = getEstRemCost(newState.getInitialState());
            EPlis.add(newState);
        }
        //Checks if zero in middle/right column
        if (zeroIndeces[1] >= 1) {
            EpuzzState newState = new EpuzzState(moveTile(zeroIndeces, Direction.LEFT));
//            newState.estRemCost = getEstRemCost(newState.getInitialState());
            EPlis.add(newState);
        }

        for (EpuzzState es : EPlis) {
            slis.add((SearchState) es);
        }
        return slis;

    }

    //Method to check if two 2d arrays are the same
    private boolean equal(final int[][] array1, final int[][] array2) {
        for (int i = 0; i < array1.length; i++) {
            if (!Arrays.equals(array1[i], array2[i])) {
                return false;
            }
        }
        return true;
    }

    private int[] findZero(int[][] tileArray) {
        for (int i = 0; i < tileArray.length; i++) {
            for (int j = 0; j < tileArray[i].length; j++) {
                if (tileArray[i][j] == 0) {
                    int[] zeroIndeces = new int[]{i, j};
                    return zeroIndeces;
                }
            }
        }
        return new int[2];
    }

    private int[][] moveTile(int[] zeroIndeces, Direction dir) {
        int temp;
        int[][] tempArray = copyArray(initialState);
        switch(dir) {
            case UP:
                temp = tempArray[zeroIndeces[0]-1][zeroIndeces[1]];
                tempArray[zeroIndeces[0]][zeroIndeces[1]] = temp;
                tempArray[zeroIndeces[0]-1][zeroIndeces[1]] = 0;
                break;
            case RIGHT:
                temp = tempArray[zeroIndeces[0]][zeroIndeces[1]+1];
                tempArray[zeroIndeces[0]][zeroIndeces[1]] = temp;
                tempArray[zeroIndeces[0]][zeroIndeces[1]+1] = 0;
                break;
            case DOWN:
                temp = tempArray[zeroIndeces[0]+1][zeroIndeces[1]];
                tempArray[zeroIndeces[0]][zeroIndeces[1]] = temp;
                tempArray[zeroIndeces[0]+1][zeroIndeces[1]] = 0;
                break;
            case LEFT:
                temp = tempArray[zeroIndeces[0]][zeroIndeces[1]-1];
                tempArray[zeroIndeces[0]][zeroIndeces[1]] = temp;
                tempArray[zeroIndeces[0]][zeroIndeces[1]-1] = 0;
                break;
        }
        return tempArray;

    }

    public int[][] copyArray(int[][] origin) {
        int[][] copy = Arrays.stream(origin).map(int[]::clone).toArray(int[][]::new);
        return copy;
    }

    public boolean sameState(SearchState s2) {
        EpuzzState ep2 = (EpuzzState)s2;
        return equal(ep2.getCurrentState(), currentState);
    }

    public int getEstRemCost(int[][] testState) {
        int cost = 0;
        EpuzzSearch EPsearcher = new EpuzzSearch();
        int[][] target = EPsearcher.getTarget();

        //false = hamming, true = manhattan
        boolean manhattan = false;
        if (manhattan) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int difference = Math.abs(testState[i][j] - target[i][j]);
                    cost += difference;
                }
            }
        }
        else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (!(testState[i][j] == target[i][j])) {
                            cost += 1;
                    }
                }
            }
        }
        return cost;
    }

    public String toString() {
        String string = new String();
        string = string + "\r\n" + (currentState[0][0] + " " + currentState[0][1] + " " + currentState[0][2]) + "\r\n" +
                          (currentState[1][0] + " " + currentState[1][1] + " " + currentState[1][2]) + "\r\n" +
                          (currentState[2][0] + " " + currentState[2][1] + " " + currentState[2][2]);
        return string;
    }
}