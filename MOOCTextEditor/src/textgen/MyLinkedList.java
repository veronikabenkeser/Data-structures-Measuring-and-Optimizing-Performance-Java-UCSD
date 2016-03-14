package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 * @author Veronika Benkeser
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		size=0;
		head=null;
		tail=null;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		if (element ==null )throw new NullPointerException();
		LLNode<E> node = createNode(element);
		
		if (head == null){
			this.head =  node;
			this.tail=node;
		} else {
			LLNode<E> prev = this.tail;
			this.tail = node;
			prev.next = node;
			node.prev = prev;
		}
		this.size++;
		return false;
	}

	private LLNode<E> createNode(E val){
		return new LLNode(val);
	}
	
	/** Get the element at position index 
	 * @return 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		return getNode(index).data;
	}
	

	/**
	 * Add an element to the list at the specified index. If  an elements exist at that 
	 *index, you will move elements at that index (and beyond) up, 
	 * effectively inserting this element at that location in the list. 
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		if(index<0 || index>this.size)throw new IndexOutOfBoundsException();
		if (element ==null )throw new NullPointerException();
		
		LLNode<E> node = createNode(element);
		
		if((index==0 && this.size==0) || index == this.size){
			//add to the end of the list
			add(element);
			return;
		}
		
		LLNode<E> oldElem = this.getNode(index);

		//Inserting instead of the first element
		if(index ==0){
			this.head = node;
		}
		
		if(oldElem.prev != null){
			LLNode<E> prevN = oldElem.prev;
			node.prev = prevN;
			prevN.next = node;
		}
		
		node.next = oldElem;
		oldElem.prev = node;
		
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		if(index<0 || index>this.size-1)throw new IndexOutOfBoundsException();
		
		E removedElement = this.get(index);
		
		if(this.size == 1){
			this.head = null;
			this.tail= null;
		} else if(this.size-1 == index){	
			LLNode<E> tobeLast = getNode(index-1);
			tobeLast.next=null;
			this.tail=tobeLast;	
		}else if (index ==0){
			LLNode<E> tobeFirst = getNode(index+1);
			tobeFirst.prev=null;
			this.head = tobeFirst;
		}else{
			//remove from middle
			LLNode<E> oldPrev = this.getNode(index).prev;
			LLNode<E> oldNext = this.getNode(index).next;
			oldPrev.next = oldNext;
			oldNext.prev = oldPrev;
		}
		size--;
		return removedElement;
	}

	private LLNode<E> getNode(int index){
		if (index>this.size-1 || index<0) throw new IndexOutOfBoundsException();
		
		int i=0;
		LLNode<E> current = this.head;
		while(i<index){
			current = current.next;
			i++;
		}
		return current;
	}
	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		
		if(index<0 || index>this.size-1)throw new IndexOutOfBoundsException();
		
		if (element ==null )throw new NullPointerException();
				
		LLNode<E> current = this.head;
		LLNode<E> previousInList=null;
		LLNode<E> nextInList=current.next;
		
		int i=0;
		
		while(i<index){
			previousInList=current;
			current = current.next;
			nextInList = current.next;
			i++;
		}
		E oldVal = current.data;
		current.data = element;
		current.prev = previousInList;
		current.next = nextInList;
		
		if(previousInList !=null){
			previousInList.next = current;
		}
		if(nextInList !=null){
			nextInList.prev=current;
		}	
		if(index ==0){
			this.head = current;
		} 
		if( index == this.size-1){
			this.tail = current;
		}
		return oldVal;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
