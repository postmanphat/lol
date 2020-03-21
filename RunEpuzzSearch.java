import sheffield.*;
import java.util.*;

public class RunEpuzzSearch {
    public static void main(String[] arg) {

        // create an EasyWriter
        EasyWriter screen = new EasyWriter();

        // create the searcher
        EpuzzSearch searcher = new EpuzzSearch();

        EpuzzGen generator = new EpuzzGen(69420);
        int[][] puzzle = generator.puzzGen(8);
        // create the initial state, cast it as SearchState
        SearchState initState = (SearchState) new EpuzzState(puzzle);
        // breadth first search
        String resas = searcher.runSearch(initState, "AStar");
        screen.println(resas);

    }
}