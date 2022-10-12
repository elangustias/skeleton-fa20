public class OffByN implements CharacterComparator {
    int n;
    public OffByN(int num) {
        n = num;
    }
    @Override
    public boolean equalChars(char x, char y) {
        int diff = Math.abs((int) x - (int) y);
        return diff == n;
    }
}