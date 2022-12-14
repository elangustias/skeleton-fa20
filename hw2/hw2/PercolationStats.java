package hw2;

import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    int[] percolationNums;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        percolationNums = new int[T];
        int[] grid = new int[N*N];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = i;
        }
        for (int i = 0; i < T; i++) {
            int index = 0;
            int percolationCount = 0;
            Percolation curr = pf.make(N);
            StdRandom.shuffle(grid);
            while (!curr.percolates()) {
                int row = grid[index] / N;
                int col = grid[index] % N;
                curr.open(row, col);
                percolationCount++;
            }
            percolationNums[index] = percolationCount;
            index++;
        }
    }
}
