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
    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        Deque<Character> link = wordToDeque(word);
        return isPalindromeHelper(link);
    }
    public boolean isPalindromeHelper(Deque<Character> link) {
        if (link.size() <= 1) {
            return true;
        }
        boolean firstIsLast = link.removeFirst() == link.removeLast();
        return (firstIsLast && isPalindromeHelper(link));
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        return true;
    }
}