import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
    private ArrayList<LineSegment> A;

    private Point max(Point ... p) {
        Point Max = new Point(Integer.MIN_VALUE,Integer.MIN_VALUE);
        for(Point i : p) {
            if(Max.compareTo(i) < 0) {
                Max =i;
            }
        }
        return Max;
    }
    private Point min(Point ... p) {
        Point Min = new Point(Integer.MAX_VALUE,Integer.MAX_VALUE);
        for(Point i : p) {
            if(Min.compareTo(i) > 0) {
                Min =i;
            }
        }
        return Min;
    }
    private boolean comp(double x, double y, double z) {
        return (x==y ? x==z : false);
    }
    private Point[] check(Point A, Point B, Point C) {
        Point[] P = new Point[3];
        if (A != null && B != null && C != null) {
            P[0] =A; P[1] =B; P[2] =C;
            return P;
        }
        throw new IllegalArgumentException();
    }
    public FastCollinearPoints(Point[] points)   {
        if(points ==null) {throw new IllegalArgumentException();}
        A=new ArrayList<LineSegment>();
        Arrays.sort(points);
        ArrayList<Double> aux;
        ArrayList<Integer> K= new ArrayList<Integer>();
        Point w;
        Point[] x;
        double slope, slabs;
        if(points[0]==null) {throw new IllegalArgumentException();}
        for(int i=0; i< points.length-1; i++) {
            if(K.contains(i)) continue;
            w=points[i];
            aux =new ArrayList<Double>();
            Arrays.sort(points, i+1, points.length,w.slopeOrder());
            for(int k=i+1; k< points.length -2; k++) {
                x=check(points[k], points[k+1], points[k+2]);
                slope = x[0].slopeTo(w);
                if(comp(slope, x[1].slopeTo(w), x[2].slopeTo(w))) {
                    if(!aux.contains(slope)) {
                        A.add(new LineSegment(min(x[0], x[1], x[2], w), max(x[0], x[1], x[2], w)));
                        aux.add(slope);
                        K.add(k);
                    }
                }
            }
        }
    } // finds all line segments containing 4 or more points
    public           int numberOfSegments()    {
        return A.size();// the number of line segments
    }
    public LineSegment[] segments()   {
        return A.toArray(new LineSegment[A.size()]);// the line segments
    }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}