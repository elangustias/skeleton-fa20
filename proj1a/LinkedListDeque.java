public class LinkedListDeque<ItemType> {
    // PROJECT SITE USES "T" AS THE ITEMTYPE SO MIGHT HAVE TO UPDATE FOR TESTING
    // SETTING SENTINEL'S ITEM AS NULL HELPS ME OUT WITH THE GET METHOD;
    private int size;
    private LinkedNode sentinel;

    public class LinkedNode {
        public ItemType item;
        public LinkedNode next;
        public LinkedNode prev;
        public LinkedNode(ItemType i, LinkedNode n, LinkedNode p) {
            ItemType item = i;
            LinkedNode next = n;
            LinkedNode prev = p;
        }
    }

    public LinkedListDeque() {
        sentinel = new LinkedNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    public LinkedListDeque(ItemType item) {
        sentinel = new LinkedNode(null, null, null);
        sentinel.next = new LinkedNode(item, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    public void addFirst(ItemType item) {
        LinkedNode prevFirst = sentinel.next;
        sentinel.next = new LinkedNode(item, prevFirst, sentinel);
        prevFirst.prev = sentinel.next;
        size += 1;
    }
    public void addLast(ItemType item) {
        LinkedNode prevLast = sentinel.prev;
        sentinel.prev = new LinkedNode(item, sentinel, prevLast);
        prevLast.next = sentinel.prev;
        size += 1;
    }
    public boolean isEmpty() {
        return this.size == 0;
    }
    public int size() {
        return this.size;
    }
    public void printDeque() {
        LinkedNode dequeCopy = sentinel.next; // SHOULD I BE USING LINKEDNODE OR LINKELISTDEQUE HERE???
        while (dequeCopy != sentinel) {
            System.out.print(dequeCopy.item + " ");
        }
        System.out.println(""); // Project asks to print out new line after all items have been printed
    }
    public ItemType removeFirst() {
        if (size > 0) {
            ItemType rf = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return rf;
        }
        return null;
    }
    public ItemType removeLast() {
        if (size > 0) {
            ItemType rl = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return rl;
        }
        return null;
    }
    public ItemType get(int index) {
        LinkedNode dequeCopy = sentinel.next;
        // I NEED TO DO THIS BELOW IN ORDER TO ACOUNT FOR GETTING INPUTS ABOVE THE LENGTH OF THE DEQUE
        // DON'T NEED AN IF HERE BC FOR AN EMPTY LIST, THE WHILE LOOP WILL STOP IMMEDIATELY ANYWAY
        // SENTINEL FIRST IS SET AS NULL WHICH HELPS ME HERE, BUT IT MIGHT GIVE ERRORS IN FUTURE
        while (index > 0 && dequeCopy != sentinel) {
            dequeCopy = dequeCopy.next;
            index += 1;
        }
        return dequeCopy.item;
        // SENTINEL.ITEM IS NULL SO FOR THIS PROBLEM THAT WORKS PERFECTLY
    }
    public ItemType getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }
    // NOT SURE IF I'M ALLOWED TO DO THIS BUT IT SHOULD AT LEAST WORK
    private ItemType getRecursive(int index, LinkedNode dequeCopy) {
        if (index == 0 || dequeCopy == sentinel) {
            return dequeCopy.item;
        } else {
            return getRecursive(index-1, dequeCopy.next);
        }
    }
}