package bearmaps;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private int size = 0;
    Node[] minHeap = new ArrayHeapMinPQ.Node[10];
    private HashMap<T, Node> nodeMap;
    private class Node {
        T item;
        double priority;
        int index;
        Node(T item, double priority) {
            this.item = item;
            this.priority = priority;

        }
        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
    @Override
    public void add(T item, double priority) {
        if (nodeMap.containsKey(item)) {
            throw new IllegalArgumentException("Item already exists");
        }
        Node n = new Node(item, priority);
        nodeMap.put(item, n);
        minHeap[size] = n;
        swim(size);
        size++;
        resize();

    }
    private void swim(int index) {
        while (index != 0) {
            int parentIndex = (index - 1) / 2;
            if (minHeap[index].priority < minHeap[parentIndex].priority) {
                Node pCopy = minHeap[parentIndex];
                minHeap[parentIndex] = minHeap[index];
                minHeap[index] = pCopy;
                minHeap[index].index = index; // Updates the demoted Node's index before reassigning index value
                index = parentIndex;
            } else {
                minHeap[index].index = index;
                break;
            }
        }
    }
    @Override
    public boolean contains(T item) {
        return nodeMap.containsKey(item);
    }
    @Override
    public T getSmallest() {
        return minHeap[0].item;
    }
    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException("Priority Queue is empty");
        }
        T smallest = minHeap[0].item;
        nodeMap.remove(smallest);
        minHeap[0] = minHeap[size-1];
        minHeap[size-1] = null;
        sink(0);
        size--;
        resize();
        return smallest;
    }
    private void sink(int index) {
        int childIndex = smallestChild(index);
        while (childIndex > 0) {
            if (minHeap[index].priority <= minHeap[childIndex].priority) {
                minHeap[index].index = index;
                break;
            } else {
                Node pCopy = minHeap[index];
                minHeap[index] = minHeap[childIndex];
                minHeap[index].index = index; // Updates the promoted Node's index before reassigning index value
                minHeap[childIndex] = pCopy;
                index = childIndex;
                childIndex = smallestChild(index);
            }
        }
    }
    private int smallestChild(int index) {
        int left = index*2+1;
        int right = index*2+2;
        boolean outOfBounds = right >= size;
        if (outOfBounds || (minHeap[left] == null && minHeap[right] == null)) {
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
        Node n = nodeMap.get(item);
        if (n == null) {
            throw new NoSuchElementException("Item doesn't exist");
        }
        n.priority = priority;
        sink(n.index);
        swim(n.index);
    }
    private void resize() {
        Double fill = Double.valueOf(size) / minHeap.length;
        if (0.25 < fill && fill < 1.00) {
            return;
        }
        Node[] newHeap = (Node []) new Object[size*2];
        for (int i = 0; i < size; i++) {
            newHeap[i] = minHeap[i];
        }
        minHeap = newHeap;
    }
}
