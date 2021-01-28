import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class Board {
    private final int[][] board;
    private final int n, manhattan;
    private int zeroi, zeroj;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        int count = 0;
        int k=1;
        n = tiles.length;
        board = new int[n][n];
        for(int i = 0; i< n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j]=tiles[i][j];
                if (board[i][j] == 0) {
                    zeroi = i;
                    zeroj = j;
                }
                if (board[i][j] != (k++ % (n*n)) && board[i][j]!=0) count += Math.abs(i -  (board[i][j] - 1) / n ) +  Math.abs(j - (board[i][j] - 1) % n) ;
            }
        }
        manhattan = count;
    }
    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(board[i][j] + "\t");
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count=0;
        int k=1;
        for(int i=0; i<n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != (k++ % (n*n)) && board[i][j]!=0) count++;
            }
        }
        return count;
    }


    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int val = 1;
        for(int i=0; i<n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != (val++ % (n*n))) return false;

            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if(this==y) return true;
        if(y== null) return false;
        if(y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return this.toString().equals(that.toString());
    }


    private boolean inBounds(int x) {
        if(x >=n || x < 0 ) return false;
        return true;
    }

    private int[][] deepCopy(int[][] original) {
        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }
    
    private Board exch(int row, int col, int rshift, int cshift) {
        if(inBounds(row + rshift) && inBounds(col + cshift)) {
            int[][] tile = deepCopy(board);
            int tmp = tile[row][col];
            tile[row][col] = tile[row + rshift][col + cshift];
            tile[row + rshift][col + cshift] = tmp;
            return new Board(tile);
        }
        return null;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> A = new ArrayList<Board>();
        Board x= exch(zeroi, zeroj, 1, 0);
        if(x != null )A.add(x);
        x=exch(zeroi, zeroj, 0, 1);
        if(x != null )A.add(x);
        x=exch(zeroi, zeroj, -1, 0);
        if(x != null )A.add(x);
        x=exch(zeroi, zeroj, 0, -1);
        if(x != null )A.add(x);
        return A;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n - 1; col++)
                if(row != zeroi || (col !=zeroj && col+1!=zeroj))
                    return exch(row, col, 0, 1);
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        System.out.println(initial.manhattan());
    }

}
