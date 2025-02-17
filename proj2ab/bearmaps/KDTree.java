package bearmaps;
import java.util.List;

public class KDTree implements PointSet {
    private PointTree rootTree = null;
    public KDTree(List<Point> points) {
        for (Point point : points) {
            rootTree = add(rootTree, point, 0);
        }
    }

    private class PointTree {
        Point point;
        PointTree left, right;
        private PointTree(Point point) {
            this.point = point;
        }
    }

    private PointTree add(PointTree t, Point point, int depth) {
        if (t == null || areEqual(t.point, point)) {
            return new PointTree(point);
        }
        int order = compareByDimension(t, point, depth);
        if (order == 0) {
            t.left = add(t.left, point, depth + 1);
        } else {
            t.right = add(t.right, point, depth + 1);
        }
        return t;
    }

    @Override
    public Point nearest(double x, double y) {
        Point point  = new Point(x, y);
        return nearest(point, rootTree, rootTree.point, 0);
    }

    private Point nearest(Point point, PointTree t, Point bestPoint, int depth) {
        if (t == null) {
            return bestPoint;
        }
        bestPoint = updateBest(bestPoint, t.point, point);
        PointTree goodSide, badSide;
        int order = compareByDimension(t, point, depth);
        if (order == 0) {
            goodSide = t.left;
            badSide = t.right;
        } else {
            goodSide = t.right;
            badSide = t.left;
        }
        bestPoint = nearest(point, goodSide, bestPoint, depth + 1);

        double bestDist = Point.distance(bestPoint, point);
        double bestBadDist = bestPossibleBad(point, t, depth);
        if (bestBadDist < bestDist) {
            bestPoint = nearest(point, badSide, bestPoint, depth + 1);
        }
        return bestPoint;
    }

    private boolean areEqual(Point tPoint, Point point) {
        return tPoint.getX() == point.getX() && tPoint.getY() == point.getY();
    }

    private Point updateBest(Point bestPoint, Point currPoint, Point point) {
        double bestDist = Point.distance(bestPoint, point);
        double currDist = Point.distance(currPoint, point);
        if (currDist < bestDist) {
            return currPoint;
        }
        return bestPoint;
    }

    private int compareByDimension(PointTree t, Point p, int depth) {
        double a, b;
        if (depth % 2 == 0) {
            a = t.point.getX();
            b = p.getX();
        } else {
            a = t.point.getY();
            b = p.getY();
        }
        if (b >= a) {
            return 1;
        } else {
            return 0;
        }
    }
    private double bestPossibleBad(Point point, PointTree t, int currDim) {
        if (currDim % 2 == 0) {
            return Math.abs(t.point.getX() - point.getX());
        }
        return Math.abs(t.point.getY() - point.getY());
    }
}
