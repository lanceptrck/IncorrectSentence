package com.esrawa.domain;

public class ErrorRule {
	
	private String ruleRepresentation;
	
	public ErrorRule(String ruleRepresentation) {
		this.ruleRepresentation = ruleRepresentation;
	}
	
	@Override
	public String toString() {
		return ruleRepresentation;
	}

}
