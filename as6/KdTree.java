/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private class KDNode {
        // instance variable of Node class
        private Point2D point;
        private KDNode left;
        private KDNode right;
        private final RectHV rect;

        // constructor
        private KDNode(Point2D point, RectHV rect) {
            this.point = point;
            this.left = null;
            this.right = null;
            this.rect = rect;
        }
    }

    private KDNode kdRoot;
    private PointSET points = new PointSET();
    private int treeSIZE = 0;

    public KdTree() {
        this.kdRoot = null;
    }                     // construct an empty set of points

    public boolean isEmpty() {
        return this.kdRoot == null;
    }         // is the set empty?

    public int size() {
        return treeSIZE;
    }            // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        if (points.contains(p)) {
            return;
        }
        else
            kdRoot = insert(kdRoot, p, new RectHV(0, 0, 1, 1), true);
    }

    private KDNode insert(KDNode node, Point2D point, RectHV rect, boolean isVertical) {
        if (node == null) {
            treeSIZE++;
            return new KDNode(point, rect);
        }

        if (node.point.equals(point)) return node;

        if (isVertical) {
            if (point.x() < node.point.x()) {
                RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
                node.left = insert(node.left, point, leftRect, !isVertical);
            }
            else {
                RectHV rightRect = new RectHV(node.point.x(), rect.ymin(), rect.xmax(),
                                              rect.ymax());
                node.right = insert(node.right, point, rightRect, !isVertical);
            }
        }
        else {

            if (point.y() < node.point.y()) {
                RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
                node.left = insert(node.left, point, leftRect, !isVertical);
            }
            else {
                RectHV rightRect = new RectHV(rect.xmin(), node.point.y(), rect.xmax(),
                                              rect.ymax());
                node.right = insert(node.right, point, rightRect, !isVertical);
            }
        }
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Null Argument");
        }
        return contains(kdRoot, p, true);
    }

    private boolean contains(KDNode root, Point2D point, boolean isVertical) {
        if (root == null) {
            return false;
        }
        if (root.point.equals(point)) {
            return true;
        }

        int cmp;
        if (isVertical) {
            cmp = Double.compare(point.x(), root.point.x());
        }
        else {
            cmp = Double.compare(point.y(), root.point.y());
        }

        if (cmp < 0) {
            return contains(root.left, point, !isVertical);
        }
        else {
            return contains(root.right, point, !isVertical);
        }
    }


    public void draw() {
        draw(kdRoot);
    }             // draw all points to standard draw'

    private void draw(KDNode node) {
        if (node == null) {
            return;
        }

        double x = node.point.x();
        double y = node.point.y();
        StdDraw.point(x, y);

        if (node.left != null) {
            draw(node.left);
        }
        if (node.right != null) {
            draw(node.right);
        }

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null argument");
        List<Point2D> rectPoints = new ArrayList<>();
        range(kdRoot, rect, rectPoints);
        return rectPoints;
    } // all points that are inside the rectangle (or on the boundary)

    private void range(KDNode node, RectHV rect, List<Point2D> rectPoints) {
        if (node == null) return;
        if (rect.intersects(node.rect)) {
            if (rect.contains(node.point)) {
                rectPoints.add(node.point);
            }
            if (node.left != null) {
                range(node.left, rect, rectPoints);
            }
            if (node.right != null) {
                range(node.right, rect, rectPoints);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null Argument");
        return nearest(kdRoot, p, null, Double.POSITIVE_INFINITY, true);
    }

    private Point2D nearest(KDNode root, Point2D point, Point2D nearestPoint,
                            double startDistance, boolean isVertical) {
        if (root == null) {
            return nearestPoint;
        }
        double distance = root.point.distanceSquaredTo(point);

        if (distance < startDistance) {
            nearestPoint = root.point;
            startDistance = distance;
        }

        int cmp;
        if (isVertical) {
            cmp = Double.compare(point.x(), root.point.x());
        }
        else {
            cmp = Double.compare(point.y(), root.point.y());
        }

        KDNode firstBranch = cmp < 0 ? root.left : root.right;
        KDNode secondBranch = cmp < 0 ? root.right : root.left;

        nearestPoint = nearest(firstBranch, point, nearestPoint, startDistance, !isVertical);
        startDistance = point.distanceSquaredTo(nearestPoint);

        if (shouldCheckOtherSubtree(root, point, startDistance, isVertical)) {
            nearestPoint = nearest(secondBranch, point, nearestPoint, startDistance, !isVertical);
        }
        return nearestPoint;
    }

    private boolean shouldCheckOtherSubtree(KDNode node, Point2D point, double currentBestDist,
                                            boolean isVertical) {
        if (isVertical) {
            return (point.x() - node.point.x()) * (point.x() - node.point.x()) < currentBestDist;
        }
        else {
            return (point.y() - node.point.y()) * (point.y() - node.point.y()) < currentBestDist;
        }
    }

    public static void main(String[] args) {
    }             // unit testing of the methods (optional)
}
