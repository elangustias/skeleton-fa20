
/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
    public static void main(String[] args) {
        Palindrome palindrome = new Palindrome();
        int minLength = 4;
        int biggestLength = 0;
        String biggestWord = "";
        int biggestN = 0;
        int biggestIndex = 0;
        int biggestWordN = 0;
        for (int i = 1; i <= 26; i += 1) {
            In in = new In("../../library-fa20/data/words.txt");
            int currN = 0;
            CharacterComparator offByN = new OffByN(i);
            while (!in.isEmpty()) {
                String word = in.readString();
                if (palindrome.isPalindrome(word, offByN)) {
                    currN += 1;
                    if (word.length() > biggestLength) {
                        biggestLength = word.length();
                        biggestWord = word;
                        biggestWordN = i;
                    }
                }
            }
            System.out.println("N: " + i + ", Number of Palindromes: " + currN);
            if (currN > biggestN) {
                biggestN = currN;
                biggestIndex = i;
            }
        }
        System.out.println("N with most palindromes: " + biggestIndex + ", with " + biggestN + " palindromes.");
        System.out.println("Biggest palindrome of any N is " + biggestWord + " at N: " + biggestWordN + " with "
                           + biggestWord.length() + " chars.");
    }
}
