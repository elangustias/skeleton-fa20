public interface Deque<ItemType> {
    public void addFirst(ItemType item);
    public void addLast(ItemType item);
    default public boolean isEmpty() {
        return size() == 0;
    }
    public int size();
    public void printDeque();
    public ItemType removeFirst();
    public ItemType removeLast();
    public ItemType get(int index);
}
