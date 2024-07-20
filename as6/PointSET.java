/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PointSET {
    private SET<Point2D> pSet;
    private int numPoints;

    public PointSET() {
        pSet = new SET<>();
        numPoints = 0;
    }                       // construct an empty set of points

    public boolean isEmpty() {
        return pSet.size() == 0;
    }                    // is the set empty?

    public int size() {
        return numPoints;
    }                       // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        if (!pSet.contains(p)) {
            pSet.add(p);
            numPoints++;
        }
    }           // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        return pSet.contains(p);
    }         // does the set contain point p?

    public void draw() {
        if (pSet == null) throw new IllegalArgumentException("Set is Empty");
        for (Point2D x : pSet) {
            StdDraw.point(x.x(), x.y());
        }
    }                  // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null argument");
        List<Point2D> rectPoints = new ArrayList<>();

        for (Point2D x : pSet) {
            if (rect.contains(x)) {
                rectPoints.add(x);
            }
        }
        return Collections.unmodifiableList(rectPoints);
    }         // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        double closest = Double.POSITIVE_INFINITY;
        Point2D nearestPoint = null;
        for (Point2D x : pSet) {
            double temp = p.distanceSquaredTo(x);
            if (temp < closest) {
                closest = temp;
                nearestPoint = x;
            }
        }
        return nearestPoint;
    }           // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
    }              // unit testing of the methods (optional)
}
