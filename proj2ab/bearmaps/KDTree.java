package bearmaps;
import java.util.List;

public class KDTree implements PointSet {
    private PointTree rootTree = null;

    public KDTree(List<Point> points) {
        for (Point point : points) {
            rootTree = add(rootTree, point, 0, null);
        }
    }
    private class PointTree {
        Point point;
        PointTree left, right, parent;
        private PointTree(Point point, PointTree parent) {
            this.point = point;
            this.parent = parent;
        }
    }
    private PointTree add(PointTree t, Point point, int depth, PointTree parent) {
        if (t == null || areEqual(t.point, point)) {
            return new PointTree(point, parent);
        }
        int order = compareByDimension(t, point, depth);
        if (order == 0) {
            t.left = add(t.left, point, depth + 1, t);
        } else {
            t.right = add(t.right, point, depth + 1, t);
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
        if (badSide != null) {
            Point bestBad = bestPointBad(point, t, depth);
            double distA = Point.distance(bestPoint, point);
            double distB = Point.distance(bestBad, point);
            if (distB < distA) {
                bestPoint = nearest(point, badSide, bestPoint, depth + 1);
            }
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
    private Double[] familyDimensions(PointTree t, int depth) {
        Double[] fd = new Double[4];
        if (depth % 2 == 0 && t.parent != null) {
            fd[0] = t.point.getY();
            fd[1] = t.parent.point.getY();
            if (depth > 2) {
                fd[2] = t.parent.parent.parent.point.getY();
            }
        } else if (depth % 2 == 1) {
            fd[0] = t.point.getX();
            fd[1] = t.parent.point.getX();
            if (depth > 2) {
                fd[2] = t.parent.parent.parent.point.getX();
            }
        }
        return fd;
    }
    private Point bestPointBad(Point point, PointTree t, int depth) {
        double greaterLimit = Double.POSITIVE_INFINITY;
        double lesserLimit = Double.NEGATIVE_INFINITY;
        double x = t.point.getX();
        double y = t.point.getY();
        /* Note: The code below is much cleaner than a solution that does the work done in
         * familyDimensions directly within bestPointBad, without the arrays, but it is also
         * a bit slower, taking ~2.2 seconds to compute 1 million nearest operations vs
         * the previous ~2.0 seconds for the same computation without the helper method.
         */
        Double[] fd = familyDimensions(t, depth);
        if (t.parent != null && fd[0] < fd[1]) {
            greaterLimit = fd[1];
        } else if (t.parent != null) {
            lesserLimit = fd[1];
        }
        if (depth > 2 && fd[2] < fd[1]) {
            lesserLimit = fd[2];
        } else if (depth > 2) {
            greaterLimit = fd[2];
        }
        Point best = createBestBad(point, t, depth, greaterLimit, lesserLimit);
        return best;
    }
    
    private Point createBestBad(Point point, PointTree t, int depth, double gLim, double lessLim) {
        Point best;
        if (depth % 2 == 0) {
            if (t.point.getY() < point.getY()) {
                best = new Point(t.point.getX(), Math.min(point.getY(), gLim));
            } else {
                best = new Point(t.point.getX(), Math.max(point.getY(), lessLim));
            }
        } else { // If depth % 2 == 1
            if (t.point.getX() < point.getX()) {
                best = new Point(Math.min(point.getX(), gLim), t.point.getY());
            } else {
                best = new Point(Math.max(point.getX(), lessLim), t.point.getY());
            }
        }
        return best;
    }
    public static void main(String[] args) {

    }
}
