import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> segments;

    //find begin and end of line segment
    private void add(ArrayList<Point> p) {
        Point max = new Point(Integer.MIN_VALUE,Integer.MIN_VALUE);
        Point min = new Point(Integer.MAX_VALUE,Integer.MAX_VALUE);

        for(Point i : p) {
            if(max.compareTo(i) < 0) {
                max =i;
            }
            if(min.compareTo(i) > 0) {
                min =i;
            }
        }

        // Only add segment if current point is maximal, i.e, the limit of the LineSegment.
        // Avoid inserting subsegments.
        if(p.get(0) == min)
            segments.add(new LineSegment(min, max));
    }

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points)   {
        if(points == null) {throw new IllegalArgumentException();}

        for(int i=0; i< points.length; i++)
            if(points[i] == null) throw new IllegalArgumentException();
            
        for(int i=0; i< points.length; i++)
            for(int j=i+1; j< points.length; j++) 
                if(points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();


        segments = new ArrayList<LineSegment>();
        Point[] by_slopeTo = points.clone();
        double slope;

        /*
        
        - Think of p as the origin.
        - For each other point q, determine the slope it makes with p.
        - Sort the points according to the slopes they makes with p.
        - Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points, together with p, are collinear.
        
        */

        for(int p=0; p< points.length; p++) {
            Point curr = points[p];

            // sort whole array by slopeTo(curr)
            Arrays.sort(by_slopeTo, 0, by_slopeTo.length, curr.slopeOrder());
        
            // check whole array to avoid subsegments. 
            for(int q=0; q< by_slopeTo.length;) {
                slope = by_slopeTo[q].slopeTo(curr);
        
                ArrayList<Point> pts = new ArrayList<Point>(Arrays.asList(curr, by_slopeTo[q++]));
        
                // Get all points in this line.
                while(q < by_slopeTo.length && by_slopeTo[q].slopeTo(curr) == slope) 
                    pts.add(by_slopeTo[q++]);
        
                //At least 4 collinear points. 
                if(pts.size() >= 4) 
                    add(pts);
            }
        }
    } 
    
    // the number of line segments
    public int numberOfSegments()    {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments()   {
        return segments.toArray(new LineSegment[numberOfSegments()]);
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