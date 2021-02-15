    import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
public class KdTree {
    private static final int xmin = 0;
    private static final int xmax = 1;
    private static final int ymin = 0;
    private static final int ymax = 1;

    private static class Node {
        private final Point2D p;      // the point
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
    public         KdTree()  {
        Root = null;
        size=0;
    }                        // construct an empty set of points
    public           boolean isEmpty() {
        return Root==null;
    }            // is the set empty?
    public               int size()       {
        return size;
    }                   // number of points in the set
    private boolean search(Node node, Point2D p, int level) {
        if(node == null) return false;
        //System.out.println(node.rect + "\t" + node.p);
        if(node.p.compareTo(p)==0) return true;
        boolean cmp = (level++ % 2==0) ? node.p.x() > p.x() : node.p.y() > p.y();
        if(cmp) return search(node.left, p, level);
        return search(node.right, p, level);
    }
    //rect NOde if(x) ? cmp(y)? .............
    private Node put(Node node, Point2D p, int level) {
        //if(node!=null)System.out.println(node.p);
        /*if(node ==null) {
            node = new Node(p, new RectHV(xmin, ymin, xmax, ymax));
            return node;
        }*/
        boolean cmp;
        boolean x = level%2==0;
        if(x) cmp = node.p.x() > p.x();
        else cmp = node.p.y() > p.y();
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
    public              void insert(Point2D p) {
        if(p == null) throw new IllegalArgumentException();
        if(Root ==null) {
            Root = new Node(p, new RectHV(xmin, ymin, xmax, ymax));
            size++;
        }
        else if(!search(Root, p, 0)) {
            Root = put(Root, p, 0);
            size++;
        }
    }       // add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p)      {
        if(p == null) throw new IllegalArgumentException();
        return search(Root, p, 0);
    }      // does the set contain point p?
    public              void draw()            {
        /*for(Node i : T) {
            i.p.draw();
        }*/
    }              // draw all points to standard draw
    private void searchRange(RectHV rect, Node root, ArrayList<Point2D> A) {
        if(root!=null && rect.intersects(root.rect)) {
            if(rect.contains(root.p)) A.add(root.p);
            searchRange(rect, root.left, A);
            searchRange(rect, root.right, A);
        }
    }
    public Iterable<Point2D> range(RectHV rect)      {
        if(rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> A = new ArrayList<Point2D>();
        searchRange(rect, Root, A);
        return A;
    }       // all points that are inside the rectangle (or on the boundary)
    private Node searchNear(Node root, Point2D p, double dist, Node min) {
        if (root != null) {
            //System.out.println(min.p);
            double dist2 = root.p.distanceSquaredTo(p);
            if (dist2 < dist) {
                dist = dist2;
                min = root;
            }
            Node x=null, y;
            x = searchNear(root.right, p, dist, min);
            y = searchNear(root.left, p, dist, min);
            x = (x.p.distanceSquaredTo(p) > y.p.distanceSquaredTo(p) ? y : x);
            /*if (root.left != null && root.right != null) {
                if (root.left.rect.contains(p)) {
                    x = searchNear(root.left, p, dist, min);
                    y = searchNear(root.right, p, dist, min);
                }
                else {
                    x = searchNear(root.right, p, dist, min);
                    y = searchNear(root.left, p, dist, min);
                }
                x = (x.p.distanceSquaredTo(p) > y.p.distanceSquaredTo(p) ? y : x);
            }
            if(root.left!=null)x=searchNear(root.left, p, dist, min);
            if(root.right!=null)x=searchNear(root.right, p, dist, min);
            if(x==null) return min;
            */dist2 = x.p.distanceSquaredTo(p);
            if(dist2 < dist) {
                min =x;
            }
            /*if (dist >= root.rect.distanceSquaredTo(p)) {
                if (root.left != null && root.right != null) {
                    if (root.left.rect.contains(p)) {
                        x = searchNear(root.left, p, dist, min);
                        y = searchNear(root.right, p, dist, min);
                    }
                    else {
                        x = searchNear(root.right, p, dist, min);
                        y = searchNear(root.left, p, dist, min);
                    }
                    x = (x.p.distanceSquaredTo(p) > y.p.distanceSquaredTo(p) ? y : x);
                }
                if(root.left!=null)x=searchNear(root.left, p, dist, min);
                if(root.right!=null)x=searchNear(root.right, p, dist, min);
                if(x!=null) {
                    dist2 = x.p.distanceSquaredTo(p);
                    if (dist > dist2) {
                        min = x;
                        dist = dist2;
                    }
                }
            }*/
        }
        return min;
    }
    public Point2D nearest(Point2D p)       {
        if(p == null) throw new IllegalArgumentException();
        if(size==0) return null;
        Node min=Root;
        double dist=min.p.distanceSquaredTo(p);
        min = searchNear(Root, p, dist, min);
        return min.p;
    }// a nearest neighbor in the set to point p; null if the set is empty
    public static void main(String[] args) {
        KdTree A = new KdTree();
        Point2D p1 = new Point2D(.7, .2);
        Point2D p2 = new Point2D(.5, .4);
        Point2D p3 = new Point2D(.2, .3);
        Point2D p4 = new Point2D(.4, .7);
        Point2D p5 = new Point2D(.9, .6);
        //Point2D p6 = new Point2D(.6, .5);
        A.insert(p1);
        A.insert(p2);
        A.insert(p3);
        A.insert(p4);
        A.insert(p5);
        //A.insert(p6);
        System.out.println(A.size());
        System.out.println(A.contains(p2));
        RectHV r = new RectHV(0.3, .3, .5, .5);
        Iterable<Point2D> x = A.range(r);
        System.out.println(x);
        Point2D p = new Point2D(.117, .579);
        System.out.println(A.nearest(p));
    }          // unit testing of the methods (optional)
}
