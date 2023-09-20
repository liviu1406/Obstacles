import java.util.*;

public class AStarSearch {

    public static void main(String[] args) {
        State A = StateConnectionsUtil.A; // Access starting state from StateConnectionsUtil

        Map<State, Integer> gValues = SearchUtils.initializeGValues(A);
        PriorityQueue<State> frontier = SearchUtils.initializeFrontier(A, gValues);


        Set<State> explored = new HashSet<>();
        List<String> progression = new ArrayList<>();

        System.out.println("RESULT");

        while (!frontier.isEmpty()) {
            State current = frontier.poll();
            explored.add(current);
            progression.add(current.name);

            System.out.println("Expand: " + current.name + ", f=" + (gValues.get(current) + current.heuristic));
            System.out.println("Frontier: " + frontierToString(frontier, gValues));

            if (current == StateConnectionsUtil.H) {
                System.out.println("Reached goal state: " + current.name);
                System.out.println("The path taken is: " + progression);
                System.out.println("The cost of this path is: " + gValues.get(current));
                return;
            }

            for (Connection conn : current.connections) {
                State neighbor = conn.state;
                int tentativeGValue = gValues.get(current) + conn.cost;
                if (!explored.contains(neighbor) && !frontier.contains(neighbor)) {
                    gValues.put(neighbor, tentativeGValue);
                    frontier.add(neighbor);
                } else if (tentativeGValue < gValues.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    frontier.remove(neighbor);
                    gValues.put(neighbor, tentativeGValue);
                    frontier.add(neighbor);
                }
            }
        }

        System.out.println("Goal state not reached");
    }

    private static String frontierToString(PriorityQueue<State> frontier, Map<State, Integer> gValues) {
        List<String> states = new ArrayList<>();
        for (State state : frontier) {
            states.add(state.name + "(f=" + (gValues.get(state) + state.heuristic) + ")");
        }
        return states.toString();
    }
}
