package com.esrawa.domain;

import java.io.IOException;

public class ParallelData {
	
	private String correctSentence;
	private String incorrectSentence;
	private String correctTags;
	private String incorrectTags;
	private ErrorType errorType;
	private int errorDifference;
	
	private ParallelData(String correctSentence, String incorrectSentence, String correctTags, String incorrectTags) {
		this.correctSentence = correctSentence;
		this.incorrectSentence = incorrectSentence;
		this.correctTags = correctTags;
		this.incorrectTags = incorrectTags;
	}
	
	private ParallelData(String correctSentence, String incorrectSentence, String correctTags, String incorrectTags,
			ErrorType errorType, int errorDifference) {
		super();
		this.correctSentence = correctSentence;
		this.incorrectSentence = incorrectSentence;
		this.correctTags = correctTags;
		this.incorrectTags = incorrectTags;
		this.errorType = errorType;
		this.errorDifference = errorDifference;
	}

	public ParallelData() {
		
	}
	
	public ParallelData of(String correctSentence, String incorrectSentence, String correctTags, String incorrectTags) {
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
	
	public ParallelData getParallelDataWithErrorInfo() throws IOException {
		return new ParallelData(this.correctSentence, this.incorrectSentence, 
				this.correctTags, this.incorrectTags, this.identifyErrorType(), this.identifyErrorDifference());
	}
	
	public ParallelData preprocessParallelData() {
		
		String correctSentence = "<empty> <start> " + this.correctSentence + " <end> <empty>";
		String correctTags = "<empty> <start> " + this.correctTags + " <end> <empty>";
		String incorrectSentence = "<empty> <start> " +this.incorrectSentence + " <end> <empty>";
		String incorrectTags = "<empty> <start> " + this.incorrectTags + " <end> <empty>";
		
		return new ParallelData(correctSentence, incorrectSentence, correctTags, incorrectTags);
		
	}
	
	public static int minDistance(String word1, String word2) {
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
		return correctSentence+"|"+errorType;
	}
	
	
	
	
	
}
