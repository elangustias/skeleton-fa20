package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class KDTreeNearestTest {
    @Test
    public void testNearest() {
        int size = 10000;
        ArrayList<Point> listOfPoints = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Double randPoint = StdRandom.uniform(-1000.00, 1000.00);
            Double randPoint2 = StdRandom.uniform(-1000.00, 1000.00);
            Point p = new Point(randPoint2, randPoint);
            listOfPoints.add(p);
        }
        NaivePointSet naive = new NaivePointSet(listOfPoints);
        KDTree kd = new KDTree(listOfPoints);
        for (int in = 0; in < size; in++) {
            Double randPoint = StdRandom.uniform(-1000.00, 1000.00);
            Double randPoint2 = StdRandom.uniform(-1000.00, 1000.00);
            Point p = new Point(randPoint, randPoint2);
            Point naiveNearest = naive.nearest(randPoint, randPoint2);
            Point kdNearest = kd.nearest(randPoint, randPoint2);
            Double naiveDist = Point.distance(p, naiveNearest);
            Double kdDist = Point.distance(p, kdNearest);
            assertEquals(naiveDist, kdDist, 0.00000001);
        }
    }
}
