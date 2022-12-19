package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int[] percolationNums;
    private double meanNum = -1.0;
    private double stddevNum = -1.0;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        percolationNums = new int[T];
        int[] grid = new int[N*N];
        for (int i = 0; i < N*N; i++) {
            grid[i] = i + 1;
        }
        for (int i = 0; i < T; i++) {
            int percolationCount = 0;
            Percolation curr = pf.make(N);
            StdRandom.shuffle(grid);
            int index = 0;
            while (!curr.percolates()) {
                int row = (grid[index] - 1) / N;
                int col = (grid[index] - 1) % N;
                curr.open(row, col);
                percolationCount++;
                index++;
            }
            percolationNums[i] = percolationCount;
        }
    }
    public double mean() {
        if (meanNum < 0) {
            meanNum = StdStats.mean(percolationNums);
        }
        return meanNum;
    }
    public double stddev() {
        if (stddevNum < 0) {
            stddevNum = StdStats.stddev(percolationNums);
        }
        return stddevNum;
    }
    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(percolationNums.length));
    }
    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(percolationNums.length));
    }
}
