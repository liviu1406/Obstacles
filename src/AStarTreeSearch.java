import java.util.*;

class NodeTree {
    public String name;
    public int g;
    public int h;
    public NodeTree parent;

    public NodeTree(String name, int h) {
        this.name = name;
        this.h = h;
        this.g = Integer.MAX_VALUE;
        this.parent = null;
    }

    public int f() {
        return g + h;
    }
}

public class AStarTreeSearch {
    private static Map<String, NodeTree> nodes = new HashMap<>();
    private static Map<String, List<NodeTree>> adjList = new HashMap<>();
    private static Map<String, Map<String, Integer>> stepCosts = new HashMap<>();

    public static void main(String[] args) {
        initializeGraph();
        NodeTree start = nodes.get("A");
        NodeTree goal = nodes.get("H");
        List<NodeTree> path = aStarTreeSearch(start, goal);
        printPath(path);
    }

    private static void initializeGraph() {
        nodes.put("A", new NodeTree("A", 5));
        nodes.put("B", new NodeTree("B", 6));
        nodes.put("C", new NodeTree("C", 8));
        nodes.put("D", new NodeTree("D", 4));
        nodes.put("E", new NodeTree("E", 4));
        nodes.put("F", new NodeTree("F", 5));
        nodes.put("G", new NodeTree("G", 2));
        nodes.put("H", new NodeTree("H", 0));

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

    private static List<NodeTree> aStarTreeSearch(NodeTree start, NodeTree goal) {
        PriorityQueue<NodeTree> openList = new PriorityQueue<>(Comparator.comparingInt(NodeTree::f));

        start.g = 0;
        openList.add(start);

        while (!openList.isEmpty()) {
            NodeTree current = openList.poll();

            if (current.name.equals(goal.name)) {
                return reconstructPath(current);
            }

            for (NodeTree neighbor : adjList.get(current.name)) {
                NodeTree child = new NodeTree(neighbor.name, neighbor.h);
                child.g = current.g + stepCosts.get(current.name).get(neighbor.name);
                child.parent = current;

                openList.add(child);
            }
        }

        return null; // No path found
    }

    private static List<NodeTree> reconstructPath(NodeTree node) {
        List<NodeTree> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private static void printPath(List<NodeTree> path) {
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
