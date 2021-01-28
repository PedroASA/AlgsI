import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Stack;


public class Solver {
    private class SN implements Comparable<SN>{
        Board B;
        int moves;
        SN prev;
        private SN(Board B, int n, SN prev) {
            this.B=B;
            this.moves=n;
            this.prev =prev;
        }
        public int compareTo(SN s) {
            return (this.B.manhattan() - s.B.manhattan()) + (this.moves - s.moves);
            //return (this.B.hamming() + this.moves - s.B.hamming() + s.moves);
        }
    }
    private boolean solvable;
    private SN Sol;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if(initial == null) throw new IllegalArgumentException();
        MinPQ<SN> Q= new MinPQ<SN>();
        MinPQ<SN> QTwin = new MinPQ<SN>();
        SN Node = new SN(initial, 0, null);
        Q.insert(Node);
        SN nodeTwin = new SN(initial.twin(), 0, null);
        QTwin.insert(nodeTwin);
        while(!Q.isEmpty()) {
            SN Sn = Q.delMin();
            //System.out.println(Sn.B + "çdfçdkbkd");
            if(Sn.B.isGoal()) {
                solvable = true;
                Sol = Sn;
                break;
            }
            for(Board x : Sn.B.neighbors()) {
                //System.out.println(Sn.B);
                if (Sn.prev == null || !x.equals(Sn.prev.B)) {
                    //System.out.println(x);
                    Q.insert(new SN(x, Sn.moves + 1, Sn));
                }
            }
            //System.out.println("dfçblmdç");
            if(QTwin.isEmpty()) {
                SN no = new SN(Sn.B.twin(), 0, null);
                QTwin.insert(no);
            }
            SN snt = QTwin.delMin();
            if(snt.B.isGoal()) {
                solvable=false;
                break;
            }
            //System.out.println("aaaaaa" +snt.B);
            for(Board t : snt.B.neighbors()) {
                //System.out.println(snt.B);
                if (snt.prev == null || !t.equals(snt.prev.B)) {
                    QTwin.insert(new SN(t, snt.moves + 1, snt));
                }
            }
        }

    }
    // is the initial board solvable? (see below)
    public boolean isSolvable() {return solvable;}

    // min number of moves to solve initial board
    public int moves() {
        if(solvable) return Sol.moves;
        return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if(!solvable) return null;
        Stack<Board> P = new Stack<Board>();
        SN s = Sol;
        while(s != null) {
            P.add(s.B);
            s=s.prev;
        }
        return P;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}