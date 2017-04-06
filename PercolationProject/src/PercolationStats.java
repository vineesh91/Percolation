/*Created by: Vineesh Ravindran
  Date		: 03/26/2017
  Purpose   : To calculate the mean, standard deviation and 95% confidence interval
   			  for a set of trials
  Execution : The 'main' method takes two integers as arguments : number of rows in the grid 
  			  and number of trials 			  
*/
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private int trialCount; //number of trials to be run
	private double[] trialResults;  //to save the calculated threshold
	
	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if(n <= 0 || trials <= 0)
			throw new java.lang.IllegalArgumentException();
		trialCount = trials;
		trialResults = new double[trials];
		for (int i = 0; i < trials; i++){
			//open random sites until the system percolates
			Percolation classPercolaion= new Percolation (n);			
			int sitesOpened=0; //to save the number of sites opened before the system percolates
			while (!classPercolaion.percolates()){
				int row = StdRandom.uniform(1,n+1);  //genrate a random number in the range [1,n]
				int col = StdRandom.uniform(1,n+1);  //genrate a random number in the range [1,n]
				if(!classPercolaion.isOpen(row,col)){
					sitesOpened++;
					classPercolaion.open(row, col);
				}
			}
			trialResults[i] =((double)(sitesOpened))/(n*n);  //save the percolation threshold
		}
	}   
	
	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(trialResults);
	}                         
	
	// sample standard deviation of percolation threshold
	public double stddev()   {
		return StdStats.stddev(trialResults);
	}                     
	
	// low  endpoint of 95% confidence interval
	public double confidenceLo()    {
		//calculated by (mean - (1.96/sqrt(mean))
		double confidenceLow= this.mean() - (1.96/Math.sqrt(trialCount)); 
		return confidenceLow;
	}              
	
	// high endpoint of 95% confidence interval
	public double confidenceHi()     {
		//calculated by (mean + (1.96/sqrt(mean))
				double confidenceHigh= this.mean() + (1.96/Math.sqrt(trialCount)); 
				return confidenceHigh;
	}             
	
	public static void main(String[] args) {
		/* the arguments provided should be n (number of rows or columns of the grid)
		 *  and number of trials */
		int n = Integer.parseInt(args[0]); 
        int t = Integer.parseInt(args[1]);
        PercolationStats pStats = new PercolationStats(n,t);
        StdOut.println("Input provided : n = "+n+" T = "+t);
        StdOut.println("Mean = "+pStats.mean());
        StdOut.println("Standard devaiation = "+pStats.stddev());
        StdOut.println("95% confidence interval = ["+pStats.confidenceLo()+"s,"+pStats.confidenceHi()+"s]");
	}

}
