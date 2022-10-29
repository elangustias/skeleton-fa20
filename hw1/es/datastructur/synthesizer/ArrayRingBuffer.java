package es.datastructur.synthesizer;
import java.util.Iterator;
import java.util.NoSuchElementException;

//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    /* Variabe for the capacity of the array */
    private int capacity;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last] = x;
        fillCount += 1;
        last = (last + 1) % capacity;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T firstItem = rb[first];
        fillCount -= 1;
        first = (first + 1) % capacity;
        return firstItem;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        return rb[first];
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
    // TODO: Remove all comments that say TODO when you're done.
    @Override
    public int capacity() {
        return capacity;
    }
    @Override
    public int fillCount() {
        return fillCount;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
                this.getClass() != o.getClass() ||
                this.fillCount() != ((ArrayRingBuffer<?>) o).fillCount()) {
            return false;
        }
        ArrayRingBuffer<?> other = (ArrayRingBuffer<?>) o;
        Iterator<?> otherIter = other.iterator();
        for (T item : this) {
            if (!item.equals(otherIter.next())) {
                return false;
            }
        }
        return true;
    }
    public Iterator<T> iterator() {
        return new BoundedQueueIterator(rb);
    }
    private class BoundedQueueIterator implements Iterator<T> {
        int index;
        int size;
        T[] arr;
        public BoundedQueueIterator(T[] arr) {
            index = first;
            size = fillCount;
            this.arr = arr;
        }
        @Override
        public boolean hasNext() {
            return size > 0;
        }
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T curr = arr[index % capacity];
            index += 1;
            size -= 1;
            return curr;
        }
    }
}
