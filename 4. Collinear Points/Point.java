/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that == null) {
            throw new IllegalArgumentException();
        }

        if (that.x == this.x && that.y == this.y) {
           return Double.NEGATIVE_INFINITY;
        }
        else if (that.x == this.x) {
            return Double.POSITIVE_INFINITY;
        } else if (that.y == this.y) {
            return +0.0;
        } else {
            return (double) (that.y - this.y) / (that.x - this.x);
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        } else if (this.y > that.y) {
            return 1;
        } else {
            if (this.x < that.x) {
                return -1;
            } else if (this.x > that.x) {
                return 1;
            }
        }

        return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        Point outer = this;
        return new Comparator<Point>() {
            public int compare(Point p1, Point p2) {
                double slope1 = outer.slopeTo(p1);
                double slope2 = outer.slopeTo(p2);
                return Double.compare(slope1, slope2);
            }
        };
    }



    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        // Create sample points
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(0, 1);
        Point p4 = new Point(1, 0);
        Point p5 = new Point(0, 0); // duplicate of p1
        Point p6 = new Point(2, 2);

        System.out.println("compareTo tests:");
        System.out.println("p1 vs p2 (expect -1): " + p1.compareTo(p2));
        System.out.println("p2 vs p1 (expect 1): " + p2.compareTo(p1));
        System.out.println("p1 vs p5 (expect 0): " + p1.compareTo(p5));
        System.out.println("p3 vs p4 (expect 1): " + p3.compareTo(p4));

        System.out.println("\nslopeTo tests:");
        // Horizontal
        System.out.println("p1 -> p4 (horizontal, expect +0.0): " + p1.slopeTo(p4));
        // Vertical
        System.out.println("p1 -> p3 (vertical, expect +Infinity): " + p1.slopeTo(p3));
        // Same point
        System.out.println("p1 -> p5 (degenerate, expect -Infinity): " + p1.slopeTo(p5));
        // Regular slope
        System.out.println("p1 -> p2 (expect 1.0): " + p1.slopeTo(p2));
        // Another slope
        System.out.println("p4 -> p2 (expect 1.0): " + p4.slopeTo(p2));

        System.out.println("\nslopeOrder comparator tests:");
        Point origin = p1;
        Point[] arr = {p2, p3, p4, p6};
        Arrays.sort(arr, origin.slopeOrder());
        System.out.println("Points sorted by slope to p1:");
        for (Point p : arr) {
            System.out.println(p + " slope: " + origin.slopeTo(p));
        }

        System.out.println("\ntoString tests:");
        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);
        System.out.println("p3: " + p3);
    }

}