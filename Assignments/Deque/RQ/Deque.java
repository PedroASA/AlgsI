import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private Node first;
	private Node last;
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
    	if(item ==null) {throw new IllegalArgumentException();}
    	if(size==0) {
    		first=new Node();
    		first.item=item;
    		last=first;}
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
    	if(size==1) {tmp=first.item; first=null;}
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
		if(size==1) {tmp=last.item; last=null;}
		tmp = last.item;
		last = last.prev;
		last.next = null;
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
    	Deque deque = new Deque<Integer>();
    	//deque.addFirst(10);
    	//deque.addLast(20);
    	deque.addFirst(5);
    	/*int count=0;
		deque.addLast(1);
		deque.addLast(2);
		*/System.out.println(deque.removeFirst());/*
		Iterator<Integer> it = deque.iterator();
    	while(it.hasNext()) {
			System.out.println(it.next());
			count++;
		}
    	System.out.println(count+" "+ deque.size());
		/*deque.addFirst(1);
		System.out.println(deque.removeFirst());
		System.out.println(deque.isEmpty());
		deque.addFirst(4);
		System.out.println(deque.removeFirst());*/
	}
}
