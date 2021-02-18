import java.util.TreeSet;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
public class PointSET {
    private final TreeSet<Point2D> T;
    // construct an empty set of points
    public         PointSET()  {
        T = new TreeSet<Point2D>();
    }                   
    // is the set empty?    
    public boolean isEmpty() {
        return T.isEmpty();
    }
    // number of points in the set    
    public int size() {
        return T.size();
    }                   
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if(p == null) throw new IllegalArgumentException();
        T.add(p);
    }
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if(p == null) throw new IllegalArgumentException();
        return T.contains(p);
    }
    // draw all points to standard draw
    public void draw() {
        for(Point2D i : T) i.draw();
    }          
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> A = new ArrayList<Point2D>();
        for(Point2D i : T) {
            if(rect.contains(i)) A.add(i);
        }
        return A;
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if(p == null) throw new IllegalArgumentException();
        if(T.isEmpty()) return null;
        Point2D min = (!T.first().equals(p) ? T.first() : T.last());
        double dist = p.distanceSquaredTo(min);
        for(Point2D i : T) {
            double tmp =p.distanceSquaredTo(i);
            if(tmp < dist) {
                min = i;
                dist = tmp;
            }
        }
        return min;
    }       
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET A = new PointSET();
        RectHV r = new RectHV(1, 1, 10, 5);
        Iterable<Point2D> x = A.range(r);
        System.out.println(x);
        Point2D p = new Point2D(10, 10);
        System.out.println(A.nearest(p));
    }          
}
