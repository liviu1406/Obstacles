import java.util.*;

//Defining a set of states and their connections
public class StateConnectionsUtil {
    public static final State A = new State("A", 5);
    public static final State B = new State("B", 6);
    public static final State C = new State("C", 8);
    public static final State D = new State("D", 4);
    public static final State E = new State("E", 4);
    public static final State F = new State("F", 5);
    public static final State G = new State("G", 2);
    public static final State H = new State("H", 0);

    static {
        A.connections.add(new Connection(B, 3));
        A.connections.add(new Connection(C, 3));
        B.connections.add(new Connection(D, 2));
        C.connections.add(new Connection(F, 3));
        D.connections.add(new Connection(E, 4));
        E.connections.add(new Connection(F, 1));
        E.connections.add(new Connection(G, 2));
        F.connections.add(new Connection(G, 3));
        G.connections.add(new Connection(H, 2));
    }

    public static List<State> getAllStates() {
        return Arrays.asList(A, B, C, D, E, F, G, H);
    }

    //Finding a state by its name
    public static State getStateByName(String name) {
        return switch (name) {
            case "A" -> A;
            case "B" -> B;
            case "C" -> C;
            case "D" -> D;
            case "E" -> E;
            case "F" -> F;
            case "G" -> G;
            case "H" -> H;
            default -> null;
        };
    }

}

