import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item RQueue [];
	private int size;
	private int end;

    // construct an empty randomized queue
    public RandomizedQueue() {
    	RQueue = (Item[]) new Object[1];
    	size=1;
    	end=0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
    	return (end==0);
    }

    // return the number of items on the randomized queue
    public int size() {
    	return end;
    }
	private void resize(int newSize) {
		size= size == 0 ? 1 : newSize;
		Item[] tmp = RQueue.clone();
		RQueue = (Item[]) new Object[size];
		for(int i=0; i<end; i++) {
			RQueue[i] = tmp[i];
		}
	}
    // add the item
    public void enqueue(Item item) {
		if(item==null) {throw new IllegalArgumentException();}
    	if(this.end==this.size) {resize(2*size);}
    	RQueue[end]= item;
    	end++;
    }



    // remove and return a random item
    public Item dequeue() {
    	if(this.isEmpty()) {throw new java.util.NoSuchElementException();}
    	int x =StdRandom.uniform(0, end);
    	Item k = RQueue[x];
    	for(int i=x; i<end-1;++i) {
    		RQueue[i]=RQueue[i+1];
    	}
		end--;
    	if(end == size/4) {resize(size/2);}
    	return k;
    }

    // return a random item (but do not remove it)
    public Item sample() {
    	if(this.isEmpty()) {throw new java.util.NoSuchElementException();}
    	return RQueue[StdRandom.uniform(0, end)];
	}
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {return new RandomQueueIterator();}

    private class RandomQueueIterator implements Iterator<Item>{
    	private RandomizedQueue<Item> A= new RandomizedQueue<Item>();

		private RandomQueueIterator() {
			for(int i=0; i<end; i++) {
				A.enqueue(RQueue[i]);
		 	}
		}
    	public boolean hasNext() {return !A.isEmpty();}
    	public Item next() {
    		if(!this.hasNext()) {throw new java.util.NoSuchElementException();}
    		return A.dequeue();
    	}
    	public void remove() {throw new UnsupportedOperationException();}
	}
    // unit testing (required)
    public static void main(String[] args) {
    	RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		rq.isEmpty();
		rq.size();
		rq.size();
		rq.size() ;
		rq.enqueue(27);
		rq.dequeue();
		rq.isEmpty();
		rq.enqueue(1);


    }

}
