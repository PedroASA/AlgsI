/*
Failed Test3 (Bonus Memory Test)
*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> R =new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            R.enqueue(value);
        }
        Iterator<String> it = R.iterator();
        int i=0;
        while(it.hasNext() && i++ <k) StdOut.println(it.next());
    }
}
