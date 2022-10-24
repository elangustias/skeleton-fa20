import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about AList construction.
 */
public class TimeAList {
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
    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        int size = 1000;
        ArrayList<Integer> listOfSizes = new ArrayList<>();
        ArrayList<Double> times = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            Stopwatch sw = new Stopwatch();
            AList<Integer> lst = new AList<>();
            for (int i2 = 0; i2 < size; i2++) {
                lst.addLast(1);
            }
            double timeInSeconds = sw.elapsedTime();
            listOfSizes.add(lst.size());
            times.add(timeInSeconds);
            size *= 2;
        }
        printTimingTable(listOfSizes, times, listOfSizes);
    }


}
