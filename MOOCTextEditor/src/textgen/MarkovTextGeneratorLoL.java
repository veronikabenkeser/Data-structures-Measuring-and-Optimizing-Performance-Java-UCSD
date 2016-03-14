package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 * @author Veronika Benkeser
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	//First word in the training set
	private String firstWord;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	private String getFirstWord (){
		return firstWord;
	}
	private void setFirstWord(String word){
		firstWord = word;
	}
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{	
		//Per assignment specifications, this method does not remove the original word lists.
		//clearOldTrainingSet();
		if(sourceText == null|| sourceText =="")return;
		
		sourceText=sourceText.trim();
		
		//If the given string was only comprised of spaces, exit the method
		if(sourceText.isEmpty()){
			return;
		}
		
		String[] textArr= sourceText.split("\\s+");
		int starterWsLength = textArr.length;
		
		//Add the first word to an empty list
		String starterW = textArr[0];
		setFirstWord(starterW);
		
		ListNode newWord = new ListNode(starterW);
		
		//If there was only 1 word entered as a parameter, add that word to the word list
		//and exit the method.
		if(starterWsLength == 1){
			wordList.add(newWord);
			return;
		}
		
		String nextW = textArr[1];
		newWord.addNextWord(nextW);
		wordList.add(newWord);
		
		for(int i=1; i<starterWsLength-1; i++){
			starterW = nextW;
			nextW = textArr[i+1];
			boolean isNotInTheList = true;
			
			for(ListNode node: wordList){
				if(node.getWord().equals(starterW)){
					node.addNextWord(nextW);
					isNotInTheList = false;
				}
			}
			
			if(isNotInTheList){
				newWord = new ListNode(starterW);
				newWord.addNextWord(nextW);
				wordList.add(newWord);
			}
		}
		
		//For the last word in the text, make the starterW its next word
		newWord = new ListNode(textArr[starterWsLength-1]);
		newWord.addNextWord(getFirstWord());
		wordList.add(newWord);

	}
	
	private void clearOldTrainingSet(){
		wordList = new LinkedList<ListNode>();
	}
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		
		if (numWords< 0) throw new IndexOutOfBoundsException();
		if (numWords ==0) return "";
		
		//If the model has not been trained on any data, return an empty string
		if(wordList.size() == 0){
			return "";
		}
		
		//If the model has only been trained on 1 word, return that word
		boolean oneLetterTrainingSet = wordList.size()==1 && wordList.get(0).getAllNextWords().size()==0;
		if(oneLetterTrainingSet){
			return wordList.get(0).getWord();
		}
		
	    StringBuilder sb = new StringBuilder();

	    String currentWord = getFirstWord();
	    sb.append(currentWord);
	    int i=1;
	    while(i<numWords){
	    	//Find the currentWord node
	    	for(ListNode node: wordList){
	    		if(node.getWord().equals(currentWord)){
	    			currentWord = node.getRandomNextWord(rnGenerator);
	    			sb.append(" "+currentWord);
	    			break;
	    		}
	    	}
	    	i++;
	    }
	    return sb.toString();
	}
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		clearOldTrainingSet();
		train(sourceText);
	}
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		
		gen.train("");
		System.out.println(gen.generateText(20));
		
		gen.train("           a           ");
		System.out.println(gen.generateText(20));
		
		gen.train("                   ");
		System.out.println(gen.generateText(20));
		
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		
		
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public List<String> getAllNextWords(){
		return nextWords;
	}
	
	public String getRandomNextWord(Random generator)
	{
		int val=0;
		int bound = this.getAllNextWords().size();
		
		if(bound >1 ){
			val = generator.nextInt(bound);
		}
		return this.getAllNextWords().get(val);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
}


