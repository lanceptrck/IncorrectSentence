package com.esrawa.service;

import java.util.Collection;

import com.esrawa.dao.ParallelDataDao;
import com.esrawa.domain.ParallelData;
import com.esrawa.domain.ErrorRule;
import com.esrawa.domain.ErrorType;

import java.io.IOException;
import java.util.ArrayList;

public class ErrorRuleGeneratorService {
	
	private final ParallelDataDao parallelDataDao;
	private final String JOINT_CORPUS_PATH = "src\\main\\resources\\jointSentences.txt";
	private final String JOINT_TAG_PATH = "src\\main\\resources\\jointTags.txt";
	
	public ErrorRuleGeneratorService(ParallelDataDao parallelDataDao) {
		this.parallelDataDao = parallelDataDao;
	}
	
	public Collection<ParallelData> getAllParallelData() throws IOException{
		return parallelDataDao.getAllParallelData(JOINT_CORPUS_PATH, JOINT_TAG_PATH);
	}
	
	public void generateErrorRules() {
		
	}
		
}
