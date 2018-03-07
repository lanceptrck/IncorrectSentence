package com.esrawa.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class SpellingErrorRule extends ErrorRule{
	
	private final String correctSequence; // correct character n-gram sequence
	private final String incorrectSequence; // incorrect character n-gram sequence
	private final String sourceOfIncorrectness; // word that is the source of incorrectness
	private final ErrorType errorType; // error type

	private SpellingErrorRule(String correctSequence, String incorrectSequence, String sourceOfIncorrectness,
			ErrorType errorType) {
		super(correctSequence+"|"+incorrectSequence+"|"+sourceOfIncorrectness+"|"+errorType);
		this.correctSequence = correctSequence;
		this.incorrectSequence = incorrectSequence;
		this.sourceOfIncorrectness = sourceOfIncorrectness;
		this.errorType = errorType;

	}
	
	public SpellingErrorRule of(String correctSequence, String incorrectSequence, String sourceOfIncorrectness,
			ErrorType errorType) {	
		
		return new SpellingErrorRule(correctSequence, incorrectSequence, sourceOfIncorrectness, errorType);
	}
	
	@Override
	public String toString() {
		return correctSequence+"|"+incorrectSequence+"|"+sourceOfIncorrectness+"|"+errorType;
	}
	
	
	
	
	
}
