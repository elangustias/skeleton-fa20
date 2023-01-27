package bearmaps;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.List;

public class KDTreeTimeTest {
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
    public static void timeNearest() {
        int size = 31250;
        int ops = 1000000;
        int numTests = 6;
        ArrayList<Integer> listOfSizes = new ArrayList<>();
        ArrayList<Integer> listOfOps = new ArrayList<>();
        ArrayList<Double> listOfTimes = new ArrayList<>();
        for (int in = 0; in < numTests; in++) {
            ArrayList<Point> listOfPoints = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Double randPoint = StdRandom.uniform(-1000.00, 1000.00);
                Double randPoint2 = StdRandom.uniform(-1000.00, 1000.00);
                Point p = new Point(randPoint2, randPoint);
                listOfPoints.add(p);
            }
            KDTree t = new KDTree(listOfPoints);
            Stopwatch sw = new Stopwatch();
            for (int index = 0; index < ops; index++) {
                Double randPoint = StdRandom.uniform(-1000.00, 1000.00);
                Double randPoint2 = StdRandom.uniform(-1000.00, 1000.00);
                t.nearest(randPoint, randPoint2);
            }
            double timeInSeconds = sw.elapsedTime();
            listOfSizes.add(size);
            listOfOps.add(ops);
            listOfTimes.add(timeInSeconds);
            size *= 2;
        }
        printTimingTable(listOfSizes, listOfTimes, listOfOps);
    }
    public static void timeConstructor() {
        int size = 31250;
        int numTests = 8;
        ArrayList<Integer> listOfSizes = new ArrayList<>();
        ArrayList<Integer> listOfOps = new ArrayList<>();
        ArrayList<Double> listOfTimes = new ArrayList<>();
        for (int in = 0; in < numTests; in++) {
            ArrayList<Point> listOfPoints = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Double randPoint = StdRandom.uniform(-1000.00, 1000.00);
                Double randPoint2 = StdRandom.uniform(-1000.00, 1000.00);
                Point p = new Point(randPoint2, randPoint);
                listOfPoints.add(p);
            }
            Stopwatch sw = new Stopwatch();
            KDTree t = new KDTree(listOfPoints);
            double timeInSeconds = sw.elapsedTime();
            listOfSizes.add(size);
            listOfOps.add(size);
            listOfTimes.add(timeInSeconds);
            size *= 2;
        }
        printTimingTable(listOfSizes, listOfTimes, listOfOps);
    }
    public static void main(String[] args) {
        timeNearest();
    }

}
