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
		first=new Node();
		last=new Node();
		first.next=last;
		last.prev=first;
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
    	if(first.item==null) {first.item=item;}
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
    	if(last.item==null) {last.item=item;}
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
    	if(first.next==last) {tmp = first.item; first.item=null;}
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
		if(last.prev==last) {tmp = last.item; last.item=null;}
    	else{
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
    	public boolean hasNext() {
    		return i != null;
		}
    	public Item next() {
    		if(!this.hasNext()) {throw new java.util.NoSuchElementException();}
    		if(size==1) {
				i= (first.item==null) ? last : first;
			}
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
    	deque.addLast(20);
    	//deque.addFirst(5);
    	Iterator<Integer> it = deque.iterator();
    	int count=0;
    	while(it.hasNext()) {
			System.out.println(it.next());
			count++;

		}
    	System.out.println(count+" "+ deque.size());
		deque.addFirst(1);
		System.out.println(deque.removeFirst());
		System.out.println(deque.isEmpty());
		deque.addFirst(4);
		System.out.println(deque.removeFirst());
	}
}
