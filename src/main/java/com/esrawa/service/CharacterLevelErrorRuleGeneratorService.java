package com.esrawa.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.esrawa.dao.ParallelDataDao;
import com.esrawa.domain.ErrorRule;
import com.esrawa.domain.NgramIterator;
import com.esrawa.domain.NgramWordIterator;
import com.esrawa.domain.ParallelData;

class CharacterLevelErrorRuleGeneratorService extends ErrorRuleGeneratorService implements ErrorRuleGeneratorInterface{
	
	private final ParallelDataDao parallelDataDao;
	private final String JOINT_CORPUS_PATH = "src\\main\\resources\\jointSentences.txt";
	private final String JOINT_TAG_PATH = "src\\main\\resources\\jointTags.txt";
	private final int gram; // n-gram

	public CharacterLevelErrorRuleGeneratorService(ParallelDataDao parallelDataDao, int gram) {
		super(parallelDataDao, gram);
		this.parallelDataDao = parallelDataDao;
		this.gram = gram;
		
	}

	@Override
	public ErrorRule generateRules() throws IOException {
		
		ArrayList<ParallelData> parallelData = new ArrayList(getAllParallelData());
		
		for(int i = 0; i<parallelData.size(); i++) {
			
			ParallelData p = parallelData.get(i);
			
			//Set<String> correctGramList = getDistinctNGramOf(p.getCorrectSentence(), p.getIncorrectSentence());
			//Set<String> incorrectGramList = getDistinctNGramOf(p.getIncorrectSentence(), p.getCorrectSentence());
	
		}
		
		return super.generateRules();
	}
	
	public Set<String> getDistinctNGramOf(String correctSentence, String incorrectSentence){
		
		NgramIterator current = getTokenNgram(gram, correctSentence);
		NgramIterator other = getTokenNgram(gram, incorrectSentence);
		
		return current.distinctGramsOfThisAndOf(other);
		
	}
	
	private NgramIterator getTokenNgram(int gram, String sentence) {

		return new NgramIterator(gram, sentence);

	}
	
	public String getFrequentlyOccuringWord(Set<String> nGramSet) {

		Map<String, Integer> frequentWordMap = converNgramSetToMap(nGramSet);
		Entry<String, Integer> maxEntry = getMaxEntry(frequentWordMap);

		if (maxEntry != null) {
			return maxEntry.getKey();
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

}
