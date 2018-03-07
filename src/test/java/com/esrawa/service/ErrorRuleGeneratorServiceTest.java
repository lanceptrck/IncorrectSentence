package com.esrawa.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import com.esrawa.dao.ParallelDataDao;

public class ErrorRuleGeneratorServiceTest {
	
	private final String JOINT_CORPUS_PATH = "src\\main\\resources\\jointSentences.txt";
	private final String JOINT_TAG_PATH = "src\\main\\resources\\jointTags.txt";
	private final ParallelDataDao dao = new ParallelDataDao();
	private final ErrorRuleGeneratorService service = new ErrorRuleGeneratorService(dao, 3);


	@Test
	public void generalize_test() throws IOException
	{
		String correctSentence = "Ako ay isang haliparot .";
		String incorrectSentence ="Ako ay isang haliparo .";
		
		assertEquals("haliparot", service.generalize(service.getDistinctNGramOf(correctSentence, incorrectSentence)));
		assertEquals("haliparo", service.generalize(service.getDistinctNGramOf(incorrectSentence, correctSentence)));
	}
	
	@Test 
	public void getDistinctNGramOfTest() {
		
		String correctSentence = "Ako ay isang haliparot .";
		String incorrectSentence ="Ako ay isang haliparo .";
		
		Set<String> correctGramSet = new HashSet<String>();
		correctGramSet.add("ay isang haliparot");
		correctGramSet.add("isang haliparot .");
		
		Set<String> incorrectGramSet = new HashSet<String>();
		incorrectGramSet.add("ay isang haliparo");
		incorrectGramSet.add("isang haliparo .");
		
		assertEquals(correctGramSet, service.getDistinctNGramOf(correctSentence, incorrectSentence));
		assertEquals(incorrectGramSet, service.getDistinctNGramOf(incorrectSentence, correctSentence));
		
	}

}
