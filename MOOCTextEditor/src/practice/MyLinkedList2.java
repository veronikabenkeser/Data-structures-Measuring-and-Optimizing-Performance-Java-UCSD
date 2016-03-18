package practice;

import java.util.AbstractList;
import java.util.LinkedList;
import java.util.ListIterator;

public class MyLinkedList2<E> {
	LLNode<E> head;
	LLNode<E> tail;

	private static boolean addOne = false;
	
	public static void main(String args[]){
		MyLinkedList2<Integer> llist = new MyLinkedList2<Integer>();
		llist.addNode(2);
		llist.addNode(199);
		llist.addNode(10);
		llist.addNode(1);
		llist.addNode(10);
		llist.addNode(10);
		llist.addNode(19);
		
		llist.addNode(3);
		llist.addNode(5);
		llist.addNode(8);
		llist.addNode(5);
		llist.addNode(10);
		llist.addNode(2);
		llist.addNode(1);
		
		MyLinkedList2<Integer> llist1 = new MyLinkedList2<Integer>();
		llist1.addNode(9);
		llist1.addNode(9);
		llist1.addNode(9);

		MyLinkedList2<Integer> llist2 = new MyLinkedList2<Integer>();	
		llist2.addNode(9);

		MyLinkedList2<Integer> sumList = new MyLinkedList2<Integer>();
		
		llist.removeDuplicates(llist.head);
		llist.print(llist.head);
		llist.addNode(19);
		llist.removeDuplicates(llist.head);
		llist.print(llist.head);
		System.out.println("tail");
		System.out.println(llist.tail.data);
		llist.findNthNumToLast1(llist.head,2);
		llist.findNthToLast(llist.head,2);
		
		llist.pivotAround(5, llist.head);
		llist.print(llist.head);

		sumList.head = llist.addLinkedLists(llist1.head, llist2.head);
		llist.print(sumList.head);
	}
	
	/*
	 * This method returns the starting node of the sum of two linked lists, where the number is 
	 * listed backwards.
	 * For example, head1.data = 2. head1.next.data = 1. head1.next.next.data = 3 is 312. 
	 * head2.data=5. head2.next.data =8 is 85. The result should be 7 -> 9 -> 3, which is 397. */
	
	private LLNode<Integer> addLinkedLists(LLNode<Integer> head1, LLNode<Integer> head2){
		return recursiveAdd (head1,head2, new LLNode<Integer>(0));
	}
	
private LLNode<Integer> recursiveAdd(LLNode<Integer> head1, LLNode<Integer> head2, LLNode<Integer> result){
		
		if(head1 == null && head2 == null){
			
			return null;
		}
		
		LLNode<Integer> next1;
		LLNode<Integer> next2;
		LLNode<Integer> nextRes;
		
		if(head1==null){
			next1=null;
		} else {
			next1 = head1.next;
		}
		
		if (head2==null){
			next2=null;
		}else {
			next2 = head2.next;
		}
		
		if(next1 == null && next2 == null){
			result.next=null;
		} else {
			result.next = new LLNode<Integer>(0);
		}
				
		LLNode<Integer> node = recursiveAdd(next1, next2, result.next);
	
		
		int sum;
		if(head1==null){
			sum = (int)head2.data;
			
		} else if (head2==null){
			sum=(int)head1.data;
		} else {
			sum  = (int)head1.data+(int)head2.data;
		}
	
		if(sum>=10){
			sum-=10;
			
			if(result.next ==null){
				result.next = new LLNode<Integer>(1);
			} else {
				LLNode<Integer> nextE  = result.next;
				nextE.data = (int)nextE.data+1;
				//propagate changes
				while(nextE !=null && nextE.data>=10){
					nextE.data -=10;
					if(nextE.next ==null){
						nextE.next = new LLNode(1);
					}else{
						nextE.next.data=(int)nextE.next.data+1;
					}
					nextE=nextE.next;
				}
			}
		}
		result.data = sum;
		return result;
	}
	
	private boolean deleteLast(LLNode<E> head){
		if (head == null)return false;
		
		if(head.next ==null){
			this.head=null;
			this.tail=null;
			return true;
		}
		
		if(head.next.next==null){
			this.tail=head;
			return true;
		}
		
		while(head.next.next !=null){
			head = head.next;
		}
		head.next = null;
		this.tail = head;
		System.out.println("updated");
		return true;
	}
	
	private void setNewHead(LLNode<E> head){
		this.head = head;
	}
	
	private void pivotAround(int val, LLNode<E> head){
		LLNode<E> curr = head;
	
		while(curr !=null){
			if(curr != head && (int)curr.data< 5){
				System.out.println("curr is not head and less than 5 :"+curr.data);
				LLNode<E> nextElem = curr;
				LLNode<E> tobeHeadElem = new LLNode<E>(curr.data);
				
				if(curr.next !=null){
					
					nextElem = curr.next;
					curr.data = nextElem.data;
					curr.next = nextElem.next;
				}else{
					//reached the end
					deleteLast(head);
					curr = null;
				}
				tobeHeadElem.next = head;
				head = tobeHeadElem;
				
			} else {
				curr = curr.next;
			}
		}
		setNewHead(head);
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
	
	//wrapper class
	class Index{
		public int value = 0;
	}
	
	private LLNode<E> findNthToLast(LLNode<E> head, int k){
		//2 pointers
		//one points at the first element
		//one points at the kth element
		
		LLNode<E> iPointsTo = head;
		LLNode<E> jPointsTo = head;
		
		//set j to point at the kth element
		for(int i=0; i<k;i++){
			if(jPointsTo == null) throw new IndexOutOfBoundsException();
			jPointsTo = jPointsTo.next;
		}
		
		//increment i and j by the same amount until j reaches the end of the list
		while(jPointsTo != null){
			jPointsTo = jPointsTo.next;
			iPointsTo = iPointsTo.next;
		}
		System.out.println(iPointsTo.data);
		return iPointsTo;
	}
	
	public LLNode<E> findNthNumToLast1(LLNode<E> head, int k){
		return  findNthToLast1(head,k, new Index());
	}
	
	private LLNode<E> findNthToLast1(LLNode<E> head, int k, Index i){
	
		if(head ==null) return null;
		
		LLNode<E> node = findNthToLast1(head.next, k, i);
		
		//executes after the node = tail
		i.value++;
		System.out.println("i "+i.value);
		if(i.value==k){
			System.out.println("returning head "+head.data);
			return head;
		}
		System.out.println(node);
		return node;
		
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
		System.out.println();
		LLNode<E> node = head;
		while(node !=null){
			System.out.print(node.data);
			System.out.print(" -> ");
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
