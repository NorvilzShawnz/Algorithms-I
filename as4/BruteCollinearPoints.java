/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("The array passed in is Null");
        }
        for (Point x : points) {
            if (x == null) {
                throw new java.lang.IllegalArgumentException(
                        "There exists a null value in the array of Points");
            }
        }
        // Check for duplicate Values
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new java.lang.IllegalArgumentException(
                            "Array contains duplicate points.");
                }
            }
        }

        // Implement collinear points detection
        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p + 1; q < points.length - 2; q++) {
                for (int r = q + 1; r < points.length - 1; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r]) &&
                                points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
                            // Found 4 collinear points, add the segment
                            Point[] collinearPoints = {
                                    points[p], points[q], points[r], points[s]
                            };
                            Arrays.sort(collinearPoints);
                            LineSegment segment = new LineSegment(collinearPoints[0],
                                                                  collinearPoints[3]);
                            segments.add(segment);
                        }
                    }
                }
            }
        }


    }    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return segments.size();
    }    // the number of line segments

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }               // the line segments

    public static void main(String[] args) {
        Point[] pointsArray;

        // Step 2: Create the array object with a specific size (e.g., size = 5)
        pointsArray = new Point[5];

        // Step 3: Initialize each element of the array
        pointsArray[0] = new Point(1, 2);
        pointsArray[1] = new Point(3, 4);
        pointsArray[2] = new Point(5, 6);
        pointsArray[3] = new Point(7, 8);
        pointsArray[4] = new Point(9, 10);

        BruteCollinearPoints bcp = new BruteCollinearPoints(pointsArray);

        int x = bcp.numberOfSegments();
        System.out.print("Number of segments: " + x);
    }
}

/*************************************************************************
 *  Compilation:  javac LineSegment.java
 *  Execution:    none
 *  Dependencies: Point.java
 *
 *  An immutable data type for Line segments in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 *  DO NOT MODIFY THIS CODE.
 *
 *************************************************************************/

class LineSegment {
    private final Point p;   // one endpoint of this line segment
    private final Point q;   // the other endpoint of this line segment

    /**
     * Initializes a new line segment.
     *
     * @param p one endpoint
     * @param q the other endpoint
     * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt>
     *                              is <tt>null</tt>
     */
    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new IllegalArgumentException("argument to LineSegment constructor is null");
        }
        if (p.equals(q)) {
            throw new IllegalArgumentException(
                    "both arguments to LineSegment constructor are the same point: " + p);
        }
        this.p = p;
        this.q = q;
    }


    /**
     * Draws this line segment to standard draw.
     */
    public void draw() {
        p.drawTo(q);
    }

    /**
     * Returns a string representation of this line segment
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this line segment
     */
    public String toString() {
        return p + " -> " + q;
    }

    /**
     * Throws an exception if called. The hashCode() method is not supported because
     * hashing has not yet been introduced in this course. Moreover, hashing does not
     * typically lead to good *worst-case* performance guarantees, as required on this
     * assignment.
     *
     * @throws UnsupportedOperationException if called
     */
    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported");
    }
}

