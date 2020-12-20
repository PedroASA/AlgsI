/*
input: CSV file with the following pattern in each line:
timestamp, person1, person2
Sorted by timestamp.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.UF;

public class Ex1 {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        UF u = new UF(n);
        int time = 0;
        while (in.hasNextLine()) {
            time = in.readInt();
            u.union(in.readInt(), in.readInt());
            if (u.count() == 1)
                break;
        }
        if(u.count() == 1)
            System.out.println(time);
        else
            System.out.println("Members are never all connected");
    }
}
