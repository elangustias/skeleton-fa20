import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private int size = 0;
    private BST rootTree;
    private class BST {
        K key;
        V value;
        BST left, right;
        BST(K key, V value, BST left, BST right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
        private BST get(K key) {
            if (this == null) {
                return null;
            }
            int cmp = key.compareTo(this.key);
            if (cmp == 0) {
                return this;
            } else if (cmp < 0) {
                return left.get(key);
            } else {
                return right.get(key);
            }
        }

    }
    @Override
    public void clear() {
        size = 0;
        rootTree = null;
    }
    @Override
    public boolean containsKey(K key) {
        V value = get(key);
        return value != null;
    }
    @Override
    public V get(K key) {
        if (rootTree == null) {
            return null;
        }
        BST tree = rootTree.get(key);
        if (tree == null) {
            return null;
        }
        return tree.value;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void put(K key, V value) {
        rootTree = put(key, value, rootTree);
    }
    private BST put(K key, V value, BST tree) {
        if (tree == null) {
            size++;
            return new BST(key, value, null, null);
        }
        int cmp = key.compareTo(tree.key);
        if (cmp < 0) {
            tree.left = put(key, value, tree.left);
        } else if (cmp > 0) {
            tree.right = put(key, value, tree.right);
        } else {
            tree.value = value;
        }
        return tree;
    }
    public void printInOrder() {
        printHelper(rootTree);
    }
    private void printHelper(BST tree) {
        if (tree.left != null) {
            printHelper(tree.left);
        }
        System.out.print(tree.value + " ");
        if (tree.right != null) {
            printHelper(tree.right);
        }
    }

    @Override
    public Set<K> keySet() { throw new UnsupportedOperationException(); }

    @Override
    public V remove(K key) { throw new UnsupportedOperationException(); }

    @Override
    public V remove(K key, V value) { throw new UnsupportedOperationException(); }

    @Override
    public Iterator<K> iterator() { throw new UnsupportedOperationException(); }
    public static void main(String[] args) {
        BSTMap dict = new BSTMap();
        dict.put(1, 1);
        dict.put(3, 3);
        dict.put(9, 9);
        dict.put(7, 7);
        dict.put(8, 8);
        dict.put(2, 2);
        dict.put(4, 4);
        dict.put(6, 6);
        dict.put(5, 5);
        dict.printInOrder();
        System.out.println();
        System.out.println(dict.size());
    }
}
