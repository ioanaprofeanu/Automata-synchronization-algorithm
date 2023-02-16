// Profeanu Ioana, 333CA
import java.util.*;

/**
 * Class for solving the Synchronize problem
 */
public class Synchronize {
    /**
     * Class which contains a first state - second state pair
     * and the sequence which led to it
     */
    static class States_Sequence {
        // first and second state
        int first_state;
        int second_state;
        // sequence which was used to get to these states
        ArrayList<Integer> sequence = new ArrayList<>();

        public States_Sequence(int first_state, int second_state) {
            this.first_state = first_state;
            this.second_state = second_state;
        }

        public States_Sequence(int first_state, int second_state,
                               ArrayList<Integer> sequence) {
            this.first_state = first_state;
            this.second_state = second_state;
            this.sequence.addAll(sequence);
        }

        public States_Sequence(int first_state, int second_state,
                               ArrayList<Integer> sequence, int new_transition) {
            this.first_state = first_state;
            this.second_state = second_state;
            // add the new transition symbol to the sequence list
            this.sequence.addAll(sequence);
            this.sequence.add(new_transition);
        }

        @Override
        public boolean equals(Object o) {
            States_Sequence that = (States_Sequence) o;
            return first_state == that.first_state && second_state == that.second_state;
        }
    }

    // input data
    int no_states, no_symbols, no_source_states;
    ArrayList<Integer> source_states;
    ArrayList<ArrayList<Integer>> adjacency_list;
    // list of available states
    ArrayList<Integer> available_starting_states = new ArrayList<>();
    // final merging sequence
    ArrayList<Integer> final_merging_sequence = new ArrayList<>();
    // the merging sequence pair
    States_Sequence found_merging_sequence_pair = null;

    public Synchronize(int no_states, int no_symbols, int no_source_states,
                       ArrayList<Integer> source_states,
                       ArrayList<ArrayList<Integer>> adjacency_list) {
        this.no_states = no_states;
        this.no_symbols = no_symbols;
        this.no_source_states = no_source_states;
        this.source_states = source_states;
        this.adjacency_list = adjacency_list;
    }

    /**
     * Method which uses an iterative dfs algorithm
     * for finding the sequence which leads an initial pair
     * of states to the same merging state
     * @param initial_states the pair of initial states
     */
    void find_merging_sequence(States_Sequence initial_states) {
        // initialize the stack and add the pair of initial states
        Stack <States_Sequence> stack = new Stack<>();
        stack.push(initial_states);
        // initialize visited list and add the pair of initial states
        ArrayList<States_Sequence> visited = new ArrayList<>();
        visited.add(new States_Sequence(initial_states.first_state,
                initial_states.second_state));

        // while the stack is not empty
        while (stack.size() != 0) {
            // get the top of the stack
            States_Sequence current_states = stack.peek();
            stack.pop();
            // for each possible symbol
            for (int symbol = 0; symbol < no_symbols; symbol++) {
                // for the transition with the current symbol, get
                // the next state for each element of the pair of states
                States_Sequence next_states = new States_Sequence
                        (adjacency_list.get(current_states.first_state).get(symbol),
                        adjacency_list.get(current_states.second_state).get
                                (symbol), current_states.sequence, symbol);
                // if the two states are equal, it means we found a merging sequence
                if (next_states.first_state == next_states.second_state) {
                    found_merging_sequence_pair = next_states;
                    return;
                }
                // check if the pair has already been visited
                States_Sequence aux_state = new States_Sequence(next_states.first_state,
                        next_states.second_state);
                if (!visited.contains(aux_state)) {
                    // if not, add the pair to the stack
                    stack.push(next_states);
                    visited.add(aux_state);
                }
            }
        }
    }

    /**
     * Method which, for a given sequence, returns the final state
     * after transitioning from an initial state
     * @param initial_state the starting state
     * @param sequence the merging sequence
     * @return the final state
     */
    int get_final_state(Integer initial_state, ArrayList<Integer> sequence) {
        int current_state = initial_state;
        // for each symbol within the sequence, make the transitions
        for (Integer symbol : sequence) {
            current_state = adjacency_list.get(initial_state).get(symbol);
            initial_state = current_state;
        }
        // return final state
        return current_state;
    }

    /**
     * Method which resolves the synchronize problem
     */
    void solve() {
        // initially, the available starting states are the source states
        available_starting_states.addAll(source_states);

        // while there is more than one available state
        while (available_starting_states.size() > 1) {
            // find the merging sequence for the first two
            // available starting states
            ArrayList<Integer> current_sequence = new ArrayList<>();
            find_merging_sequence(new States_Sequence
                    (available_starting_states.get(0),
                            available_starting_states.get(1), current_sequence));

            // for each available starting states, get the final state which
            // is reached using the previously found merging sequence and
            // add it to the set
            SortedSet<Integer> final_states = new TreeSet<>();
            for (Integer state : available_starting_states) {
                final_states.add(get_final_state(state,
                        found_merging_sequence_pair.sequence));
            }

            // the available starting states will be the previously
            // found starting sets
            available_starting_states.clear();
            available_starting_states.addAll(final_states);
            // add the previous merging sequence to the final sequence
            final_merging_sequence.addAll(found_merging_sequence_pair.sequence);
        }
        // print the final merging sequence
        for (Integer symbol : final_merging_sequence) {
            System.out.print(symbol + " ");
        }
    }
}
