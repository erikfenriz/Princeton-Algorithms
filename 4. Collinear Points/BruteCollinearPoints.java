import java.util.ArrayList;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private ArrayList<LineSegment> segmentList = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Point array is null");

        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException("Point is null");
        }

        Point[] copy = points.clone();

        java.util.Arrays.sort(copy);
        for (int i = 1; i < copy.length; i++) {
            if (copy[i].compareTo(copy[i-1]) == 0) {
                throw new IllegalArgumentException("Duplicate points detected");
            }
        }

        int n = copy.length;
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point p = copy[i];
                        Point q = copy[j];
                        Point r = copy[k];
                        Point s = copy[l];

                        // check if 4 points are collinear
                        double slopePQ = p.slopeTo(q);
                        double slopePR = p.slopeTo(r);
                        double slopePS = p.slopeTo(s);

                        if (slopePQ == slopePR && slopePQ == slopePS) {
                            // find min and max points
                            Point min = p;
                            Point max = p;
                            if (q.compareTo(min) < 0) min = q;
                            if (r.compareTo(min) < 0) min = r;
                            if (s.compareTo(min) < 0) min = s;

                            if (q.compareTo(max) > 0) max = q;
                            if (r.compareTo(max) > 0) max = r;
                            if (s.compareTo(max) > 0) max = s;

                            segmentList.add(new LineSegment(min, max));
                        }
                    }
                }
            }
        }
        segments = segmentList.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {
        Point[] points = {
                new Point(1000, 1000),
                new Point(2000, 2000),
                new Point(3000, 3000),
                new Point(4000, 4000),
                new Point(1000, 2000)
        };

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        System.out.println("Segments found: " + collinear.numberOfSegments());
        for (LineSegment seg : collinear.segments()) {
            System.out.println(seg);
        }
    }

}
