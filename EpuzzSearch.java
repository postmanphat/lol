/**
 * EpuzzSearch.java
 *
 * search for 8 puzzle problems
 * @author Adam Bird
 */

import java.util.*;

public class EpuzzSearch extends Search {

    private int[][] targetState;

    public EpuzzSearch() {
        targetState = new int[][]{{1,2,3}, {4, 5, 6}, {7, 8, 0}};
    }

    public int[][] getTarget() {
        return targetState;
    }
}