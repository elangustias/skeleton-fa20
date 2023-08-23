package bearmaps;
import java.util.List;

public class KDTreeDummy implements PointSet {
    private PointTree rootTree = null;
    private int dimensions = 2;
    public KDTreeDummy(List<Point> points) {
        for (Point point : points) {
            rootTree = add(rootTree, point, 0);
        }
    }

    private class PointTree {
        Point point;
        PointTree[] children = new PointTree[dimensions];
        private PointTree(Point point) {
            this.point = point;
        }
    }

    private PointTree add(PointTree t, Point point, int currDim) {
        if (currDim == dimensions) {
            currDim = 0;
        }
        if (t == null) {
            return new PointTree(point);
        } else if (t.point.getX() == point.getX() && t.point.getY() == point.getY()) {
            return t;
        }
        int k = compareByDimension(t, point, currDim);
        t.children[k] = add(t.children[k], point, currDim + 1);
        return t;
    }

    @Override
    public Point nearest(double x, double y) {
        Point point  = new Point(x, y);
        return nearest(point, rootTree, rootTree.point, 0);
    }

    private Point nearest(Point point, PointTree t, Point bestPoint, int currDim) {
        if (t == null) {
            return bestPoint;
        }
        bestPoint = updateBest(bestPoint, t.point, point);
        int order = compareByDimension(t, point, currDim);
        PointTree goodSide = t.children[order];
        PointTree badSide = t.children[Math.abs(order - 1)];
        // Good side first
        bestPoint = nearest(point, goodSide, bestPoint, currDim + 1);
        double bestDist = Point.distance(bestPoint, point);
        double bestBadDist = bestPossibleBad(point, t, currDim);
        if (bestBadDist < bestDist) {
            bestPoint = nearest(point, badSide, bestPoint, currDim + 1);
        }
        return bestPoint;
    }

    /* PRIVATE HELPERS */
    private boolean areEqual(Point tPoint, Point point) {
        return tPoint.getX() == point.getX() && tPoint.getY() == point.getY();
    }
    private Point updateBest(Point bestPoint, Point currPoint, Point point) {
        double bestDist = Point.distance(bestPoint, point);
        double currDist = Point.distance(currPoint, point);
        if (currDist < bestDist) {
            bestDist = currDist;
            return currPoint;
        }
        return bestPoint;
    }
    private int compareByDimension(PointTree t, Point p, int depth) {
        double a, b;
        if (depth == 0) {
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
        if (currDim == 0) {
            return Math.abs(t.point.getX() - point.getX());
        }
        return Math.abs(t.point.getY() - point.getY());
    }
}
