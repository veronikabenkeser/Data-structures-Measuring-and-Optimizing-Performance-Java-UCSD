/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 * @author Veronika Benkeser
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// Test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// Test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		// TODO: Add more tests here
		
		//Head and tail have been updated
		int b=list1.remove(list1.size()-1);
		LLNode<Integer> node = getNode(0, list1);
		assertEquals("Remove: tail has been updated", list1.tail, node);
		assertEquals("Remove: head has been updated", list1.head, node);
		
		//Try removing an element that does not exist
		try {
			emptyList.remove(0);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		
		//Try removing an element at an invalid index
		try {
			list1.remove(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		
		//Check that prev and next connections get updated
		longerList.remove(2);
		assertEquals("Remove: val at index is updated", longerList.get(2), (Integer)(3));
		assertEquals("Remove: next updated", getNode(2, longerList).next.data, (Integer)(4));
		assertEquals("Remove: prev updated", getNode(2, longerList).prev.data, (Integer)(1));
		assertEquals("Remove: next val of the element following the removed element is updated", getNode(1, longerList).next.data, (Integer)(3));
		assertEquals("Remove: prev val of the preceeding the remove delement is updated", getNode(3, longerList).prev.data, (Integer)(3));
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
		try{
			list1.add(null);
			fail("Trying to add a null element.");
		} catch(NullPointerException e){
		}
		
		//Properly sets head and tail if the list is empty
        emptyList.add(10);
        LLNode<Integer> node = getNode(0, emptyList);
        assertEquals("add(E element): properly sets head", emptyList.head, node);
        assertEquals("add(E element): properly sets tail", emptyList.tail, node);
        assertEquals("add(E element): size is updated", emptyList.size(), 1);
        
        //Properly updates tail, prev, and next if the list is not empty
        shortList.add("C");
        assertEquals("add(E element) to a non-empty list: properly sets tail", shortList.tail.data,  "C");
        assertEquals("add(E element) to a non-empty list: updates prev", getNode(1, shortList).next.data, "C");
        assertEquals("add(E element) to a non-empty list: updates next", getNode(2, shortList).prev.data, "B");
	}
	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		assertEquals("testSize: size of empty list is 0", emptyList.size,0);
		assertEquals("testSize: size of the long list is LONG_LIST_LENGTH", longerList.size,LONG_LIST_LENGTH);
	}
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * If elements exist at that index, the new element is inserted into this list 
	 * and all the other elements are moved up.
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test
		try{
			emptyList.add(-1, 1);
			fail("Check out of bounds");
		} catch(IndexOutOfBoundsException e){
		}
		
		try{
			emptyList.add(2, 1);
			fail("Check out of bounds");
		} catch(IndexOutOfBoundsException e){
		}
		
		try{
			list1.add(0, null);
			fail("Trying to add a null element.");
		} catch(NullPointerException e){
		}
		
		
		emptyList.add(0, 1);
		assertEquals("testAddAtIndex: size of empty list is updated", emptyList.size(), 1);
		assertEquals("testAddAtIndex: head is set correctly", emptyList.head.data, (Integer)(1));
		assertEquals("testAddAtIndex: tail is set correctly", emptyList.tail.data, (Integer)(1));
		assertEquals("testAddAtIndex: first element is set correctly", emptyList.get(0), (Integer)(1));

		shortList.add(0,"AA");
		assertEquals("testAddAtIndex: element inserted at index 0 and head is updated", getNode(0, shortList), shortList.head);
		assertEquals("testAddAtIndex: element inserted at index 0 and data prop of new element is correct", getNode(0, shortList).data, "AA");
		assertEquals("testAddAtIndex: element inserted at index 0 and  next prop of new element is correct", getNode(0, shortList).next.data, "A");
		assertEquals("testAddAtIndex: element inserted at index 0 and  prev prop of new element is correct", getNode(0, shortList).prev, null);
		assertEquals("testAddAtIndex: element inserted at index 0 and  size of short list is updated", shortList.size(), 3);
		
		shortList.add(2,"C");
		assertEquals("testAddAtIndex: element at last index is updated", getNode(2,shortList).data, "C");
		assertEquals("testAddAtIndex: element is inserted at last index and count goes up", shortList.size(), 4);
		assertEquals("testAddAtIndex: element is inserted at last index and tail is updated", shortList.tail.data, "B");
		assertEquals("testAddAtIndex: element is inserted at last index and tail's prev is updated", getNode(3, shortList).prev.data, "C");
		assertEquals("testAddAtIndex: element is inserted at last index and its next prop is updated", getNode(2, shortList).next.data,"B");
		assertEquals("testAddAtIndex: element is inserted at last index and its prev prop is updated", getNode(2, shortList).prev.data,"A");
		assertEquals("testAddAtIndex: next prop of element in front of the inserted element is updated", getNode(1, shortList).next.data,"C");
		longerList.add(LONG_LIST_LENGTH, 100);
		assertEquals("testAddAtIndex: element is added to the end of the list", longerList.get(LONG_LIST_LENGTH),(Integer)(100));
		assertEquals("testAddAtIndex: count goes up", longerList.size(),LONG_LIST_LENGTH+1);
		assertEquals("testAddAtIndex: element is added to the end of the list and tail is updated", longerList.tail.data,(Integer)(100));
		assertEquals("testAddAtIndex: prev prop of the tail is updated", longerList.tail.prev.data,(Integer)(LONG_LIST_LENGTH-1));
		assertEquals("testAddAtIndex: next prop of the element that used to be last is updated", getNode(LONG_LIST_LENGTH-1, longerList).next.data, (Integer)(100));

	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		try{
			emptyList.set(-1, 1);
			fail("Check out of bounds");
		} catch(IndexOutOfBoundsException e){
		}
		
		try{
			emptyList.set(0, 1);
			fail("Check out of bounds");
		} catch(IndexOutOfBoundsException e){
		}
		
		try{
			shortList.set(0,null);
			fail("Trying to set element to null.");
		} catch(NullPointerException e){
		}
		
		String newVal = shortList.set(0,"QQ");
		assertEquals("testSet: returns the old value at that index", newVal, "A");
		assertEquals("testSet: size does not change", shortList.size(), 2);
		
		longerList.set(5, 100);
		assertEquals("testSet: loop iterates correctly", longerList.get(5), (Integer)(100));
		assertEquals("testSet: next prop of new element is set correctly", getNode(5, longerList).next.data, longerList.get(6));
		assertEquals("testSet: prev prop of new element is set correctly", getNode(5,longerList).prev.data, longerList.get(4));
		assertEquals("testSet: prev prop of element that follows the newly set element is correct", getNode(4, longerList).next.data,(Integer)(100));
		assertEquals("testSet: next prop of element that preceeds the newly set element is correct", getNode(6, longerList).prev.data,(Integer)(100));
	}
	
	private <E> LLNode<E> getNode(int index, MyLinkedList<E> llist){
		if (index>llist.size-1 || index<0) throw new IndexOutOfBoundsException();
		
		int i=0;
		LLNode<E> current = llist.head;
		while(i<index){
			current = current.next;
			i++;
		}
		return current;
	}
}
