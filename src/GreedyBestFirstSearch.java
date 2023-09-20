import java.util.*;

public class GreedyBestFirstSearch {

    public static void main(String[] args) {
        State A = StateConnectionsUtil.A; // Access starting state from StateConnectionsUtil

        // Greedy Best-First Search
        Set<State> explored = new HashSet<>();
        PriorityQueue<State> frontier = new PriorityQueue<>(Comparator.comparingInt(s -> s.heuristic));
        frontier.add(A);

        System.out.println("RESULT");
        List<String> progression = new ArrayList<>();

        while (!frontier.isEmpty()) {
            // Print the current frontier first
            System.out.println("Frontier: " + frontierToString(frontier));
            State current = frontier.poll();
            assert current != null;
            progression.add(current.name);

            if (!current.name.equals("H")) {
                System.out.println("Expand " + current.name + " (lowest heuristic).");
            }

            explored.add(current);

            if (current.name.equals("H")) {
                int pathCost = computePathCost(progression);
                System.out.println("Reached goal state: " + current.name);
                System.out.println("The path taken is: " + progression);
                System.out.println("The cost of this path is: " + pathCost);
                return;
            }

            for (Connection conn : current.connections) {
                if (!explored.contains(conn.state) && !frontier.contains(conn.state)) {
                    frontier.add(conn.state);
                }
            }
        }

        System.out.println("Goal state not reached");
    }

    private static int computePathCost(List<String> progression) {
        int pathCost = 0;
        for (int i = 0; i < progression.size() - 1; i++) {
            State currentState = StateConnectionsUtil.getStateByName(progression.get(i));
            State nextState = StateConnectionsUtil.getStateByName(progression.get(i + 1));
            for (Connection conn : currentState.connections) {
                if (conn.state == nextState) {
                    pathCost += conn.cost;
                    break;
                }
            }
        }
        return pathCost;
    }

    private static String frontierToString(PriorityQueue<State> frontier) {
        List<String> states = new ArrayList<>();
        for (State state : frontier) {
            states.add(state.name + "(h=" + state.heuristic + ")");
        }
        return states.toString();
    }
}
