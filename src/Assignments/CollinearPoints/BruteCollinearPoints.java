import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class BruteCollinearPoints {
    private final ArrayList<LineSegment> A;

    private Point[] check(Point A, Point B, Point C, Point D) {
        Point[] P = new Point[4];
        if (A != null && B != null && C != null && D!= null) {
            P[0] =A; P[1] =B; P[2] =C; P[3] =D;
            return P;
        }
        throw new IllegalArgumentException();
    }
    private boolean comp(double x, double y, double z) {
        return (x==y ? x==z : false);
    }
    public BruteCollinearPoints(Point[] points) {
        if(points ==null) {throw new IllegalArgumentException();}
        ArrayList<Double> As;
        A = new ArrayList<LineSegment>();
        Arrays.sort(points);
        Point[] x;

        double slope;
        for(int i=0; i< points.length; i++) {
            As=new ArrayList<Double>();
            //if(K.contains(i)) continue;
            for(int j=i+1; j< points.length; j++) {
                if(points[i]==points[j]) {throw new IllegalArgumentException();}
                for(int k=j+1; k< points.length; k++) {
                    for(int w=k+1; w< points.length; w++) {
                        x= check(points[i], points[j], points[k], points[w]);
                        slope = x[0].slopeTo(x[1]);
                        if(comp(slope, x[1].slopeTo(x[2]),x[2].slopeTo(x[3]))) {
                            //for(Point n : x) { System.out.print(n +"\t");};System.out.println();
                            if(!As.contains(slope)) {
                                A.add(new LineSegment(x[0], x[3]));
                                As.add(slope);
                                K.add(j);K.add(k);K.add(i);
                            }
                        }
                    }
                }
            }
        }
    }   // finds all line segments containing 4 points
    public           int numberOfSegments()   {
        return A.size();
    }    // the number of line segments
    public LineSegment[] segments()   {
        return A.toArray(new LineSegment[A.size()]);
    }              // the line segments


    public static void main(String[] args) {

       // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}