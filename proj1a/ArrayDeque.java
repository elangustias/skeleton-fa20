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
    private ItemType[] items; // DOES THIS GO HERE OR IN THE ALIST CONSTRUCTOR???

    public ArrayDeque() {
        items = (ItemType[]) new Object[8];
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
    public void resize(int capacity) {
        ItemType[] a = (ItemType[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }
    public void addFirst(ItemType item) {
        // If size is 8, ie if the list is empty, we will just make sure the next starting index is 0
        if (size == items.length + 8) {
            // HERE IS WHERE I'LL DO THE NECESSARY RESIZE OPERATION
        } else {
            // I've moved the task of checking whether the list is empty to the nextFirst method to keep this clean
            int nf = nextFirst();
            // These below might be able to be used generally for both the if and the else
            items[nf] = item;
            firstIndex = nf;
        }
        size += 1;
    }
    public int nextFirst() {
        int nf;
        // If list is empty, nextFirst needs to remain index 0
        if (size == 8) {
            nf = 0;
        // If first index is 0 BUT the list ins't empty, we can go ahead and assigna  new first index
        } else if (firstIndex == 0){
            nf = items.length - 1;
        } else {
            nf = firstIndex - 1;
        }
        return nf;
    }
    public void addLast(ItemType item) {
        if (size == items.length + 8) {
            // HERE IS WHERE I'LL DO THE NECESSARY RESIZE OPERATION
        } else {
            int nl = nextLast();
            items[nl] = item;
            lastIndex = nl;
        }
        size += 1;
    }
    public int nextLast() {
        int nl;
        if (size == 8 || lastIndex == items.length - 1) {
            nl = 0;
        } else {
            nl = lastIndex + 1;
        }
        return nl;
    }
    public void printDeque() {
        int i = firstIndex;
        int len = size - 8;
        while (len > 0) {
            if (i == items.length - 1) {
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