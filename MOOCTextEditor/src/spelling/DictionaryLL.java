package spelling;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A class that implements the Dictionary interface using a LinkedList
 *
 */
public class DictionaryLL implements Dictionary 
{

	private LinkedList<String> dict;
	
    // TODO: Add a constructor
	public DictionaryLL(){
		dict = new LinkedList<String>();
	}

    /** Add this word to the dictionary.  Convert it to lowercase first
     * for the assignment requirements.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
    public boolean addWord(String word) {
    	// TODO: Implement this method
    	word=word.toLowerCase();
    	if(isWord(word)){
    		return false;
    	} else {
    		dict.add(word);
    		return true;
    	}
    }


    /** Return the number of words in the dictionary */
    public int size()
    {
        // TODO: Implement this method
        return dict.size();
    }

    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
        //TODO: Implement this method
    	s=s.toLowerCase();
    	Iterator iter = dict.iterator();
    	while(iter.hasNext()){
    		if(s.equals(iter.next())){
    			return true;
    		}
    	}
        return false;
    }
    
}
