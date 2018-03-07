package com.esrawa.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import com.esrawa.dao.ParallelDataDao;

public class CharacterLevelErrorRuleGeneratorServiceTest {

	private final String JOINT_CORPUS_PATH = "src\\main\\resources\\jointSentences.txt";
	private final String JOINT_TAG_PATH = "src\\main\\resources\\jointTags.txt";
	private final ParallelDataDao dao = new ParallelDataDao();
	private final CharacterLevelErrorRuleGeneratorService service = new CharacterLevelErrorRuleGeneratorService(dao, 3);


	@Test
	public void generalize_test() throws IOException
	{
		String correctSentence = "Ako ay isang haliparot .";
		String incorrectSentence ="Ako ay isang haliparo .";
		
		assertEquals("haliparot", service.getFrequentlyOccuringWord(service.getDistinctNGramOf(correctSentence, incorrectSentence)));
		assertEquals("haliparo", service.getFrequentlyOccuringWord(service.getDistinctNGramOf(incorrectSentence, correctSentence)));
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
