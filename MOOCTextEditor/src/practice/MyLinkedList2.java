package practice;

import java.util.AbstractList;
import java.util.LinkedList;
import java.util.ListIterator;

public class MyLinkedList2<E> {
	LLNode<E> head;
	LLNode<E> tail;
	
	public static void main(String args[]){
		MyLinkedList2<Integer> llist = new MyLinkedList2<Integer>();
		llist.addNode(1);
		llist.addNode(199);
		llist.addNode(10);
		llist.addNode(1);
		llist.addNode(10);
		llist.addNode(10);
		llist.addNode(19);
		llist.removeDuplicates(llist.head);
		llist.print(llist.head);
		llist.addNode(19);
		llist.removeDuplicates(llist.head);
		llist.print(llist.head);
		System.out.println("tail");
		System.out.println(llist.tail.data);
	}
	//Add to the end of the singly linked list
	private void addNode(E val){
		LLNode<E> node = new LLNode(val);
		
		if(this.head ==null){
			this.head = node;
		} else {
			this.tail.next = node;
		}
		this.tail = node;
	}
	
	private void removeDuplicates(LLNode<E> head){
		//two pointers: current and runner
		//for each current value, runner will scan the rest of the list and remove all 
		//repeated elements
		LLNode<E> current = head;
		
		while(current !=null ){
			LLNode<E> runner = current;
			while(runner.next !=null ){
				if(runner.next.data == current.data){
					//remove that element by removing a reference to it
					runner.next = runner.next.next;
				} else {
					runner = runner.next;
				}
			}
			current = current.next;
			
			setTail(head);
		}
	}
	
	private void setTail(LLNode<E> head){
		LLNode<E> prev = head;
		
		while(true){
			if(prev.next == null){
				this.tail = prev;
				break;
			}
			prev = prev.next;
		}
	}
	
	private void removeDuplicates3(LLNode<E> head){
		//two pointers: current and runner
		//for each current value, runner will scan the rest of the list and remove all 
		//repeated elements
		LLNode<E> current = head;
		while(current != null){
			
			//runner
			LLNode<E> prev = current;
			LLNode<E> runner= current.next;
			
			while(runner != null ){
				if(runner.data == current.data){
					prev.next = runner.next;
				} else {
					prev = runner;
				}
				runner = runner.next;
			}
			current=current.next;
			
			if(current.next ==null){
				this.tail = current;
			}
		}
	}
	
	private void removeDuplicates2(LLNode<E> head){
		LLNode<E> node = head.next;
		LLNode<E> prevNode = head;
		//assuming we can't use a structure a temporary buffer like hashtable or hashset
		//use two pointers: i and j
		
		int i=1; //because starting to check from the first element
		
		//go through all the of nodes
		while(node != null){
			LLNode<E> pointedToByJ = head;
			boolean foundDup = false;
			for(int j=0; j<i;j++){
				if(pointedToByJ.data == node.data){
					i--;
					foundDup=true;
					prevNode.next = node.next;
					break;
				}
				pointedToByJ= pointedToByJ.next;
			}
			if(!foundDup){
				prevNode = node;
			}
			i++;
			node=node.next;
		}
		setTail(head);
	}
	
	private void print(LLNode<E> head){
		LLNode<E> node = head;
		while(node !=null){
			System.out.println(node.data);
			node = node.next;
		}
	}
	
	class LLNode<E> {
		E data;
		LLNode next;
		
		public LLNode(E data){
			this.data = data;
		}
	}
	
}
