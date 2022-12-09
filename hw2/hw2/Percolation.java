package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

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
        for (int i = 1; i <= numSites; i++) {
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
        int[] conns = new int[4];
        conns[0] = index - numRowsCols;
        conns[1] = index - 1;
        conns[2] = index + 1;
        conns[3] = index + numRowsCols;
        // This if statement checks if it's a top or bottom row site being opened, and if so, connects it to virtual root or tail.
        if (index <= numRowsCols) {
            uf.union(virtualRootIn, index);
        } else if (index > (numSites - numRowsCols)) {
            uf.union(virtualTailIn, index);
        }
        // Off by one???
        // Might not even need these conditionals, i can possibly just set root index to open in the constructor.
        for (int i = 0; i < conns.length; i++) {
            int currNeighborIndex = conns[i];
            if (currNeighborIndex > virtualRootIn
                    && currNeighborIndex < virtualTailIn
                    && isOpenArr[currNeighborIndex]) {
                uf.union(index, currNeighborIndex);
            }
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
        // use for unit testing
    }
}
