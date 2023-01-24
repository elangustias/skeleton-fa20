package bearmaps;

import java.util.List;
import java.util.HashSet;

public class NaivePointSet implements PointSet {
    HashSet<Point> pSet = new HashSet<>();

    public NaivePointSet(List<Point> points) {
        pSet.addAll(points);
    }
    @Override
    public Point nearest(double x, double y) {
        Point p = new Point(x, y);
        Point bestPoint = null;
        double bestDist = Double.POSITIVE_INFINITY;
        for (Point point : pSet) {
            double dist = Point.distance(point, p);
            if (dist < bestDist) {
                bestPoint = point;
                bestDist = dist;
            }
        }
        return bestPoint;
    }
    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        double x = ret.getX();
        double y = ret.getY();
    }
}
