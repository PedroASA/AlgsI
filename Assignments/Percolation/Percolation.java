/*
<<<<<<< Updated upstream
=======
* Grade: 100/100
>>>>>>> Stashed changes
* Correctness:  38/38 tests passed
* Memory:       8/8 tests passed
* Timing:       20/20 tests passed
*
*
*
*
* Backwash prevention idea:
* 	-Use two UnionFind objects:
*		* One that connects last row to bottom and is able to indicate percolation.
* 		* That other is used to answer isFull queries.
*
* Further Improvements - Bonus:
* 	-Use an array of bytes (4 bits) to control multiple (4) states instead of a second UF object.
*
*
* */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private final WeightedQuickUnionUF Uf;//connections between open adjacent sites
	private final int ts;//virtual top site
	private final int bs;//virtual bottom site
	private final boolean[][] open_sites;//record of open sites
	private int count;//number of open sites
	private final int N;//number of real sites
	//Prevent Backwash.
	private final WeightedQuickUnionUF Uf2;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	if(n <=0 ) {throw new IllegalArgumentException();}
		this.N = n;
    	ts=n*n;
    	bs=n*n+1;
    	this.Uf = new WeightedQuickUnionUF(n*n+2);
		this.Uf2 = new WeightedQuickUnionUF(n*n+2);
    	open_sites= new boolean[n][n];
		count=0;
		//unite ts to all top sites
    	for(int i=0; i<n; i++) {
    		Uf.union(ts, i);
			Uf2.union(ts, i);
    		/*Backwash prevention!
    		Unite last row to bs lazily.*/
    		Uf.union(bs, n*n-i-1);
    	}
	
    }
    //check arguments are within range of grid
	private boolean checkArgs(int row, int col) {
		return row >= 0 && col >= 0 && row < N && col < N;
	}
	//connect recently open site to its open neighbors
	private void siteUnion(int row, int col) {
    	int site = row * N + col;
    	//site directly above
		if(checkArgs(row-1, col) && open_sites[row-1][col]) {
			Uf.union(site, ((row - 1) * N) + col);
			Uf2.union(site, ((row - 1) * N) + col);
		}
		//site directly under
		if(checkArgs(row + 1, col) && open_sites[row+1][col]) {
			Uf.union(site, ((row + 1) * N) + col);
			Uf2.union(site, ((row + 1) * N) + col);
		}
		//closest site to the left
		if(checkArgs(row, col - 1) && open_sites[row][col-1]) {
			Uf.union(site, row * N + col - 1);
			Uf2.union(site, row * N + col - 1);
		}
		//closest site to the right
		if(checkArgs(row, col + 1) && open_sites[row][col+1]) {
			Uf.union(site, row * N + col + 1);
			Uf2.union(site, row * N + col + 1);
		}
	}
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
		if(!(checkArgs(row-1, col-1))) {throw new IllegalArgumentException();}
		if(!open_sites[row-1][col-1]) {
			open_sites[row-1][col-1] = true;
			count++;
			siteUnion(row-1, col-1);
		}

    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
		if(!(checkArgs(row-1, col-1))) {throw new IllegalArgumentException();}
		return open_sites[row-1][col-1];
	}

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	if(!(checkArgs(row-1, col-1))){throw new IllegalArgumentException();}
    	//a site is full iff it is open and connected to ts
		/*if(percolates)
			return (checkArgs(row-2, col-1) && isFull(row - 1, col))
					|| (checkArgs(row-1, col) && isFull(row, col + 1))
					|| (checkArgs(row-1, col-2) && isFull(row, col - 1));*/
		return isOpen(row, col) && Uf2.find(ts) == Uf2.find((row-1) * N + col-1);
	}
    // returns the number of open sites
    public int numberOfOpenSites() {
    	return count;
	}
    // does the system percolate?
    public boolean percolates() {
    	switch (ts) {
			//n == 1 => n^2 == 1
			case 1:
				return isOpen(1, 1);
			//n == 2 => n^2 == 4
			case 4:
				return (isOpen(1, 1) && isOpen(2, 1))
						|| (isOpen(1, 2) && isOpen(2, 2));
			//The system percolates iff ts is connected to bs
			default:
				return Uf.find(ts) == Uf.find(bs);
		}
	}
    // test client (optional)
   public static void main(String[] args) {
    	In in = new In("test_files/input5.txt");
    	int n = in.readInt();
    	Percolation p = new Percolation(n);
    	int i =0;
    	while(!in.isEmpty()) {
    		i++;
    		int t1 = in.readInt();
			int t2 = in.readInt();
			p.open(t1, t2);
			if(i == 21) StdOut.println(p.percolates());
		}
   }
}
