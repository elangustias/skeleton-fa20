import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testIsPalindrome() {
        CharacterComparator offByOne = new OffByOne();

        assertFalse(palindrome.isPalindrome("rudy"));
        assertFalse(palindrome.isPalindrome("alarcdala"));
        assertFalse(palindrome.isPalindrome(null));
        assertTrue(palindrome.isPalindrome("lol"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("alarcrala"));

        assertFalse(palindrome.isPalindrome("aza", offByOne));
        assertFalse(palindrome.isPalindrome("aa", offByOne));
        assertTrue(palindrome.isPalindrome("azmopnyb", offByOne));
        assertTrue(palindrome.isPalindrome("azmonyb", offByOne));
        assertTrue(palindrome.isPalindrome("a", offByOne));
    }
}