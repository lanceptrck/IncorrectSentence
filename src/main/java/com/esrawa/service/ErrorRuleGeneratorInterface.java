package com.esrawa.service;

import java.io.IOException;
import java.util.Collection;

import com.esrawa.domain.ErrorRule;
import com.esrawa.domain.ParallelData;

public interface ErrorRuleGeneratorInterface {
	
	public ErrorRule generateRules() throws Exception;
	
	public Collection<ParallelData> getAllParallelData() throws IOException;

}
