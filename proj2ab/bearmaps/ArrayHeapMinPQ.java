package bearmaps;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    int size = 0;
    Node[] minHeap = new ArrayHeapMinPQ.Node[10];
    HashSet<Node> minSet = new HashSet<>();
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
        @Override
        public boolean equals(Object o) {
            if (o == null || (o.getClass() != this.getClass() || o.getClass() != this.item.getClass())) {
                return false;
            }
            // This might always return true bc all objects are of class object
            // ... and java doesn't know if the object is actually an item or node
            if (o.getClass() == this.getClass()) {
                return ((Node) o).item.equals(this.item);
            } else { // If Object o is of class T, which it normally should be
                return o.equals(this.item);
            }
        }
    }
    @Override
    public void add(T item, double priority) {
        Node n = new Node(item, priority);
        if (!(minSet.contains(n))) {
            minSet.add(n);
            minHeap[size] = n;
            swim(size);
            size++;
            minHeap = resize();
        }
    }
    private void swim(int index) {
        int currIndex = index;
        while (currIndex != 0) {
            int parentIndex = (currIndex - 1) / 2;
            if (minHeap[currIndex].priority < minHeap[parentIndex].priority) {
                Node pCopy = minHeap[parentIndex];
                minHeap[parentIndex] = minHeap[currIndex];
                minHeap[currIndex] = pCopy;
                minHeap[currIndex].index = currIndex;
                currIndex = parentIndex;
            } else {
                minHeap[currIndex].index = currIndex;
                break;
            }
        }
    }
    @Override
    public boolean contains(T item) {
        return minSet.contains(item);
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
        minSet.remove(smallest);
        minHeap[0] = minHeap[size];
        minHeap[size] = null;
        sink(0);
        size--;
        minHeap = resize();
        return smallest;
    }
    private void sink(int index) {
        int smallChild = smallestChild(index);
        while (smallChild > 0) {
            if (minHeap[index].priority < minHeap[smallChild].priority) {
                minHeap[index].index = index;
                break;
            } else {
                Node pCopy = minHeap[index];
                minHeap[index] = minHeap[smallChild];
                minHeap[index].index = index;
                minHeap[smallChild] = pCopy;
                index = smallChild;
                smallChild = smallestChild(index);
            }
        }
    }
    private int smallestChild(int index) {
        int left = index*2+1;
        int right = index*2+2;
        if (minHeap[left] == null && minHeap[right] == null) {
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
    /* I think I'll have to build a TreeMap instead of the HashSet bc for
     * changePriority I'm going to have to be able to retrieve the index from the
     * utility set, which is useless bc sets don't serve that purpose.
     */
    @Override
    public void changePriority(T item, double priority) {
        if (minSet.contains(item))
    }

}
