import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.lang.reflect.Constructor;
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
    	int x = StdRandom.uniform(0, end);
    	Item k = RQueue[x];
    	for(int i=x; i<end-1;++i) {
    		RQueue[i]=RQueue[i+1];
    	}
		RQueue[--end] = null;
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
		
		//deep copy of RQueue
		private Item[] copy;
		int cnt;

		private RandomQueueIterator() {
			copy = RQueue.clone();
			cnt = 0;
		}

		public boolean hasNext() {return end - cnt != 0;}

		//get random unused element in array and mark it as used.
		//To mark as used, just swap its value with cnt's as elements before cnt + 1 can no longer be selected.
		//Avoid deletion which takes linear time considering nuber of elements in array. 
    	public Item next() {
    		if(!this.hasNext()) {throw new java.util.NoSuchElementException();}
    		return sampleAndSwap();
		}
		
		public void remove() {throw new UnsupportedOperationException();}
		
		//constant time
		private Item sampleAndSwap() {
			int x = StdRandom.uniform(cnt, end);
			Item tmp = copy[x];
			swap(x, cnt++);
			return tmp;
		}

		private void swap(int pos1, int pos2) {
			Item tmp = copy[pos1];
			copy[pos1] = copy[pos2];
			copy[pos2] = tmp;
		}
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
		StdOut.println(rq.size);
		for(Integer i : rq) StdOut.println(i);
		// Iterator<Integer> it = rq.iterator();
		// while(it.hasNext()) {
		// 	StdOut.print(it.next());
		// }
	}
	
	//resize array when end == size or end == size/4.
	//Theta(end); where end is number of eements in RQueue.
	private void resize(int newSize) {
		size= size == 0 ? 1 : newSize;
		Item[] tmp = RQueue.clone();
		RQueue = (Item[]) new Object[size];
		for(int i=0; i<end; i++) {
			RQueue[i] = tmp[i];
		}
	}


}
