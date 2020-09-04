import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class PercolationStats {
	private double[] results;
	private double mean;
	private double stddev;
	private int trials;
 
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	if(n<= 0) {throw new IllegalArgumentException();}  	
		results = new double[trials];
		this.trials = trials;
    	for(int i=0; i<trials; ++i) {
    		Percolation P = new Percolation(n);
			while(!(P.percolates())) {
				P.open(StdRandom.uniform(1,n+1), StdRandom.uniform(1, n+1));
			}
			results[i]=(double)P.numberOfOpenSites()/(n*n);
    	}
    	mean=StdStats.mean(results);
		stddev=StdStats.stddev(results);
    	
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
    	return (mean-1.96*stddev)/Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return (mean+1.96*stddev)/Math.sqrt(trials);
	}
	// test client (see below)
	public static void main(String[] args) {
		try {
			int n=Integer.parseInt(args[0]);
			int T = Integer.parseInt(args[1]);
			PercolationStats P = new PercolationStats(n, T);
			System.out.format("mean = %.16f\n",P.mean());
			System.out.format("stddev = %.16f\n",P.stddev());
			System.out.format("95%% Confidence Interval = [%.16f, %.16f]\n", P.confidenceLo(), P.confidenceHi());
		}catch(NumberFormatException e1) {
			System.err.println(e1 + "\n Argumento não é inteiro.");
		}catch(IllegalArgumentException e) {
			System.err.println(e + "\n Argumento não é positivo.");
		}
	}
   

}
