import java.awt.*;

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
        if (link.size() < 2) {
            return true;
        }
        boolean firstIsLast = link.removeFirst() == link.removeLast();
        return (firstIsLast && isPalindromeHelper(link));
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        } else if (cc == null){
            return isPalindrome(word);
        } else {
            Deque<Character> link = wordToDeque(word);
            return isPalindromeHelper2(link, cc);
        }
    }
    public boolean isPalindromeHelper2(Deque<Character> link, CharacterComparator cc) {
        if (link.size() < 2) {
            return true;
        }
        boolean firstIsLast = cc.equalChars(link.removeFirst(), link.removeLast());
        return (firstIsLast && isPalindromeHelper2(link, cc));
    }
}