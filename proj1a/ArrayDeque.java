public class ArrayDeque<ItemType> {
    /** INVARIANTS:
     *  1. The position of the next item to be added (using addLast) is always either firstIndex + size, or if
     *     firstIndex + size is greater than length, firstIndex + size - length.
     *  2. The number of items in the array is always size.
     *  3. The position of the last item in the CIRCULAR list is always either firstIndex + size - 1,
     *     or if firstIndex + size - 1 is greater than length, firstIndex + size - 1 - length.
     */
    private int size;
    private int firstIndex;
    private int lastIndex;
    private ItemType[] items;

    public ArrayDeque() {
        items = (ItemType[]) new Object[15];
        size = 8; // WHY????
        firstIndex = 0;
        lastIndex = 0;
    }
    public boolean isEmpty() {
        return size == 8;
    }
    public int size() {
        return size - 8;
    }

    /** Resizes through resize method if list is full. Checks where the next first index should be through
     *  indexMovesLeft method, then adds the new first item to items. If method creates the sole item in items,
     *  keeps first and last indexes equal.
     */
    public void addFirst(ItemType item) {
        if (size == items.length + 8) {
            resize(size * 2);
        }
        if (size > 8) {
            firstIndex = indexMovesLeft(firstIndex);
        }
        items[firstIndex] = item;
        size += 1;
    }

    /** Resizes through resize if list is full. Checks where the next last index should be through
     *  indexMovesRight method, then adds a new last item to items. If method creates the sole item in items,
     *  keeps first and last indexes equal.
     */
    public void addLast(ItemType item) {
        if (size == items.length + 8) {
            resize(size * 2);
        }
        if (size > 8) {
            lastIndex = indexMovesRight(lastIndex);
        }
        items[lastIndex] = item;
        size += 1;
    }
    /** Removes first item. Checks if items needs to be trimmed down through resize. If removing an item causes
     *  the list to become empty, keeps first and last indexes equal. Otherwise, reassigns new first index through
     *  indexMovesRight. Returns the removed first item.
     */
    public ItemType removeFirst() {
        if (size > 8) {
            ItemType firstItem = items[firstIndex];
            items[firstIndex] = null;
            size -= 1;
            if (items.length >= 16 && (size - 8) * 100 / items.length < 25) {
                firstIndex = indexMovesRight(firstIndex);
                resize(size);
            } else if (size > 8) {
                firstIndex = indexMovesRight(firstIndex);
            }
            return firstItem;
        }
        return null;
    }
    /** Removes last item. Checks if items need to be trimmed down through resize. If removing an item causes
     *  the list to become empty, keeps first and last indexes equal. Otherwise, reassigns new last index through
     *  indexMovesLeft. Returns the removed last item.
     */
    public ItemType removeLast() {
        if (size > 8) {
            ItemType lastItem = items[lastIndex];
            items[lastIndex] = null;
            size -= 1;
            if (items.length >= 16 && (size - 8) * 100 / items.length < 25) {
                lastIndex = indexMovesLeft(lastIndex);
                resize(size);
            } else if (size > 8) {
                lastIndex = indexMovesLeft(lastIndex);
            }
            return lastItem;
        }
        return null;
    }
    // Gives next first index when adding a first item OR next last index when removing a last item.
    public int indexMovesLeft(int currIndex) {
        int newIndex;
        if (currIndex == 0){
            newIndex = items.length - 1;
        } else {
            newIndex = currIndex - 1;
        }
        return newIndex;
    }
    // Gives next last index when adding a last item OR next first index when removing a first item.
    public int indexMovesRight(int currIndex) {
        int newIndex;
        if (currIndex == items.length - 1) {
            newIndex = 0;
        } else {
            newIndex = currIndex + 1;
        }
        return newIndex;
    }
    public void resize(int capacity) {
        ItemType[] a = (ItemType[]) new Object[capacity];
        int oldIndex = firstIndex;
        int newIndex = 0;
        int len = size - 8;
        while (len > 0) {
            if (oldIndex == items.length) {
                oldIndex = 0;
            }
            a[newIndex] = items[oldIndex];
            oldIndex += 1;
            newIndex += 1;
            len -= 1;
        }
        items = a;
        firstIndex = 0;
        lastIndex = newIndex - 1;
    }
    public ItemType get(int index) {
        if (index >= size - 8) {
            return null;
        } else if (firstIndex + index > items.length) {
            return items[firstIndex + index - items.length];
        } else {
            return items[index];
        }
    }
    public void printDeque() {
        int i = firstIndex;
        int len = size - 8;
        while (len > 0) {
            if (i == items.length) {
                i = 0;
            }
            System.out.print(items[i] + " ");
            i += 1;
            len -= 1;
        }
        System.out.println("");
    }
    public static void main(String[] args) {
        ArrayDeque<Integer> a = new ArrayDeque<>();
        System.out.println(a.isEmpty());
        System.out.println(a.size());
        a.addLast(3);
        a.addLast(2);
        System.out.println(a.size());
        a.addLast(1);
        System.out.println(a.size());
        System.out.println(a.isEmpty());
        a.printDeque();
        a.addFirst(5);
        a.addFirst(8);
        a.addFirst(21);
        System.out.println(a.size());
        a.addLast(30);
        a.addLast(32);
    }
}