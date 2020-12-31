/*
Failed Test 6b of Memory
Failed Test 7a of Memory
Failed Test 8 of Memory
Failed Test 4a-k of Memory
Failed Test 5a-k of Memory
Failed Test 6a-k of Memory
*/
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	
	private Node first, last;
	private int size;

	private class Node {
		Item item;
		Node next;
		Node prev;
	}
    // construct an empty deque
    public Deque() {
		size=0;
	}
    // is the deque empty?
    public boolean isEmpty() {
		return (size==0);
	}
    // return the number of items on the deque
    public int size() {
		return size;
	}
    // add the item to the front
    public void addFirst(Item item) {
    	if(item == null) {throw new IllegalArgumentException();}
    	if(size == 0) {
    		first = new Node();
    		first.item = item;
			last = first;
		}
    	else {
    		Node tmp = first;
    		first= new Node();
    		first.item =item;
    		tmp.prev=first;
			first.next = tmp;
		}
		size++;
	}
    // add the item to the back
    public void addLast(Item item) {
    	if(item ==null) {throw new IllegalArgumentException();}
		if(size==0) {
			last=new Node();
			last.item=item;
			first=last;
		}
    	else {
    		Node tmp = last;
			last= new Node();
			last.item =item;
			tmp.next=last;
			last.prev = tmp;
		}
		size++;
	}
    // remove and return the item from the front
    public Item removeFirst() {
    	if(this.isEmpty()) {throw new java.util.NoSuchElementException();}
		Item tmp;
    	if(size==1) {
			tmp=first.item; 
			first=null;
		}
    	else {
			tmp = first.item;
			first = first.next;
			first.prev = null;
		}
    	size--;
    	return tmp;
	}
    // remove and return the item from the back
    public Item removeLast() {
		if(this.isEmpty()) {throw new java.util.NoSuchElementException();}
    	Item tmp;
		if(size==1) {
			tmp = last.item; 
			last = null;
			first = null;
		}
		else {
			tmp = last.item;
			last = last.prev;
			last.next = null;
		}
		size--;
    	return tmp;
	}
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {return new DequeIterator();}
    
    private class DequeIterator implements Iterator<Item>{
    	private Node i = first;
    	public boolean hasNext(){return i != null;
		}
    	public Item next() {
    		if(!this.hasNext()) {throw new java.util.NoSuchElementException();}
    		Item tmp = i.item;
    		i=i.next;
    		return tmp;
    	}
    	public void remove() {throw new UnsupportedOperationException();}
    	
	}
    // unit testing (required)
    public static void main(String[] args) {
    	Deque<Integer> deque = new Deque<Integer>();
		deque.addFirst(1);
		deque.removeLast();
		deque.addFirst(1);
		deque.addFirst(2);
		deque.removeFirst();
	}
}
