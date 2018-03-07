package com.esrawa.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.esrawa.dao.ParallelDataDao;
import com.esrawa.domain.ParallelData;
import com.esrawa.domain.ErrorRule;
import com.esrawa.domain.ErrorType;
import com.esrawa.domain.NgramIterator;
import com.esrawa.domain.NgramWordIterator;

import java.io.IOException;
import java.util.ArrayList;

class ErrorRuleGeneratorService implements ErrorRuleGeneratorInterface{
	
	private final ParallelDataDao parallelDataDao;
	private final String JOINT_CORPUS_PATH = "src\\main\\resources\\jointSentences.txt";
	private final String JOINT_TAG_PATH = "src\\main\\resources\\jointTags.txt";
	private final int gram; // n-gram
	
	public ErrorRuleGeneratorService(ParallelDataDao parallelDataDao, int gram) {
		this.parallelDataDao = parallelDataDao;
		this.gram = gram;
	}
	
	@Override
	public Collection<ParallelData> getAllParallelData() throws IOException{
		return parallelDataDao.getAllParallelData(JOINT_CORPUS_PATH, JOINT_TAG_PATH);
	}

	@Override
	public ErrorRule generateRules() throws IOException {
		
		ArrayList<ParallelData> parallelData = new ArrayList(getAllParallelData());
		
		return new ErrorRule();
	}
	

				
}
