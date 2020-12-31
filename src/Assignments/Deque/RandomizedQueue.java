import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private DeletableArray<Item> RQueue;
	private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
		size = 0;
    	RQueue = new DeletableArray<Item>(size);
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
    	return RQueue.isEmpty();
    }

    // return the number of items on the randomized queue
    public int size() {
    	return RQueue.size();
    }
	
    // add the item
    public void enqueue(Item item) {
		if(item==null) {throw new IllegalArgumentException();}
    	RQueue.put(item);
    }

    // remove and return a random item
    public Item dequeue() {
    	if(this.isEmpty()) {throw new java.util.NoSuchElementException();}
    	return RQueue.sampleAndSwap();
    }

    // return a random item (but do not remove it)
    public Item sample() {
    	if(this.isEmpty()) {throw new java.util.NoSuchElementException();}
    	return RQueue.get(StdRandom.uniform(0, RQueue.size()));
	}

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {return new RandomQueueIterator();}

    private class RandomQueueIterator implements Iterator<Item> {
		
		//deep copy of RQueue
		DeletableArray<Item> copy;

		private RandomQueueIterator() {
			copy = new DeletableArray<Item>(RQueue);
		}

		public boolean hasNext() {return !copy.isEmpty();}

    	public Item next() {
    		if(!this.hasNext()) {throw new java.util.NoSuchElementException();}
    		return copy.sampleAndSwap();
		}
		
		public void remove() {throw new UnsupportedOperationException();}
		
	}







    // unit testing (required)
    public static void main(String[] args) {
    	RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		rq.enqueue(1);
		rq.enqueue(2);
		rq.enqueue(3);
		rq.enqueue(4);
		rq.enqueue(5);
		rq.enqueue(6);
		rq.enqueue(7);
		rq.enqueue(8);
		rq.dequeue();
		rq.dequeue();
		rq.dequeue();

		for(Integer i : rq)
			StdOut.println(i);
	}








	private static class DeletableArray<Item> {
		private Item arr [];
		private int end;
		private int size;
	
		public DeletableArray(int size) {
			this.arr = (Item[]) new Object[size];
			this.end = size;
		}

		public DeletableArray(DeletableArray<Item> Obj) {
			this(Obj.end);
			for(int i=0; i< Obj.end; i++) 
				this.arr[i] = Obj.arr[i];
			this.end = Obj.end;
		}

		public boolean isEmpty() {
			return (end == 0);
		}

		public int size() {
			return end;
		}

		public Item get(int index) {
			return arr[index];
		}

		public void put(Item val) {
			if(this.end==this.size) {resize(2*size);}
			arr[end++] = val;
		}

		//resize array when end = size or end = size/4.
		//Time Complexity: Theta(end); where end is number of elements in RQueue.
		private void resize(int newSize) {
			size= size == 0 ? 1 : newSize;
			Item[] tmp = arr.clone();
			arr = (Item[]) new Object[size];
			for(int i=0; i<end; i++) {
				arr[i] = tmp[i];
			}
		}

		//Constant time
		//Get random unused element in array and mark it as used.
		//To mark as used, just swap its value with cnt's as elements after cnt can no longer be selected.
		//Avoid actual deletion which takes linear time considering nuber of elements in array. 
		private Item sampleAndSwap() {
			int x = StdRandom.uniform(0, end);
			Item tmp = arr[x];
			swapAndRemove(arr, x, --end);
			if(end == size/4) {resize(size/2);}
			return tmp;
		}

		private void swapAndRemove(Item[] arr, int pos1, int last) {
			arr[pos1] = arr[last];
			arr[last] = null;
		}

	}

}
