public class ArrayDeque<ItemType> {
    /** INVARIANTS:
     *  1. The position of the next item to be added (using addLast) is always size
     *  2. The number of items in the AList is always size
     *  3. The position of the last item in the list is always size - 1
     */
    private int size;
    private ItemType[] items; // DOES THIS GO HERE OR IN THE ALIST CONSTRUCTOR???

    public class AList {
        // ItemType[] items;
        // int size;
        public AList() {
            items = (ItemType[]) new Object[100];
            size = 8;
        }
    }
    public void resize(int capacity) {
        ItemType[] a = (ItemType[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }
    public void addLast(ItemType item) {
        if (size == items.length) {
            resize(size*2);
        }
        items[size] = item;
        size += 1;
    }
    public ItemType getLast() {
        return items[size-1];
    }
    public ItemType get(int i) {
        return items[i];
    }
    public int size() {
        return size;
    }
    public void removeLast() {
        ItemType last = getLast();
        items[size-1] = null;
        size -= 1;
        return last; // DON'T KNOW WHY I'M GETTING AN ERROR HERE
    }

    public static void main(String[] args) {}
}