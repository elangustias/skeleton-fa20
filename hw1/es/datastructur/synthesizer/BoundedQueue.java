package es.datastructur.synthesizer;
import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {
    // Return the size of the buffer
    public int capacity();
    // Return number of items currently in the buffer
    public int fillCount();
    // Add item x to the end of the queue
    public void enqueue(T x);
    // Delete and return item from the front
    public T dequeue();
    // Return (but do not delete) item from the front
    public T peek();
    // Returns true if queue is empty, false otherwise
    default public boolean isEmpty() {
        return capacity() == 0;
    }
    default public boolean isFull() {
        return fillCount() == capacity();
    }
    public Iterator<T> iterator();
}
