package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] isOpenArr;
    private int numSites;
    private int numRowsCols;
    private int openSites = 0;
    public int findIndex(int row, int col) {
        return row * numRowsCols + col + 1;
    }
    public void validate(int index) {
        if (index < 0 || index >= isOpenArr.length - 2) {
            throw new IndexOutOfBoundsException();
        }
    }
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be of a positive non-zero length");
        }
        numSites = N * N;
        numRowsCols = N;
        uf = new WeightedQuickUnionUF(numSites + 2);
        for (int i = 1; i <= numSites; i++) {
            isOpenArr[i] = false;
        }
        // ^^ do I need to set new default false values or can I just use the default null?
    }
    private void connectNeighbors(int index) {
        int[] conns = new int[4];
        conns[0] = index - numRowsCols;
        conns[1] = index - 1;
        conns[2] = index + 1;
        conns[3] = index + numRowsCols;
        // This if statement checks if it's a top row site being opened, and if so, connects it to virtual root.
        if (index < numRowsCols) {
            uf.union(0, index);
        } else if (index > (numSites - numRowsCols)) {
            uf.union(numSites + 1, index);
        }
        // Off by one???
        for (int i = 0; i < conns.length; i++) {
            int currNeighborIndex = conns[i];
            if (0 < currNeighborIndex
                    && currNeighborIndex < numSites
                    && isOpenArr[currNeighborIndex]) {
                uf.union(index, conns[i]);
            }
        }
    }
    // Not sure if that's all i need for Percolation constructor
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
        return uf.connected(index, 0);
    }
    public int numberOfOpenSites() {
        return openSites;
    }
    public boolean percolates() {
        return uf.connected(0, numSites + 1);
    }
    public static void main(String[] args) {
        // use for unit testing
    }
}
