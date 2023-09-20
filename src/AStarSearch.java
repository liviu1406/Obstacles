import java.util.*;

class Node {
    public String name;
    public int g;
    public int h;
    public Node parent;

    public Node(String name, int h) {
        this.name = name;
        this.h = h;
        this.g = Integer.MAX_VALUE;
        this.parent = null;
    }

    public int f() {
        return g + h;
    }
}

public class AStarSearch {
    private static Map<String, Node> nodes = new HashMap<>();
    private static Map<String, List<Node>> adjList = new HashMap<>();
    private static Map<String, Map<String, Integer>> stepCosts = new HashMap<>();

    public static void main(String[] args) {
        initializeGraph();
        Node start = nodes.get("A");
        Node goal = nodes.get("H");
        List<Node> path = aStarSearch(start, goal);
        printPath(path);
    }

    private static void initializeGraph() {
        nodes.put("A", new Node("A", 5));
        nodes.put("B", new Node("B", 6));
        nodes.put("C", new Node("C", 8));
        nodes.put("D", new Node("D", 4));
        nodes.put("E", new Node("E", 4));
        nodes.put("F", new Node("F", 5));
        nodes.put("G", new Node("G", 2));
        nodes.put("H", new Node("H", 0));

        addEdge("A", "B", 3);
        addEdge("A", "C", 3);
        addEdge("B", "D", 2);
        addEdge("C", "F", 3);
        addEdge("D", "E", 4);
        addEdge("E", "F", 1);
        addEdge("E", "G", 2);
        addEdge("F", "G", 3);
        addEdge("G", "H", 2);
    }

    private static void addEdge(String from, String to, int cost) {
        adjList.computeIfAbsent(from, k -> new ArrayList<>()).add(nodes.get(to));
        adjList.computeIfAbsent(to, k -> new ArrayList<>()).add(nodes.get(from));

        stepCosts.computeIfAbsent(from, k -> new HashMap<>()).put(to, cost);
        stepCosts.computeIfAbsent(to, k -> new HashMap<>()).put(from, cost);
    }

    private static List<Node> aStarSearch(Node start, Node goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(Node::f));
        Set<Node> closedList = new HashSet<>();

        start.g = 0;
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.equals(goal)) {
                return reconstructPath(current);
            }

            closedList.add(current);

            for (Node neighbor : adjList.get(current.name)) {
                if (closedList.contains(neighbor)) {
                    continue;
                }

                int tentativeG = current.g + stepCosts.get(current.name).get(neighbor.name);
                if (tentativeG < neighbor.g) {
                    neighbor.parent = current;
                    neighbor.g = tentativeG;

                    if (openList.contains(neighbor)) {
                        openList.remove(neighbor);
                    }

                    openList.add(neighbor);
                }
            }
        }

        return null; // No path found
    }

    private static List<Node> reconstructPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private static void printPath(List<Node> path) {
        if (path == null) {
            System.out.println("No path found.");
            return;
        }

        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i).name);
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println("\nTotal Cost: " + path.get(path.size() - 1).g);
    }
}
