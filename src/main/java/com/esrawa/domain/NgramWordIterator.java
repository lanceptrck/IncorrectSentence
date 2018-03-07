package com.esrawa.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NgramWordIterator implements Iterator<String> {

    String[] words;
    String sentence;
    int pos = 0, n;

    public NgramWordIterator(int n, String str) {
        this.n = n;
        this.sentence = str;
       // words = str.split(" ");
    }
    
    public boolean hasNext() {
        return pos < sentence.length() - n + 1;
    }

    public String next() {
        StringBuilder sb = new StringBuilder();
        for (int i = pos; i < pos + n; i++)
        	sb.append(sentence.charAt(i));
        pos++;
        return sb.toString();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
    
	public Set<String> distinctWordGramsOfThisAndOf(NgramWordIterator other){
		Set<String> distinctWordGrams = new HashSet<>();
		
		for (String gram : this.wordGramListOf())
			if (!other.wordGramListOf().contains(gram))
				distinctWordGrams.add(gram);
		
		return distinctWordGrams;
	}
	
	private ArrayList<String> wordGramListOf(){
		ArrayList<String> wordGramList = new ArrayList<String>();
		
		while(this.hasNext())
			wordGramList.add(this.next());
		
		return wordGramList;
	}
}
