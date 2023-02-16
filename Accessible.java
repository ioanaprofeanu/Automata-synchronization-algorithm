// Profeanu Ioana, 333CA
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Class for solving the Accessible problem
 */
public class Accessible {
    // colors used for bfs
    public static final int WHITE = 0;
    public static final int GRAY = 1;
    public static final int BLACK = 2;
    // input data
    int no_states, no_symbols, no_source_states;
    ArrayList<Integer> source_states;
    ArrayList<ArrayList<Integer>> adjacency_list;
    // set for storing the accessible states
    SortedSet<Integer> accessible_states = new TreeSet<>();

    public Accessible(int no_states, int no_symbols, int no_source_states,
                      ArrayList<Integer> source_states,
                      ArrayList<ArrayList<Integer>> adjacency_list) {
        this.no_states = no_states;
        this.no_symbols = no_symbols;
        this.no_source_states = no_source_states;
        this.source_states = source_states;
        this.adjacency_list = adjacency_list;
    }

    /**
     * Method which uses a bfs algorithm to try to access all possible
     * states within the graph, starting from a given source
     * @param source the starting state
     */
    public void get_accessible_states(int source) {
        // colors for marking visited states
        int[] color = new int[no_states + 1];
        for (int i = 0; i < no_states; i++) {
            color[i] = WHITE;
        }

        // add the source state to the queue and to the
        // accessible states
        Vector<Integer> queue = new Vector<>();
        queue.add(source);
        color[source] = 1;
        accessible_states.add(source);

        // while the queue is not empty
        while (queue.size() != 0) {
            // pop element and visit its neighbors
            int current_state = queue.get(0);
            for (Integer symbol : adjacency_list.get(current_state)) {
                // check if the neighbor is not visited,
                // add it to queue and to accessible states
                if (color[symbol] == WHITE) {
                    color[symbol] = GRAY;
                    accessible_states.add(symbol);
                    queue.add(symbol);
                }
            }
            // mark neighbor as visited and add to queue
            color[current_state] = BLACK;
            queue.remove((Integer) current_state);
        }
    }

    /**
     * method which prints the accessible states within the graph
     */
    void print_accessible_states() {
        for (Integer accessible_state : accessible_states) {
            System.out.println(accessible_state);
        }
    }

    /**
     * method which solves the accessible problem
     */
    void solve() {
        // for each source state, find the accessible states
        // starting from it
        for (Integer source_state : source_states) {
            get_accessible_states(source_state);
        }
        // print accessible states
        print_accessible_states();
    }
}
