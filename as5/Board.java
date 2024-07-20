/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }


    // string representation of this board
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        boardString.append(n + " " + "\n");
        for (int i = 0; i < n; i++) {
            boardString.append(" ");
            for (int j = 0; j < n; j++) {
                boardString.append(this.tiles[i][j] + " ");
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int wrongPlacement = 0;
        int expectedValue = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (expectedValue != tiles[i][j] && tiles[i][j] != 0) {
                    wrongPlacement++;
                }
                expectedValue++;
            }
        }
        return wrongPlacement;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int totalDistance = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = tiles[i][j];

                // Calculate the expected position (where the value should be)
                int expectedRow = (value - 1) / n;
                int expectedCol = (value - 1) % n;

                // Calculate Manhattan distance for this tile
                if (value != 0) { // Skip the blank tile if it exists (value = 0)
                    int distance = Math.abs(i - expectedRow) + Math.abs(j - expectedCol);
                    totalDistance += distance;
                }
            }
        }
        return totalDistance;
    }

    //
    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    //
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        if (n != ((Board) y).n) {
            return false;
        }
        if (this.hamming() != ((Board) y).hamming()) {
            return false;
        }
        if (this.manhattan() != ((Board) y).manhattan()) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != ((Board) y).tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //
    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        int emptyRow = 0, emptyCol = 0;
        boolean foundZero = false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    foundZero = true;
                }
            }
            if (foundZero) {
                break;
            }
        }

        int[][] offsets = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };

        // Generate neighboring boards
        for (int[] offset : offsets) {
            int newRow = emptyRow + offset[0];
            int newCol = emptyCol + offset[1];

            // Check if the new position is within bounds
            if (isValid(newRow, newCol)) {
                // Create a copy of the current board configuration
                int[][] newTiles = new int[n][n];
                for (int i = 0; i < n; i++) {
                    System.arraycopy(tiles[i], 0, newTiles[i], 0, n);
                }

                // Swap the blank tile with the neighboring tile
                int temp = newTiles[emptyRow][emptyCol];
                newTiles[emptyRow][emptyCol] = newTiles[newRow][newCol];
                newTiles[newRow][newCol] = temp;

                // Create a new Board object with the new configuration and add to neighbors list
                Board neighborBoard = new Board(newTiles);
                neighbors.add(neighborBoard);
            }
        }
        return neighbors;
    }

    // Helper function to check if a position is valid
    private boolean isValid(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n;
    }

    //
    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinBoard = new int[n][n];
        int tmp;
        // Deep Copy
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                twinBoard[i][j] = tiles[i][j];
            }
        }

        if (twinBoard[0][0] != 0 && twinBoard[0][1] != 0) {
            tmp = twinBoard[0][0];
            twinBoard[0][0] = twinBoard[0][1];
            twinBoard[0][1] = tmp;
        }
        else {
            tmp = twinBoard[1][0];
            twinBoard[1][0] = twinBoard[1][1];
            twinBoard[1][1] = tmp;
        }

        return new Board(twinBoard);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {
                { 2, 8, 3 },
                { 4, 5, 6 },
                { 7, 1, 0 }
        };

        int[][] toes = {
                { 1, 8, 3 },
                { 4, 5, 6 },
                { 7, 2, 0 }
        };

        Board board = new Board(tiles);
        Board board2 = new Board(toes);
        System.out.print(board.equals(board2));

    }

}
