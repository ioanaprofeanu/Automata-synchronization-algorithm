// Profeanu Ioana, 333CA
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for solving the Labyrinth problem
 */
public class Labyrinth {
    static class Solve_Labyrinth {
        /**
         * Class which contains the coordinates of a robot
         */
        static class Robots_Coordinates {
            int l_coordinate;
            int c_coordinate;

            public Robots_Coordinates(int l_coordinate, int c_coordinate) {
                this.l_coordinate = l_coordinate;
                this.c_coordinate = c_coordinate;
            }
        }

        // input data
        int no_lines, no_columns, no_robots;
        ArrayList<ArrayList<Integer>> labyrinth_codification = new ArrayList<>();
        ArrayList<Robots_Coordinates> robots = new ArrayList<>();
        // directions
        int east = 1;
        int north = 2;
        int west = 4;
        int south = 8;
        ArrayList<Integer> directions;

        /**
         * Method for reading the input data
         */
        public void read_input() {
            Scanner sc = new Scanner(System.in);
            // read dimensions
            no_lines = sc.nextInt();
            no_columns = sc.nextInt();
            no_robots = sc.nextInt();

            // read the matrix of codifications
            for (int i = 0; i < no_lines; i++) {
                ArrayList<Integer> line_codifications = new ArrayList<>();
                for (int j = 0; j < no_columns; j++) {
                    int input_codification = sc.nextInt();
                    line_codifications.add(input_codification);
                }
                labyrinth_codification.add(line_codifications);
            }

            // read the robots (their coordinates)
            for (int i = 0; i < no_robots; i++) {
                int l_coordinate = sc.nextInt();
                int c_coordinate = sc.nextInt();
                robots.add(new Robots_Coordinates(l_coordinate, c_coordinate));
            }

            // add the directions to the directions arraylist
            directions = new ArrayList<>();
            directions.add(east);
            directions.add(north);
            directions.add(west);
            directions.add(south);
        }

        /**
         * Method which verifies if the robot can move in a
         * certain direction
         * @param codification the cell codification
         * @param direction the direction that is verified
         * @param line the line within the matrix
         * @param column the column within the matrix
         * @return true if the robot can move, false otherwise
         */
        boolean verify_move(int codification, int direction,
                            int line, int column) {
            // for each direction, check if the move would
            // be outside the labyrinth
            if (direction == east && no_columns - 1 == column) {
                return false;
            }
            if (direction == north && line == 0) {
                return false;
            }
            if (direction == west && column == 0) {
                return false;
            }
            if (direction == south && no_lines - 1 == line) {
                return false;
            }
            // check if the codification allows the move
            // in that specific direction
            return (codification & direction) == 0;
        }

        /**
         * Method which solves the labyrinth problem
         */
        public void solve() {
            // read the input
            read_input();
            // the new codifications of the directions
            int no_states = no_lines * no_columns;
            int no_symbols = 4;
            int no_input_states = no_robots;
            // print directions
            System.out.println(no_states + " " + no_symbols + " " + no_input_states);

            // for each cell within the matrix
            for (int line = 0; line < no_lines; line++) {
                for (int column = 0; column < no_columns; column++) {
                    // calculate the state of the current cell
                    int current_state = line * no_columns + column;
                    // calculate the state of the neighbor cells
                    // and add them to an arraylist
                    ArrayList<Integer> direction_new_states = new ArrayList<>();
                    int east_state = line * no_columns + column + 1;
                    direction_new_states.add(east_state);
                    int north_state = (line - 1) * no_columns + column;
                    direction_new_states.add(north_state);
                    int west_state = line * no_columns + column - 1;
                    direction_new_states.add(west_state);
                    int south_state = (line + 1) * no_columns + column;
                    direction_new_states.add(south_state);

                    // for each direction
                    for (int i = 0; i < directions.size(); i++) {
                        // if the move can be done
                        if (verify_move(labyrinth_codification.get(line).get(column),
                                directions.get(i), line, column)) {
                            // print the neighbor state for the current direction
                            System.out.print(direction_new_states.get(i) + " ");
                        } else {
                            // otherwise, remain in the current state
                            System.out.print(current_state + " ");
                        }
                    }
                    System.out.println();
                }

            }
            // print the source states
            for (Robots_Coordinates robot : robots) {
                int input_state = robot.l_coordinate * no_columns + robot.c_coordinate;
                System.out.print(input_state + " ");
            }
        }
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        Solve_Labyrinth labyrinth = new Solve_Labyrinth();
        labyrinth.solve();
    }
}
