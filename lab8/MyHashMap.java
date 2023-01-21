import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int size = 0;
    private double loadFactor;
    private LinkedList<BucketItem>[] buckets; // probably wrong

    /* Constructor if user offers no initial size or load factor. */
    public MyHashMap() {
        buckets = new LinkedList[16];
        loadFactor = 0.75;
    }
    /* Constructor if user offers no load factor. */
    public MyHashMap(int initialSize) {
        buckets = new LinkedList[16];
        loadFactor = 0.75;
    }
    /* Constructor if user offers both initial size and load factor. */
    public MyHashMap(int initialSize, double loadFactor) {
        buckets = new LinkedList[16];
        this.loadFactor = loadFactor;
    }
    /* Container object for individual key/value pairs. */
    private class BucketItem {
        K key;
        V value;
        BucketItem(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    /* Clear all items from the hash map. Hash map is resized to the default minimum of 16, and load
     * factor remains the same. */
    @Override
    public void clear() {
        size = 0;
        buckets = new LinkedList[16];
    }
    /* Returns true if hash map contains specific K key, false otherwise. */
    @Override
    public boolean containsKey(K key) {
        V value = get(key);
        return value != null;
    }
    public int size() { return size; }
    /* Utility function for get and put. Returns index corresponding to key to simplify methods. */
    private int getIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }
    /* Returns mapped value for specified key if key exists. If key doesn't exist, returns null. */
    @Override
    public V get(K key) {
        int index = getIndex(key);
        if (buckets[index] == null) {
            return null;
        }
        LinkedList<BucketItem> bucket = buckets[index];
        for (BucketItem item : bucket) {
            if (item.key.equals(key)) {
                return item.value;
            }
        }
        return null;
    }
    /* Searches for key. If key already exists, updates its mapped value to given V value.
     * If key doesn't exist, creates new key/value pair and stores in appropriate bucketItem. */
    public void put(K key, V value) {
        int index = getIndex(key);
        LinkedList<BucketItem> bucket = buckets[index];
        if (bucket != null) {
            for (BucketItem item : bucket) {
                if (item.key.equals(key)) {
                    item.value = value;
                    return;
                }
            }
        } else {
            bucket = new LinkedList<>();
            buckets[index] = bucket;
        }
        BucketItem pair = new BucketItem(key, value);
        bucket.add(pair);
        size++;
        double N = size;
        if (N / buckets.length >= loadFactor) {
            resize(buckets.length * 2);
        }
    }
    private void resize(int newSize) {
        LinkedList[] copy = buckets;
        buckets = new LinkedList[newSize];
        size = 0;
        for (int i = 0; i < copy.length; i++) {
            if (copy[i] != null) {
                LinkedList<BucketItem> curr = copy[i];
                for (BucketItem item : curr) {
                    put(item.key, item.value);
                }
            }
        }
    }
    /* Utility method. Creates a HashSet containing all keys in MyHashMap object. */
    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();
        for (LinkedList<BucketItem> bucket : buckets) {
            if (bucket != null) {
                for (BucketItem item : bucket) {
                    keys.add(item.key);
                }
            }
        }
        return keys;
    }
    @Override
    public V remove(K key) { throw new UnsupportedOperationException(); }
    @Override
    public V remove(K key, V value) { throw new UnsupportedOperationException(); }

    @Override
    public Iterator<K> iterator() {
        HashSet<K> keys = (HashSet<K>) keySet();
        return keys.iterator();
    }
    public static void main(String[] args) {
        /* MyHashMap dict = new MyHashMap();
        dict.put(1, 1);
        dict.put(3, 3);
        dict.put(9, 9);
        dict.put(7, 7);
        dict.put(8, 8);
        dict.put(2, 2);
        dict.put(4, 4);
        dict.put(6, 6);
        dict.put(5, 5); */
        double x = 5;
        System.out.print(x / 2);
    }
}
