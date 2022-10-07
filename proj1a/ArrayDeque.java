public class ArrayDeque<ItemType> {
    /** INVARIANTS:
     *  1. The position of the next item to be added (using addLast) is always either index of first item + size, or if
     *     item + size is greater than length, (index of first item + size) - length...
     *  2. The number of items in the AList is always size
     *  3. The position of the last item in the CIRCULAR list is always either the index of the first item + size - 1,
     *     or if index first item + size - 1 is greater than length, (index of first item + size - 1) - length...
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
    public void addFirst(ItemType item) {
        if (size == items.length + 8) {
            addResize(size * 2);
        }
        int nf = plusFirst();
        items[nf] = item;
        firstIndex = nf;
        size += 1;
    }
    public int plusFirst() {
        int nf;
        // If list is empty, plusFirst needs to remain index 0.
        if (size == 8) {
            nf = 0;
        // If first index is 0 BUT the list isN't empty, we can go ahead and assign new first index.
        } else if (firstIndex == 0){
            nf = items.length - 1;
        } else {
            nf = firstIndex - 1;
        }
        return nf;
    }
    public void addLast(ItemType item) {
        if (size == items.length + 8) {
            addResize(size * 2);
        }
        int nl = plusLast();
        items[nl] = item;
        lastIndex = nl;
        size += 1;
    }
    public int plusLast() {
        int nl;
        if (size == 8 || lastIndex == items.length - 1) {
            nl = 0;
        } else {
            nl = lastIndex + 1;
        }
        return nl;
    }
    private void restart() {
        firstIndex = 0;
        lastIndex = 0;
    }
    public ItemType removeFirst() {
        if (size == 8) {return null;}
        ItemType rf = items[firstIndex];
        items[firstIndex] = null;
        size -= 1;
        if (items.length >= 16 && ((size-8) / items.length) < 0.25) {
            removeResize();
        }
        int nf = minusFirst();
        firstIndex = nf;
        return rf;
    }
    public ItemType removeLast() {
        if (size == 8) {return null;}
        ItemType rl = items[lastIndex];
        items[lastIndex] = null;
        size -= 1;
        if (items.length >= 16 && ((size-8) / items.length) < 0.25) {
            removeResize();
        }
        int nl = minusLast();
        lastIndex = nl;
        return rl;
    }
    /** MINUSFIRST/MINUSLAST serve two functions:
     *  1) If removing causes the list to become empty, it will reset the indices first, last to 0, 0.
     *  This is to avoid a fringe case where the empty list now has first and last pointing at different indices
     *  after updating only first or last through either of the two remove functions.
     *  2) Otherwise, it just updates the new index for first or last, just like plusFirst/plusLast.
     */
    private int minusFirst() {
        int nf;
        if (size == 8) {
            restart();
            nf = 0;
        } else if (firstIndex == items.length - 1) {
            nf = 0;
        } else {
            nf = firstIndex + 1;
        }
        return nf;
    }
    private int minusLast() {
        int nl;
        if (size == 8) {
            restart();
            nl = 0;
        } else if (lastIndex == 0) {
            nl = size - 1;
        } else {
            nl = lastIndex - 1;
        }
        return nl;
    }
    public void removeResize() {
        if (size == 8) {
            items = (ItemType[]) new Object[15];
        } else {
            /** Setting new length to size seems reasonable as size has 8 extra spaces, which is the midway
             *  point number right between having to make list bigger or smaller.
             */
            ItemType[] a = (ItemType[]) new Object[size];
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
            lastIndex = size - 9;
        }
    }
    public void addResize(int capacity) {
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
        lastIndex = size - 9;
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
    }
}