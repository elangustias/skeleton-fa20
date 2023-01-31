package bearmaps;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import org.junit.*;
import java.util.*;

public class ArrayHeapMinPQTest {
    /* SOURCES:
     * https://stackoverflow.com/questions/20536566/creating-a-random-string-with-a-z-and-0-9-in-java
     */
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }
    protected static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    @Test
    public void testMethods() {
        int strSize = 500;
        NaiveMinPQ<String> naive = new NaiveMinPQ<>();
        ArrayHeapMinPQ<String> ah = new ArrayHeapMinPQ<>();
        Random rand = new Random();

        for (int i = 0; i < strSize; i++) {
            String str = getSaltString();
            double priority = rand.nextDouble();
            naive.add(str, priority);
            ah.add(str, priority);
            String s = naive.getSmallest();
            String s2 = ah.getSmallest();
            Assert.assertEquals(s, s2);
        }
        for (int in = 0; in < strSize; in++) {
            String sm = naive.getSmallest();
            double randDouble = rand.nextDouble();
            naive.changePriority(sm, randDouble);
            ah.changePriority(sm, randDouble);
        }
        for (int in = 0; in < strSize-1; in++) {
            ah.removeSmallest();
            naive.removeSmallest();
            Assert.assertEquals(ah.getSmallest(), naive.getSmallest());
        }
    }
    public static void testAddTime() {
        int size = 31250;
        int ops = size;
        int numTests = 8;
        ArrayList<Integer> listOfSizes = new ArrayList<>();
        ArrayList<Integer> listOfOps = new ArrayList<>();
        ArrayList<Double> listOfTimes = new ArrayList<>();
        Random rand = new Random();
        for (int in = 0; in < numTests; in++) {
            Stopwatch sw = new Stopwatch();
            //NaiveMinPQ<String> pq = new NaiveMinPQ<>();
            ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
            for (int i = 0; i < size; i++) {
                String str = getSaltString();
                double rd = rand.nextDouble();
                pq.add(str, rd);
            }
            double timeInSeconds = sw.elapsedTime();
            listOfSizes.add(size);
            listOfOps.add(ops);
            listOfTimes.add(timeInSeconds);
            size *= 2;
            ops = size;
        }
        printTimingTable(listOfSizes, listOfTimes, listOfOps);
    }
    public static void testContainsTime() {
        int size = 31250;
        int ops = 1000;
        int numTests = 8;
        ArrayList<Integer> listOfSizes = new ArrayList<>();
        ArrayList<Integer> listOfOps = new ArrayList<>();
        ArrayList<Double> listOfTimes = new ArrayList<>();
        Random rand = new Random();
        for (int in = 0; in < numTests; in++) {
            //NaiveMinPQ<String> pq = new NaiveMinPQ<>();
            ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
            for (int i = 0; i < size; i++) {
                String str = getSaltString();
                double rd = rand.nextDouble();
                pq.add(str, rd);
            }
            Stopwatch sw = new Stopwatch();
            for (int i = 0; i < ops; i++) {
                String str = getSaltString();
                pq.contains(str);
            }
            double timeInSeconds = sw.elapsedTime();
            listOfSizes.add(size);
            listOfOps.add(ops);
            listOfTimes.add(timeInSeconds);
            size *= 2;
        }
        printTimingTable(listOfSizes, listOfTimes, listOfOps);
    }
    public static void main(String[] args) {
        testContainsTime();
    }
}
