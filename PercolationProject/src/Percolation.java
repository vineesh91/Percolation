
/*Created by: Vineesh Ravindran
  Date		: 03/26/2017
  Purpose   : To understand the provided weighted quick union algorithm
   			  library by using it on Percolation as part of the Coursera Algorithms course
*/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private byte[] SiteGrid; // to store the sites
	private int rowCount; // to store the provided row or column count
	private int openSiteCount; // number of open sites
	private WeightedQuickUnionUF wQF;

	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {
		if (n <= 0) {
			throw new java.lang.IllegalArgumentException("Provided grid dimension is less than 1");
		}
		SiteGrid = new byte[(n * n) + 2]; // to save the sites and the two
											// virtual sites
		wQF = new WeightedQuickUnionUF((n * n) + 2);
		rowCount = n;
	}

	// open site (row, col) if it is not open already
	public void open(int row, int col) {
		checkIndexRange(row, col);
		int pos = twoDto1D(row, col);
		if (!isOpen(row, col)) {
			SiteGrid[pos] = 1;
			openSiteCount++;
		}
		makeTheConnections(row, col);
	}

	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		checkIndexRange(row, col);
		if (SiteGrid[twoDto1D(row, col)] == 1)
			return true;
		else
			return false;

	}

	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		checkIndexRange(row, col);
		return wQF.connected(0, twoDto1D(row, col));
	}

	// number of open sites
	public int numberOfOpenSites() {

		return openSiteCount;
	}

	// does the system percolate?
	public boolean percolates() {
		return wQF.connected(0, ((rowCount * rowCount) + 1));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	// ensure that the input index is within the range 1 to N
	private void checkIndexRange(int rowIndex, int colIndex) {
		if ((1 > rowIndex || rowIndex > this.rowCount) || (1 > colIndex || colIndex > this.rowCount)) {
			throw new java.lang.IndexOutOfBoundsException(
					"The provided row index or column index does not lie within the grid dimension");
		}
	}

	// Convert the n by n structure into a linear structure
	private int twoDto1D(int row, int col) {
		return (((row - 1) * rowCount) + (col - 1) + 1);
	}

	// To connect the site opened to the already open immediate neighbors
	private void makeTheConnections(int row, int col) {
		int pos = twoDto1D(row, col);
		// connect to top virtual site if in the top row
		if (row == 1) {
			wQF.union(0, pos);
		}

		// connect to bottom virtual site if in bottom row
		if (row == rowCount) {
			wQF.union(((rowCount * rowCount) + 1), pos);
		}

		// connect to top site
		if ((row > 1) && isOpen((row - 1), col)) {
			wQF.union(twoDto1D((row - 1), col), pos);
		}

		// connect to bottom site
		if ((row < rowCount) && isOpen((row + 1), col)) {
			wQF.union(twoDto1D((row + 1), col), pos);
		}

		// connect to left site
		if ((col > 1) && isOpen(row, (col - 1))) {
			wQF.union(twoDto1D(row, (col - 1)), pos);
		}

		// connect to left site
		if ((col < rowCount) && isOpen(row, (col + 1))) {
			wQF.union(twoDto1D(row, (col + 1)), pos);
		}
	}
}
