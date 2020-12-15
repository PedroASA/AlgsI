/*
* No further improvements necessary.
* */
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final double mean;
	private final double stddev;
	private final int trials;
	private final double constant = 1.96;
 
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	if(n<= 0 || trials <= 0) {throw new IllegalArgumentException();}
    	double num = n * n;
		double[] results = new double[trials];
		this.trials = trials;
    	for(int i=0; i<trials; ++i) {
			Percolation p = new Percolation(n);
			//open sites at random until system percolates
			while(!(p.percolates())) {
				p.open(StdRandom.uniform(1,n+1), StdRandom.uniform(1, n+1));
			}
			results[i] = p.numberOfOpenSites()/num;
    	}
    	mean = StdStats.mean(results);
		stddev = StdStats.stddev(results);

    }

    // sample mean of percolation threshold
    public double mean() {
    	return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	return mean - (constant * stddev / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return mean + (constant * stddev / Math.sqrt(trials));
	}
	// test client (see below)
	public static void main(String[] args) {
    	int n  = Integer.parseInt(args[0]);
    	int t  = Integer.parseInt(args[1]);
    	PercolationStats p = new PercolationStats(n, t);
    	StdOut.printf("mean = %.16f\n",p.mean());
    	StdOut.printf("stddev = %.16f\n",p.stddev());
    	StdOut.printf("95%% Confidence Interval = [%.16f, %.16f]\n", p.confidenceLo(), p.confidenceHi());
	}

   

}
