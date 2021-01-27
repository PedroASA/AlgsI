import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class BruteCollinearPoints {
    private final ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if(points == null) {throw new IllegalArgumentException();}

        for(int i=0; i< points.length; i++)
            if(points[i] == null) throw new IllegalArgumentException();
            
        for(int i=0; i< points.length; i++)
            for(int j=i+1; j< points.length; j++) 
                if(points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();


        segments = new ArrayList<LineSegment>();
        Point[] cp = points.clone();
        Arrays.sort(cp);

        double slope;

        ArrayList<Double> slopes;

        for(int p=0; p< cp.length; p++) {
            slopes = new ArrayList<Double>();
            for(int q=p+1; q< cp.length; q++) {
                slope = cp[p].slopeTo(cp[q]);
                for(int r=q+1; r< cp.length; r++) {
                    if(slope != cp[p].slopeTo(cp[r])) continue;
                    for(int s=r+1; s< cp.length; s++) {
                        if(!slopes.contains(slope) && slope == cp[p].slopeTo(cp[s])) {
                                segments.add(new LineSegment(cp[p], cp[s]));
                                slopes.add(slope);
                        }

                    }
                }
            }
        }

        
    }   
    // the number of line segments
    public int numberOfSegments()   {
        return segments.size();
    }    
    
    // the line segments
    public LineSegment[] segments()   {
        return segments.toArray(new LineSegment[segments.size()]);
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

        //print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}