package com.esrawa.dao;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.esrawa.domain.ParallelData;

public class ParallelDataDaoTest {
	
	private final String JOINT_CORPUS_PATH = "src\\main\\resources\\jointSentences.txt";
	private final String JOINT_TAG_PATH = "src\\main\\resources\\jointTags.txt";

	@Test
	public void test() throws IOException {
		ParallelDataDao dao = new ParallelDataDao();
		for(ParallelData pd : dao.getAllParallelData(JOINT_CORPUS_PATH, JOINT_TAG_PATH)) {
			System.out.println(pd.toString());
		}
		
	}

}
