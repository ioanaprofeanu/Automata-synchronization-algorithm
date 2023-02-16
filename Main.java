// Profeanu Ioana, 333CA
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /**
     * Class for reading the input data
     */
    static class Read_Input {
        int no_states, no_symbols, no_source_states;
        ArrayList<Integer> source_states = new ArrayList<>();
        ArrayList<ArrayList<Integer>> adjacency_list = new ArrayList<>();

        public void read_input() {
            Scanner sc = new Scanner(System.in);
            // read dimensions
            no_states = sc.nextInt();
            no_symbols = sc.nextInt();
            no_source_states = sc.nextInt();

            // read the symbols for each state and add it to the
            // adjacency list
            for (int state = 0; state < no_states; state++) {
                ArrayList<Integer> symbols = new ArrayList<>();
                for (int symbol = 0; symbol < no_symbols; symbol++) {
                    int input_symbol = sc.nextInt();
                    symbols.add(input_symbol);
                }
                adjacency_list.add(symbols);
            }

            if (no_source_states == 0) {
                // all the states are source states
                no_source_states = no_states;
                for (int i = 0; i < no_source_states; i++) {
                    source_states.add(i);
                }
            } else {
                // read the source states
                for (int i = 0; i < no_source_states; i++) {
                    int input_state = sc.nextInt();
                    source_states.add(input_state);
                }
            }
        }

        public int getNo_states() {
            return no_states;
        }

        public int getNo_symbols() {
            return no_symbols;
        }

        public int getNo_source_states() {
            return no_source_states;
        }

        public ArrayList<Integer> getSource_states() {
            return source_states;
        }

        public ArrayList<ArrayList<Integer>> getAdjacency_list() {
            return adjacency_list;
        }
    }

    /**
     * Main method
     * @param args receives the type of problem that is solved
     */
    public static void main(String[] args) {
        Read_Input read_input = new Read_Input();
        read_input.read_input();
        // if the problem is the accessible problem
        if (args[0].contains("accessible")) {
            Accessible accessible = new Accessible(read_input.getNo_states(),
                    read_input.getNo_symbols(), read_input.getNo_source_states(),
                    read_input.getSource_states(), read_input.getAdjacency_list());
            accessible.solve();
        }

        // if the problem is the synchronize problem
        if (args[0].contains(("synchronize"))) {
            Synchronize synchronize = new Synchronize(read_input.getNo_states(),
                    read_input.getNo_symbols(), read_input.getNo_source_states(),
                    read_input.getSource_states(), read_input.getAdjacency_list());
            synchronize.solve();
        }
    }
}
