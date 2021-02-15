import java.util.TreeSet;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
public class PointSET {
    private final TreeSet<Point2D> T;
    public         PointSET()  {
        T = new TreeSet<Point2D>();
    }                        // construct an empty set of points
    public           boolean isEmpty() {
        return T.isEmpty();
    }            // is the set empty?
    public               int size()       {
        return T.size();
    }                   // number of points in the set
    public              void insert(Point2D p) {
        if(p == null) throw new IllegalArgumentException();
        T.add(p);
    }       // add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p)      {
        if(p == null) throw new IllegalArgumentException();
        return T.contains(p);
    }      // does the set contain point p?
    public              void draw()            {
        /*for(Point2D i : T) {
            i.draw();
        }*/
    }              // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect)      {
        if(rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> A = new ArrayList<Point2D>();
        for(Point2D i : T) {
            if(rect.contains(i)) A.add(i);
        }
        return A;
    }       // all points that are inside the rectangle (or on the boundary)
    public           Point2D nearest(Point2D p)       {
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
    }       // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        PointSET A = new PointSET();
        /*Point2D p1 = new Point2D(1, 1);
        Point2D p2 = new Point2D(2, 2);
        Point2D p3 = new Point2D(3, 3);
        Point2D p4 = new Point2D(4, 4);
        Point2D p5 = new Point2D(5, 5);
        Point2D p6 = new Point2D(6, 5);
        //p1.draw();
        A.insert(p1);
        A.insert(p2);
        A.insert(p3);
        A.insert(p4);
        A.insert(p5);
        A.insert(p6);
        System.out.println(A.contains(p2));*/
        RectHV r = new RectHV(1, 1, 10, 5);
        Iterable<Point2D> x = A.range(r);
        System.out.println(x);
        Point2D p = new Point2D(10, 10);
        System.out.println(A.nearest(p));
    }          // unit testing of the methods (optional)
}
