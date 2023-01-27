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
        int order = pickDirection(t, point, depth);
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
        int order = pickDirection(t, point, depth);
        if (order == 0) {
            goodSide = t.left;
            badSide = t.right;
        } else {
            goodSide = t.right;
            badSide = t.left;
        }
        bestPoint = nearest(point, goodSide, bestPoint, depth + 1);
        if (badSide != null) {
            Point bad = bestPointBad(point, t, depth);
            double distA = Point.distance(bestPoint, point);
            double distB = Point.distance(bad, point);
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
    private int pickDirection(PointTree t, Point p, int depth) {
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
    private Point bestPointBad(Point point, PointTree t, int depth) {
        double greaterLimit = Double.POSITIVE_INFINITY;
        double lesserLimit = Double.NEGATIVE_INFINITY;
        double x = t.point.getX();
        double y = t.point.getY();
        PointTree p = t.parent;
        Point best;
        if (depth % 2 == 0 && p != null) {
            double pY = p.point.getY();
            if (y < pY) {
                greaterLimit = pY;
                if (depth > 2) {
                    double pppY = p.parent.parent.point.getY();
                    if (pppY < pY) {
                        lesserLimit = pppY;
                    }
                }
                } else { // If y > pY
                    lesserLimit = pY;
                    if (depth > 2) {
                        double pppY = p.parent.parent.point.getY();
                        if (pppY >= pY) {
                            greaterLimit = pppY;
                        }
                    }
                }
            } else if (depth % 2 == 1) { // If depth % 2 == 1 (p automatically isn't null)
                double pX = p.point.getX();
                if (x < pX) { // these might need to be <=
                    greaterLimit = pX;
                    if (depth > 2) {
                        double pppX = p.parent.parent.point.getX();
                        if (pppX < pX) {
                            lesserLimit = pppX;
                        }
                    }
                } else { // If x >= pX
                    lesserLimit = pX;
                    if (depth > 2) {
                        double pppX = p.parent.parent.point.getX();
                        if (pppX >= pX) {
                            greaterLimit = pppX;
                        }
                    }
                }
            }
            if (depth % 2 == 0) {
                if (y < point.getY()) {
                    best = new Point(x, Math.min(point.getY(), greaterLimit));
                } else {
                    best = new Point(x, Math.max(point.getY(), lesserLimit));
                }
            } else { // if depth % 2 == 1
                if (x < point.getX()) {
                    best = new Point(Math.min(point.getX(), greaterLimit), y);
                } else {
                    best = new Point(Math.max(point.getX(), lesserLimit), y);
                }
            }
            return best;
        }
    public static void main(String[] args) {
        Point A = new Point(2.0, 3.0);
        Point B = new Point(4.0, 2.0);
        Point C = new Point(4.0, 5.0);
        Point D = new Point(3.0, 3.0);
        Point E = new Point(1, 5);
        Point F = new Point(4, 4);
        KDTree nn = new KDTree(List.of(A, B, C, D, E, F));
        Point ret = nn.nearest(0, 7); // returns p2
        double x = ret.getX();
        double y = ret.getY();
    }
}
