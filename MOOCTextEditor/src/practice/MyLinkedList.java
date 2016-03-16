package practice;

import java.util.*;

/*
 * A class that implements removal of duplicates from a singly linked list.
 */

public class MyLinkedList {
	LinkedListNode head;
	LinkedListNode tail;
	
	public static void main(String args[]){
		MyLinkedList llist = new MyLinkedList();
		llist.addNode(1);
	    llist.addNode(1);
	    llist.addNode(1);
	    llist.addNode(2);
	    llist.addNode(3);
	    llist.addNode(2);
	    llist.addNode(1);
	
	    llist.removeDuplicates(llist.head);
	    llist.print();
	}
	
	//Adding to the end of the list
	public void addNode(int data){
		LinkedListNode node = new LinkedListNode(data);
		if(this.head == null){
			this.head = node;
			this.tail = node;
		} else { 
			LinkedListNode oldTail = this.tail;
			oldTail.next = node;
			this.tail = node;
		}
	}
	
	//Remove duplicates
	public void removeDuplicates(LinkedListNode head){
		HashSet hs = new HashSet();
		LinkedListNode node = head;
		LinkedListNode prevElem = null;
		while(node != null){
			if(hs.contains(node.data)){
				prevElem.next=node.next;
				if(node.next == null){
					this.tail = prevElem;
					break;
				}
			} else {
				hs.add(node.data);
				prevElem=node;
			}
			node = node.next;
		}
	}
	
	public void print(){
		LinkedListNode node = this.head;
		while(node !=null){
			System.out.println(node.data+ " ");
			node = node.next;
		}
	}
	
	
	private class LinkedListNode{
		LinkedListNode next;
		int data;
		
		public LinkedListNode(int data){
			this.data = data;
			this.next = null;
		}
	}
}
