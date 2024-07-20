/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private int numOfMoves;
    private Stack<Board> solutionSteps;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // Edge Case
        if (initial == null) {
            throw new IllegalArgumentException("Null Argument");
        }
        // Initialization of Variables
        numOfMoves = 0;
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinPq = new MinPQ<>();

        // Inserting initial and twin boards into respective priority queues
        pq.insert(new SearchNode(initial, null, 0));
        twinPq.insert(new SearchNode(initial.twin(), null, 0));

        // Needed for solution function
        solutionSteps = new Stack<>();


        while (true) {
            SearchNode currentNode = pq.delMin();
            SearchNode twinNode = twinPq.delMin();

            // Check if current Node is the goal board
            if (currentNode.board.isGoal()) {
                buildSolution(currentNode);
                break;
            }
            // Check if twin board is the Goal
            if (twinNode.board.isGoal()) {
                numOfMoves = -1;
                solutionSteps = null;
                break;
            }

            // For loop to check all of the neighbours of the initial board, continuing from the neighbour that matches. BFS
            for (Board neighbor : currentNode.board.neighbors()) {
                // Avoid revisiting a node to avoid cycles
                if (currentNode.previousNode == null || !neighbor.equals(
                        currentNode.previousNode.board)) {
                    pq.insert(new SearchNode(neighbor, currentNode, currentNode.moves + 1));
                }
            }
            // Same as the above for loop but for the twin Initial board
            for (Board neighbor : twinNode.board.neighbors()) {
                if (twinNode.previousNode == null || !neighbor.equals(
                        twinNode.previousNode.board)) {
                    twinPq.insert(new SearchNode(neighbor, twinNode, twinNode.moves + 1));
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solutionSteps != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        else {
            return numOfMoves;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return solutionSteps;
    }

    // Node class used for BFS
    private static class SearchNode implements Comparable<SearchNode> {
        // Necessary
        private Board board;
        private SearchNode previousNode;
        private int moves;

        public SearchNode(Board board, SearchNode previousNode, int moves) {
            this.board = board;
            this.previousNode = previousNode;
            this.moves = moves;
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.board.manhattan() + this.moves,
                                   that.board.manhattan() + that.moves);
        }

    }

    // Used to put together the steps for the solution
    private void buildSolution(SearchNode goalNode) {
        numOfMoves = goalNode.moves;
        while (goalNode != null) {
            solutionSteps.push(goalNode.board);
            goalNode = goalNode.previousNode;
        }
    }


    // test client (see below)
    public static void main(String[] args) {

    }

}
