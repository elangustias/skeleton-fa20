package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.introcs.StdRandom;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] isOpenArr;
    private int numSites;
    private int numRowsCols;
    private int openSites;
    private int virtualRootIn;
    private int virtualTailIn;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be of a positive non-zero length");
        }
        numSites = N * N;
        numRowsCols = N;
        openSites = 0;
        virtualRootIn = 0;
        virtualTailIn = numSites + 1;
        isOpenArr = new boolean[numSites + 2];
        uf = new WeightedQuickUnionUF(numSites + 2);
        for (int i = virtualRootIn + 1; i < virtualTailIn; i++) {
            isOpenArr[i] = false;
        }
    }
    /* Takes row/col input and returns integer corresponding index */
    private int findIndex(int row, int col) {
        return row * numRowsCols + col + 1;
    }
    /* Assures valid index is given as input to open, isOpen, and isFull methods. */
    private void validate(int index) {
        if (index <= virtualRootIn || index >= virtualTailIn) {
            throw new IndexOutOfBoundsException();
        }
    }
    /* When a new site is opened, connectNeighbors checks if site is next to an open site, and unions them accordingly. */
    private void connectNeighbors(int index) {
        // Checks if it's a top or bottom row site being opened, and if so, connects it to virtual root or tail.
        if (index <= numRowsCols) {
            uf.union(virtualRootIn, index);
        } else if (index > (numSites - numRowsCols)) {
            uf.union(virtualTailIn, index);
        }
        // Checks if there is an open site above index that needs to be connected.
        if (index - numRowsCols > virtualRootIn && isOpenArr[index - numRowsCols]) {
            uf.union(index, index - numRowsCols);
        }
        // Checks if there is an open site below index that needs to be connected.
        if (index + numRowsCols < virtualTailIn && isOpenArr[index + numRowsCols]) {
            uf.union(index, index + numRowsCols);
        }
        /* Checks if there is an open site to the left index that needs to be connected,
         * checking also that index isn't in the leftmost column.
         */
        if (index - 1 > virtualRootIn && isOpenArr[index - 1] && index % numRowsCols != 1) {
            uf.union(index, index - 1);
        }
        /* Checks if there is an open site to the right index that needs to be connected,
         * checking also that index isn't in the rightmost column.
         */
        if (index + 1 < virtualTailIn && isOpenArr[index + 1] && index % numRowsCols != 0) {
            uf.union(index, index + 1);
        }
    }
    public void open(int row, int col) {
        int index = findIndex(row, col);
        validate(index);
        if (!isOpenArr[index]) {
            isOpenArr[index] = true;
            openSites++;
            connectNeighbors(index);
        }
    }
    public boolean isOpen(int row, int col) {
        int index = findIndex(row, col);
        validate(index);
        return isOpenArr[index];
    }
    public boolean isFull(int row, int col) {
        int index = findIndex(row, col);
        validate(index);
        return uf.connected(index, virtualRootIn);
    }
    public int numberOfOpenSites() {
        return openSites;
    }
    public boolean percolates() {
        return uf.connected(virtualRootIn, virtualTailIn);
    }
    public static void main(String[] args) {
        int[] x = new int[40];
        for (int i = 0; i < x.length; i++) {
            x[i] = i;
        }
        StdRandom.shuffle(x);
        StdRandom.shuffle(x);

    }
}
