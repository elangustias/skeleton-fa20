public class LinkedListDeque<ItemType> {
    private LinkedNode last;
    private LinkedNode first;
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
        first = sentinel;
        last = sentinel;
        size = 0;
    }
    public LinkedListDeque(ItemType item) {
        sentinel = new LinkedNode(null, null, null);
        sentinel.next = new LinkedNode(item, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        first = sentinel.next;
        last = sentinel.prev;
        size = 1;
    }

    public void addFirst(ItemType item) {
        sentinel.next = new LinkedNode(item, first, sentinel);
        first = sentinel.next;
        first.next.prev = first;
        size += 1;
    }
    public void addLast(ItemType item) {
        sentinel.prev = new LinkedNode(item, sentinel, last);
        last = sentinel.prev;
        last.prev.next = last;
        size += 1;
    }
}