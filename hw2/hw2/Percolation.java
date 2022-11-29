package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] isOpenArr;
    private int num;
    private int openSites = 0;
    public int findIndex(int row, int col) {
        return (row * num) + col + 1;
    }
    public void validate(int row, int col) {
        int index = findIndex(row, col);
        if (index < 0 || index >= isOpenArr.length - 2) {
            throw new IndexOutOfBoundsException();
        }
    }
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be of a positive non-zero length");
        }
        uf = new WeightedQuickUnionUF((N*N) + 2);
        num = N;
        for (int i = 1; i < uf.count() - 2; i++) {
            isOpenArr[i] = false;
        }
        // ^^ do I need to set new default false values or can I just use the default null?
    }
    private void checkConnections(int index) {
        int[] conns = new int[4];
        conns[0] = index - num;
        conns[1] = index - 1;
        conns[2] = index + 1;
        conns[3] = index + num;

        if (index < num) {
            uf.union(0, index);
        }
        for (int i = 0; i < conns.length; i++) {
            if (conns[i] > 0) {
                uf.union(index, conns[i]);
            }
        }
        // This will crash bc I still need to check if the indeces are within bounds on the tail end also.
    }
    // Not sure if that's all i need for Percolation constructor
    public void open(int row, int col) {
        validate(row, col);
        int index = findIndex(row, col);
        if (!isOpenArr[index]) {
            isOpenArr[index] = true;
            openSites++;
            checkConnections(index);
        }
    }
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int index = findIndex(row, col);
        return isOpenArr[index];
    }
    public boolean isFull(int row, int col) {
        validate(row, col);
        int index = findIndex(row, col);
        // is the site (row, col) full?
    }
    public int numberOfOpenSites() {
        return openSites;
    }
    public boolean percolates() {
        // does the system percolate?
    }
    public static void main(String[] args) {
        // use for unit testing
    }
}
