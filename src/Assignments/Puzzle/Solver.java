import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

//java stack iterator is not LIFO
import edu.princeton.cs.algs4.Stack;


public class Solver {

    private class SN implements Comparable<SN>{
        Board B;
        int moves, manhattan;
        SN prev;
        private SN(Board B, int n, SN prev) {
            this.B = B;
            this.moves = n;
            this.prev = prev;
            this.manhattan = B.manhattan();
        }
        
        public int compareTo(SN s) {
            int man = this.manhattan - s.manhattan;
            int mov = this.moves - s.moves;
            return man + mov == 0 ? man : man + mov;
        }
    }
    private boolean solvable;
    private SN Sol;

    private void add_nodes(SN node, MinPQ<SN> Q) {
        for(Board x : node.B.neighbors()) {

            if (node.prev == null || !x.equals(node.prev.B)) {
                Q.insert(new SN(x, node.moves + 1, node));
            }
        }

    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if(initial == null) throw new IllegalArgumentException();

        MinPQ<SN> Q= new MinPQ<SN>();

        // check if board is unsolvable. 
        MinPQ<SN> QTwin = new MinPQ<SN>();

        SN Node = new SN(initial, 0, null);
        Q.insert(Node);

        // Check if a permutation of board is solvable.
        SN nodeTwin = new SN(initial.twin(), 0, null);
        QTwin.insert(nodeTwin);

        while(!Q.isEmpty()) {

            SN Sn = Q.delMin();

            if(Sn.B.isGoal()) {
                solvable = true;
                Sol = Sn;
                break;
            }

            add_nodes(Sn, Q);

            // if a twin board is unsolvable, try another one.
            if(QTwin.isEmpty()) {
                SN no = new SN(Sn.B.twin(), 0, null);
                QTwin.insert(no);
            }

            SN snt = QTwin.delMin();

            // if any twin board is solvable, than the board received is unsolvable.
            if(snt.B.isGoal()) {
                solvable=false;
                break;
            }

            add_nodes(snt, QTwin);
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {return solvable;}

    // min number of moves to solve initial board
    public int moves() {
        return solvable ? Sol.moves : -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if(!solvable) return null;
        Stack<Board> res = new Stack<Board>();
        SN s = Sol;
        while(s != null) {
            res.push(s.B);
            s=s.prev;
        }
        return res;
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