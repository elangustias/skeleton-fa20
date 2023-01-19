import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int size = 0;
    private double loadFactor;
    private ArrayList<LinkedList<BucketItem>> buckets; // probably wrong

    /* Constructor if user offers no initial size or load factor. */
    public MyHashMap() {
        buckets = new ArrayList<>(16);
        loadFactor = 0.75;
    }
    /* Constructor if user offers no load factor. */
    public MyHashMap(int initialSize) {
        buckets = new ArrayList<>(initialSize);
        loadFactor = 0.75;
    }
    /* Constructor if user offers both initial size and load factor. */
    public MyHashMap(int initialSize, double loadFactor) {
        buckets = new ArrayList<>(initialSize);
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
        buckets = new ArrayList<>(16);
    }
    /* Returns true if hash map contains specific K key, false otherwise. */
    @Override
    public boolean containsKey(K key) {
        V value = get(key);
        return value != null;
    }
    public int size() { return size; }
    /* Utility function for get and put. Returns bucket corresponding to key to simplify methods. */
    private LinkedList<BucketItem> getBucket(K key) {
        int code = key.hashCode();
        int index = code % buckets.size();
        return buckets.get(index);
    }
    /* Returns mapped value for specified key if key exists. If key doesn't exist, returns null. */
    @Override
    public V get(K key) {
        LinkedList<BucketItem> bucket = getBucket(key);
        if (bucket == null) {
            return null;
        }
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
        LinkedList<BucketItem> bucket = getBucket(key);
        if (bucket != null) {
            for (BucketItem item : bucket) {
                if (item.key.equals(key)) {
                    item.value = value;
                    return;
                }
            }
        }
        BucketItem pair = new BucketItem(key, value);
        if (bucket == null) {
            bucket = new LinkedList<>(); // figure out syntax later
            bucket.add(pair);
            int code = key.hashCode();
            buckets.add(code % buckets.size(), bucket);
        } else {
            bucket.add(pair);
        }
        size++;
        buckets = resize();
    }
    private ArrayList<LinkedList<BucketItem>> resize() {

        if ((Double.valueOf(size) / Double.valueOf(buckets.size())) < loadFactor) {
            return buckets;
        }
        MyHashMap<K, V> dummy = new MyHashMap(buckets.size()*2, loadFactor);
        for (LinkedList<BucketItem> bucket : buckets) {
            if (bucket != null) {
                for (BucketItem item : bucket) {
                    dummy.put(item.key, item.value);
                }
            }
        }
        return dummy.buckets;
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

}
