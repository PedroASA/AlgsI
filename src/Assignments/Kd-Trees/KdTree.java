import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

    private static final int xmin = 0;
    private static final int xmax = 1;
    private static final int ymin = 0;
    private static final int ymax = 1;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node left;        // the left/bottom subtree
        private Node right;        // the right/top subtree       

        private Node(Point2D p) {
            this.p = p;
        }
        private Node(Point2D p, RectHV r) {
            this.p = p;
            this.rect = r;
        }
    }

    private Node Root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        Root = null;
        size=0;
    }         
    // is the set empty?       
    public boolean isEmpty() {
        return Root==null;
    }            
    // number of points in the set
    public int size() {
        return size;
    }                   

    private boolean search(Node node, Point2D pnt, int level) {
        if(node == null) return false;
        //System.out.println(node.rect + "\t" + node.p);
        if(node.p.compareTo(pnt)==0) return true;
        boolean cmp = (level++ % 2==0) ? node.p.x() > pnt.x() : node.p.y() > pnt.y();
        if(cmp) return search(node.left, pnt, level);
        return search(node.right, pnt, level);
    }
    //rect NOde if(x) ? cmp(y)? .............
    private Node put(Node node, Point2D p, int level) {
        boolean x = level%2==0;
        boolean cmp = x ? node.p.x() > p.x() : node.p.y() > p.y();
        level++;
        if(cmp) {
            if(node.left!=null)node.left=put(node.left, p, level);
            else {
                RectHV r;
                if(x) r = new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax());
                else r = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y());
                node.left = new Node(p, r);
            }
        }
        else {
            if(node.right!=null) node.right=put(node.right, p, level);
            else {
                RectHV r;
                if(x) r = new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
                else r = new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax());
                node.right = new Node(p, r);
            }
        }
        return node;
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if(p == null) throw new IllegalArgumentException();
        if(Root ==null) {
            Root = new Node(p, new RectHV(xmin, ymin, xmax, ymax));
            size++;
        }
        else if(!search(Root, p, 0)) {
            Root = put(Root, p, 0);
            size++;
        }
    }
    // does the set contain point p?
    public boolean contains(Point2D p)      {
        if(p == null) throw new IllegalArgumentException();
        return search(Root, p, 0);
    }      
    // draw all points to standard draw
    public void draw() {}
                
    private void searchRange(RectHV rect, Node root, ArrayList<Point2D> A) {
        if(root!=null && rect.intersects(root.rect)) {
            if(rect.contains(root.p)) A.add(root.p);
            searchRange(rect, root.left, A);
            searchRange(rect, root.right, A);
        }
    }
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect)      {
        if(rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> A = new ArrayList<Point2D>();
        searchRange(rect, Root, A);
        return A;
    }       
    private Node searchNear(Node root, Point2D pnt, double min_dist, Node min) {
        if (root != null) {
            double dist = root.rect.distanceSquaredTo(pnt);
            Node x, y;
            if (dist <= min_dist) {
                boolean c = root.left != null ? root.left.rect.contains(pnt) : false;
                x = searchNear(c ? root.left : root.right, pnt, min_dist, min);
                dist = x.p.distanceSquaredTo(pnt);
                if(dist < min_dist) {
                    min_dist = dist;
                    min = x;
                }
                y = searchNear(c ? root.right : root.left, pnt, min_dist, min);
                if(y.p.distanceSquaredTo(pnt) < min_dist) min = y;
            }
        }
        return min;
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D pnt)       {
        if(pnt == null) throw new IllegalArgumentException();
        if(size==0) return null;
        Node min=Root;
        double dist=min.p.distanceSquaredTo(pnt);
        min = searchNear(Root, pnt, dist, min);
        return min.p;
    }
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        Point2D point = new Point2D(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
        StdOut.println(kdtree.nearest(point));

    }
}
