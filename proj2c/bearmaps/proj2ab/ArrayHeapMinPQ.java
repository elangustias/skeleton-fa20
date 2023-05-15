package bearmaps.proj2ab;

import java.util.*;
/* I figured out how to store integers in my hashmap so I don't have to store the entire
 * Node and maybe save some memory but it actually slowed down the tests even further.
 */

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private int size = 0;
    private double minFill = 0.25;
    private Node[] minHeap = new ArrayHeapMinPQ.Node[10];
    private HashMap<T, Integer> indexMap = new HashMap<>();
    private class Node {
        T item;
        double priority;
        Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
    }
    @Override
    public void add(T item, double priority) {
        if (indexMap.containsKey(item)) {
            throw new IllegalArgumentException("Item already exists");
        }
        Node n = new Node(item, priority);
        minHeap[size] = n;
        swim(size);
        size++;
        if (size == minHeap.length) {
            resize();
        }
    }
    /* Swim moves an item upward in depth if its priority queue number is smaller than its parent's.
     * It also updates the index mapping in indexMap for all affected items along the way.
     */
    private void swim(int index) {
        while (index != 0) {
            int parentIndex = (index - 1) / 2;
            if (minHeap[index].priority < minHeap[parentIndex].priority) {
                Node pCopy = minHeap[parentIndex];
                minHeap[parentIndex] = minHeap[index];
                minHeap[index] = pCopy;
                indexMap.put(minHeap[index].item, index); // Updates the demoted Node's index before reassigning index value
                index = parentIndex;
            } else {
                indexMap.put(minHeap[index].item, index);
                return;
            }
        }
        indexMap.put(minHeap[index].item, index);
    }
    @Override
    public boolean contains(T item) {
        return indexMap.containsKey(item);
    }
    @Override
    public T getSmallest() {
        // This crashes if the size of the PQ is 0.
        return minHeap[0].item;
    }
    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException("Priority Queue is empty");
        }
        T smallest = minHeap[0].item;
        indexMap.remove(smallest);
        size--;
        minHeap[0] = minHeap[size];
        minHeap[size] = null;
        if (size > 0) {
            sink(0);
        }
        if (size > 10 && Double.valueOf(size) / minHeap.length < minFill) {
            resize();
        }
        return smallest;
    }
    /* Sink checks which of an item's children has the smallest priority queue number, and then moves
     * the item downward in depth if its priority queue number is greater than that child's.
     * It also updates the index mapping in indexMap for all affected items along the way.
     */
    private void sink(int index) {
        int childIndex = smallestChild(index);
        while (childIndex != -1) {
            if (minHeap[index].priority < minHeap[childIndex].priority) {
                indexMap.put(minHeap[index].item, index);
                return;
            } else {
                Node pCopy = minHeap[index];
                minHeap[index] = minHeap[childIndex];
                indexMap.put(minHeap[index].item, index); // Updates the promoted Node's index before reassigning index value
                minHeap[childIndex] = pCopy;
                index = childIndex;
                childIndex = smallestChild(index);
            }
        }
        indexMap.put(minHeap[index].item, index);
    }
    private int smallestChild(int index) {
        int left = index*2+1;
        int right = index*2+2;
        boolean outOfBounds = left >= size;
        if (outOfBounds || (minHeap[left] == null)) {
            return -1;
        } else if (minHeap[right] == null || minHeap[left].priority < minHeap[right].priority) {
            return left;
        } else { // Covers for tiebreaker cases also
            return right;
        }
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void changePriority(T item, double priority) {
        if (!indexMap.containsKey(item)) {
            throw new NoSuchElementException("Item doesn't exist");
        }
        int index = indexMap.get(item);
        minHeap[index].priority = priority;
        sink(index);
        swim(index);
    }
    private void resize() {
        Node[] newHeap = new ArrayHeapMinPQ.Node[size*2];
        for (int i = 0; i < size; i++) {
            newHeap[i] = minHeap[i];
        }
        minHeap = newHeap;
    }
    public static void main(String[] args) {
    }
}