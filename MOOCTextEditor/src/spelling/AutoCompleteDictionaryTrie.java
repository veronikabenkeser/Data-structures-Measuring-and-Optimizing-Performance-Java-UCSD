package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author Veronika Benkeser
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size=0;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		
		if (word== null || word.isEmpty() || word.trim().isEmpty()) return false; 
		 
		word = word.toLowerCase();
			TrieNode node=root;
			int wordL = word.length();
			for(int i=0; i<wordL;i++){
				TrieNode proposedNode = node.insert(word.charAt(i));
				
					
				if(i==wordL-1 && proposedNode !=null){
					proposedNode.setEndsWord(true);
					this.size++;
					return true;
				} 
				
				if (proposedNode ==null){ //the node already exists
					proposedNode = node.getChild(word.charAt(i));
					
					if(i==wordL-1 && !proposedNode.endsWord()){
						this.size++;
						proposedNode.setEndsWord(true);
					}
				}
				node = proposedNode;
			}
			return false;
	}
	
	private int countChildren(TrieNode node){
		
		if(node==null){
			return this.size;
		}
		
		Set<Character> letters= node.getValidNextCharacters();
		
		for(Character letter: letters){
			TrieNode currNode = node.getChild(letter);
			if(currNode.endsWord()){
				this.size++;
			}
			//do the same for its child
			countChildren(currNode);
		}
		return this.size;
	}
	
	
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
		 return this.size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		if (s.length()<1 || s == "\\s+")return false;
		s = s.toLowerCase();
		TrieNode node=root;
		for(int i=0; i<s.length();i++){
			node=node.getChild(s.charAt(i));
			if(node == null)return false;
		}
		if(node.endsWord()){
			return true;
		}
		return false;
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	// TODO: Implement this method
    	 
    	 List<String> result = new ArrayList<String>();
    	 TrieNode stem = findStem(prefix);
    	 if (stem == null) return result;
    	 
    	 Queue<TrieNode> q = new LinkedList<TrieNode>();
    	 int i=0;
    	 q.add(stem);
    	 while(!q.isEmpty()&&i<numCompletions){
    		 //remove the top item in the queue 
    		TrieNode node= q.remove();
    		if(node.endsWord()){
    			result.add(node.getText());
    			 i++;
    		}
    		//add of child nodes to the back of the queue
    		Set<Character> letters = node.getValidNextCharacters();
    		for(Character letter: letters){
    			q.add(node.getChild(letter));
    		}
    	 }
    	 return result;
     }

     private TrieNode findStem(String prefix){
    	 if(prefix.equals("")){
    		 return root;
    	 }
    	 TrieNode node = root;
    	 int prefL = prefix.length();
    	 for(int i=0; i<prefL;i++){
    		 node = node.getChild(prefix.charAt(i));
    		 
    		 //this stem does not exist in the tree
    		 if(node ==null) return null;
    	 }
    	 return node;	
     }
 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}	
}