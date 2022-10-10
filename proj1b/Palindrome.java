public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> link = new LinkedListDeque<>();
        int index = 0;
        while (index < word.length()) {
            link.addLast(word.charAt(index));
            index++;
        }
        return link;
    }
}