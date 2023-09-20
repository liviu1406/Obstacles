import java.util.*;

public class SearchUtils {

    // Initialising the g-values for each state in a search algorithm.
    public static Map<State, Integer> initializeGValues(State startState) {
        Map<State, Integer> gValues = new HashMap<>();
        gValues.put(startState, 0);
        return gValues;
    }


    // Initialising the frontier, which is a priority queue of states, for a search algorithm.
    public static PriorityQueue<State> initializeFrontier(State startState, Map<State, Integer> gValues) {
        PriorityQueue<State> frontier = new PriorityQueue<>(Comparator.comparingInt(node -> gValues.getOrDefault(node, Integer.MAX_VALUE) + node.heuristic));
        frontier.add(startState);
        return frontier;
    }
}

