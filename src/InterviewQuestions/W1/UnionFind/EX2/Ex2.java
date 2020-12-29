import java.util.stream.IntStream;

public class Ex2 {
    private static class UFCanElem {
        private int[] size, parent, max;
        private int n;

        public UFCanElem(int n) {
            this.n = n;
            size = new int[n];
            parent = IntStream.rangeClosed(0, n - 1).toArray();
            max = parent.clone();
        }

        private int get_parent(int i) {
            if (parent[parent[i]] != parent[i])
                parent[i] = get_parent(i);
            return parent[i];
        }

        private void append(int i, int j) {
            size[i] += size[j];
            parent[j] = i;
            max[i] = Math.max(max[i], max[j]);
        }

        public void union(int i, int j) {
            i = get_parent(i);
            j = get_parent(j);
            if (i != j) {
                if (size[i] > size[j])
                    append(i, j);
                else
                    append(j, i);
            }
        }

        public boolean connected(int i, int j) {
            return get_parent(i) == get_parent(j);
        }

        public int find(int i) {
            return max[get_parent(i)];
        }
    }
    /*
    private static int rand(int x, int y) {
        return (int) Math.random() * ((x - y) + 1) + y;
    }
    
    public static void main(String[] args) {
        UFCanElem u = new UFCanElem(10);
        for (int i = 0; i <= rand(0, 9); i++) {
            u.union(rand(0, 9), rand(0, 9));
        }
        System.out.println(u.connected(u.find(5), 5));
    }
    */
}
