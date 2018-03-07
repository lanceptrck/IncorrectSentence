package com.esrawa.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ParallelData {
	
	protected String correctSentence;
	protected String incorrectSentence;
	private String correctTags;
	private String incorrectTags;
	private ErrorType errorType;
	private int errorDifference;
	private String correctGramSequence;
	private String incorrectGramSequence;

	private ParallelData(String correctSentence, String incorrectSentence, String correctTags, String incorrectTags) throws IOException {
		this.correctSentence = correctSentence;
		this.incorrectSentence = incorrectSentence;
		this.correctTags = correctTags;
		this.incorrectTags = incorrectTags;
		this.errorType = this.identifyErrorType();
		this.errorDifference = this.identifyErrorDifference();
		this.correctGramSequence = "";
		this.incorrectGramSequence = "";
	}

	public ParallelData(ParallelData p) throws IOException {
		this.correctSentence = p.correctSentence;
		this.incorrectSentence = p.incorrectSentence;
		this.correctTags = p.correctTags;
		this.incorrectTags = p.incorrectTags;
		this.errorType = p.identifyErrorType();
		this.errorDifference = p.identifyErrorDifference();
		this.correctGramSequence = "";
	}
	
	public ParallelData() {
		
	}
	
	public ParallelData of(String correctSentence, String incorrectSentence, String correctTags, String incorrectTags) throws IOException {
		return new ParallelData(correctSentence, incorrectSentence, correctTags, incorrectTags);
	}
		
	public int identifyErrorDifference() throws IOException {
		
		String temp[] = this.correctSentence.split(" ");
		String temp2[] = this.incorrectSentence.split(" ");

		if (temp.length < temp2.length)
			return 1;
		else if (temp.length > temp2.length) {
			return -1;

		} else if (temp.length == temp2.length)
			return 0;
	
	throw new RuntimeException("Did not identify any error difference! Check integrity of your data!");
	
	}
	
	public ErrorType identifyErrorType() throws IOException {
		
		String correctSentence = this.correctSentence;
		String incorrectSentence = this.incorrectSentence;
		int errorDifference = identifyErrorDifference();
				
		if (errorDifference == 1) {
			if (correctSentence.length() + 1 == incorrectSentence.length())
				return ErrorType.UNMERGING;
			if (minDistance(correctSentence, incorrectSentence) >= 4 
					&& minDistance(correctSentence, incorrectSentence) <= 5) {
				return ErrorType.INSERTION;
			} 
		}
		else {
				if (errorDifference == -1) {
					if (correctSentence.length() - 1 == incorrectSentence.length())
							return ErrorType.MERGING;
					else
						return ErrorType.DELETION;
				} else if (errorDifference == 0) {
					if (minDistance(correctSentence, incorrectSentence) >= 2)
						return ErrorType.SUBSTITUTION;
					else if(correctSentence.equals(incorrectSentence))
						return ErrorType.NOERROR;
					else
						return ErrorType.SPELLING;
				}
			}

		return ErrorType.NOERROR;
	}
	
	public ParallelData preprocessParallelData() throws IOException {
		
		String correctSentence = "<empty> <start> " + this.correctSentence + " <end> <empty>";
		String correctTags = "<empty> <start> " + this.correctTags + " <end> <empty>";
		String incorrectSentence = "<empty> <start> " +this.incorrectSentence + " <end> <empty>";
		String incorrectTags = "<empty> <start> " + this.incorrectTags + " <end> <empty>";
		
		return new ParallelData(correctSentence, incorrectSentence, correctTags, incorrectTags);
		
	}
	
	private Set<String> getDistinctNgramOf(int gram){
		
		NgramIterator current = getTokenNgram(gram, correctSentence);
		NgramIterator other = getTokenNgram(gram, incorrectSentence);
		
		return current.distinctGramsOfThisAndOf(other);
		
	}
	
	private Set<String> getDistinctWordNgramOf(int gram){
		
		NgramWordIterator current = new NgramWordIterator(gram, this.correctGramSequence);
		NgramWordIterator other = new NgramWordIterator(gram, this.incorrectGramSequence);
		
		return current.distinctWordGramsOfThisAndOf(other);
		
	}
	
	public String getFrequentlyOccuringWord(Set<String> nGramSet) {

		Map<String, Integer> frequentWordMap = converNgramSetToMap(nGramSet);
		Entry<String, Integer> maxEntry = getMaxEntry(frequentWordMap);

		if (maxEntry != null) {
			return new String(maxEntry.getKey());
		} else
			throw new NullPointerException("Max Entry cannot be null!");

	}
	
	private Map<String, Integer> converNgramSetToMap(Set<String> nGramSet) {
		
		ArrayList<String> frequentWordList = new ArrayList<String>();
		Set<String> frequentWordSet = new HashSet<String>();
		Map<String, Integer> frequentWordMap = new HashMap<String, Integer>();

		for (String nGram : nGramSet) {
			String nGramArr[] = nGram.split(" ");
			for (int i = 0; i < nGramArr.length; i++) {
				if (!nGramArr[i].equals("/")) {
					frequentWordList.add(nGramArr[i]);
					frequentWordSet.add(nGramArr[i]);
				}

			}
		}

		for (String word : frequentWordSet) {
			frequentWordMap.put(word, 0);
		}

		for (String word : frequentWordList) {
			frequentWordMap.replace(word, frequentWordMap.get(word) + 1);
		}
		
		return frequentWordMap;
		
	}
	
	private Entry<String, Integer> getMaxEntry(Map<String, Integer> frequentWordMap){
		
		Entry<String, Integer> maxEntry = null;
		for (Entry<String, Integer> entry : frequentWordMap.entrySet()) {
			if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
				maxEntry = entry;

			}

		}
		
		if(maxEntry == null) {
			throw new NullPointerException("Max entry cannot be null, Check data integrity!");
		} else 
			return maxEntry;
		
	}

	
	private NgramIterator getTokenNgram(int gram, String sentence) {

		return new NgramIterator(gram, sentence);

	}

	private static int minDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();

		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}

		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}

		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);

				// if last two chars equal
				if (c1 == c2) {
					// update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}

		return dp[len1][len2];
	}
	
	@Override
	public String toString() {
		return "["+correctSentence+"/"+incorrectSentence
				+"\n|"+errorType+"]";
	}
	
	
	
	
	
}
