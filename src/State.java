import java.util.ArrayList;
import java.util.List;

public class State {
    String name;
    int heuristic;
    List<Connection> connections = new ArrayList<>();

    public State(String name, int heuristic) {
        this.name = name;
        this.heuristic = heuristic;
    }
}

