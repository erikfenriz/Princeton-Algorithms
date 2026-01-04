import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Point array is null");

        Point[] copy = points.clone();

        for (Point p : copy) {
            if (p == null) throw new IllegalArgumentException("Point is null");
        }

        Arrays.sort(copy);

        for (int i = 1; i < copy.length; i++) {
            if (copy[i].compareTo(copy[i - 1]) == 0)
                throw new IllegalArgumentException("Duplicate points detected");
        }

        ArrayList<LineSegment> segmentList = new ArrayList<>();
        int n = copy.length;

        for (int i = 0; i < n; i++) {
            Point origin = copy[i];

            // Clone points and sort by slope relative to origin
            Point[] others = copy.clone();
            Arrays.sort(others, origin.slopeOrder());

            int count = 1;
            for (int j = 1; j < others.length; j++) {
                if (origin.slopeTo(others[j]) == origin.slopeTo(others[j - 1])) {
                    count++;
                } else {
                    if (count >= 3) {
                        addSegment(origin, others, j, count, segmentList);
                    }
                    count = 1; // reset for next slope
                }
            }

            // Check last run after loop
            if (count >= 3) {
                addSegment(origin, others, others.length, count, segmentList);
            }
        }

        segments = segmentList.toArray(new LineSegment[0]);
    }

    /**
     * Adds a line segment to the list if it is a maximal segment and avoids duplicates.
     *
     * @param origin the point currently treated as the origin in slope-sorting
     * @param others the array of all points sorted by slope to origin
     * @param endIndex the index in 'others' that marks the end of the current slope run
     * @param count the number of consecutive points (excluding origin) that share the same slope
     * @param list the list of LineSegment objects to which this segment may be added
     */
    private void addSegment(Point origin, Point[] others, int endIndex, int count, ArrayList<LineSegment> list) {

        // Create an array to hold all points in the collinear run, including the origin
        Point[] runPoints = new Point[count + 1];

        // The origin is always part of the run
        runPoints[0] = origin;

        // Copy the points from the slope run into runPoints
        // endIndex - count gives the start of the run in 'others'
        for (int k = 0; k < count; k++) {
            runPoints[k + 1] = others[endIndex - count + k];
        }

        // Sort the runPoints array to find the smallest and largest points
        // Smallest point will be runPoints[0], largest will be runPoints[runPoints.length - 1]
        Arrays.sort(runPoints);

        // Duplicate-prevention check:
        // Only add this segment if the origin is the smallest point in the run
        // This ensures that each maximal line segment is added exactly once
        if (origin.compareTo(runPoints[0]) == 0) {
            list.add(new LineSegment(runPoints[0], runPoints[runPoints.length - 1]));
        }
    }


    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone(); // defensive copy
    }

    public static void main(String[] args) {
        Point[] points = {
                new Point(0, 0),
                new Point(1, 1),
                new Point(0, 5),
                new Point(4, 5),
                new Point(10, 10),
                new Point(5, 4),
                new Point(3, 3),
                new Point(-3, -3),
                new Point(10, 8),
                new Point(15, 12),
                new Point(20, 16),
                new Point(25, 25),
                new Point(25, 20)
        };

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        System.out.println("Segments found: " + collinear.numberOfSegments());
        for (LineSegment seg : collinear.segments()) {
            System.out.println(seg);
        }
    }
}
