package com.esrawa.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NgramIterator implements Iterator<String> {

    String[] words;
    int pos = 0, n;

    public NgramIterator(int n, String str) {
        this.n = n;
        words = str.split(" ");
    }

    public boolean hasNext() {
        return pos < words.length - n + 1;
    }

    public String next() {
        StringBuilder sb = new StringBuilder();
        for (int i = pos; i < pos + n; i++)
            sb.append((i > pos ? " " : "") + words[i]);
        pos++;
        return sb.toString();
    }
    
	public Set<String> distinctGramsOfThisAndOf(NgramIterator other){
		Set<String> distinctGrams = new HashSet<>();
		
		for (String gram : this.gramListOf())
			if (!other.gramListOf().contains(gram))
				distinctGrams.add(gram);
		
		return distinctGrams;
	}
	
	private ArrayList<String> gramListOf(){
		ArrayList<String> gramList = new ArrayList<String>();
		
		while(this.hasNext())
			gramList.add(this.next());
		
		return gramList;
	}

    public void remove() {
        throw new UnsupportedOperationException();
    }
}