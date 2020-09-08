import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF A;
	private int ts;
	private int bs;
	private boolean[][] opened;
	private int count;
	private int n;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	if(n <=0 ) {throw new IllegalArgumentException();};
    	this.n=n;
    	ts=n*n;
    	bs=n*n+1;
    	this.A= new WeightedQuickUnionUF(n*n+2);
    	opened= new boolean[n][n];
		count=0;
    	for(int i=0; i<n; i++) {
    		 A.union(ts, i);
    		 A.union(bs, n*n-i-1);
    	}
	
    }
	private boolean checkArgs(int row, int col) {
		if (row < 0 || col < 0 || row >= n || col >= n) {
			return false;
		}
		return true;
	}
	private void UniteOpenSite(int row, int col) {
		if(checkArgs(row-1, col) && opened[row-1][col]) {
			A.union(row*n +col, ((row-1)*n) +col);
		}		
		if(checkArgs(row+1, col) && opened[row+1][col]) {
			A.union(row*n +col, ((row+1)*n) +col);
		}
		if(checkArgs(row, col-1) && opened[row][col-1]) {
			A.union(row*n +col, (row*n) +col-1);
		}
		if(checkArgs(row, col+1) && opened[row][col+1]) {
			A.union(row*n +col, (row*n) +col+1);
		}
	}
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
		if(!(checkArgs(row-1, col-1))) {throw new IllegalArgumentException();};
		if(!opened[row-1][col-1]) {
			opened[row-1][col-1]=true;
			count++;
			UniteOpenSite(row-1, col-1);
		}
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
		if(!(checkArgs(row-1, col-1))) {throw new IllegalArgumentException();};
		return opened[row-1][col-1];
	}
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	if(!(checkArgs(row-1, col-1))){throw new IllegalArgumentException();};
		return (isOpen(row, col) && A.find(ts)==A.find((row-1)*n + col-1));
    }
    // returns the number of open sites
    public int numberOfOpenSites() {
    	return count;
	}
    // does the system percolate?
    public boolean percolates() {
		// corner case: 1 site
		if (bs == 2) {
      		return isOpen(1, 1);
    	}
		// corner case: no sites
    	if (bs == 0) {
      		return false;
    	}
    	return A.find(ts)==A.find(bs);
	}
    // test client (optional)
   public static void main(String[] args) {
        test5();
    }
    private static void test2() {
        final Percolation p = new Percolation(3);
        System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));
        p.open(1, 2);
        System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));


        System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
        p.open(2, 2);
        System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
        System.out.println("p.isFull(2, 2) = " + p.isFull(2, 2));


        System.out.println("p.isOpen(3, 1) = " + p.isOpen(3, 2));
        p.open(3, 2);
        System.out.println("p.isOpen(3, 1) = " + p.isOpen(3, 2));
        p.isFull(3, 2);


        System.out.println("p.percolates() = " + p.percolates());
    }

    private static void test3() {
        final Percolation p = new Percolation(3);
        System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));
        p.open(1, 2);
        System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));

        System.out.println("p.isOpen(1,1) = " + p.isOpen(1, 1));
        p.open(1, 1);
        System.out.println("p.isOpen(1,1) = " + p.isOpen(1, 1));

        System.out.println("p.isFull(1, 2) = " + p.isFull(1, 2));

        System.out.println("p.isOpen(2, 2) = " + p.isOpen(2, 2));
        p.open(2, 2);
        System.out.println("p.isOpen(2, 2) = " + p.isOpen(2, 2));

        System.out.println("p.isOpen(2, 3) = " + p.isOpen(2, 3));
        p.open(2, 3);
        System.out.println("p.isOpen(2, 3) = " + p.isOpen(2, 3));

        p.isFull(2, 3);

        System.out.println("p.percolates() = " + p.percolates());

    }

    private static void test4() {
        final Percolation p = new Percolation(5);
        System.out.println("p.isOpen(1, 3) = " + p.isOpen(1, 3));
        p.open(1, 3);
        System.out.println("p.isOpen(1,3) = " + p.isOpen(1, 3));
        System.out.println("p.isFull(1, 3) = " + p.isFull(1, 3));
    }
	private static void test5() {
		final Percolation p = new Percolation(2);
        System.out.println("p.isOpen(1, 1) = " + p.isOpen(1, 1));
		p.open(1,1);
		System.out.println("p.isOpen(1, 1) = " + StdRandom.uniform(1, 3) +" "+StdRandom.uniform(1, 3));
		System.out.println(p.percolates());
	}
}
