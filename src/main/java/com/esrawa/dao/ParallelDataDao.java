package com.esrawa.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import com.esrawa.domain.*;

public class ParallelDataDao {
	
	private InputReader reader;
	
	public Collection<ParallelData> getAllParallelData(String jointCorpusPath, String jointTagPath) throws IOException{
		
		return readFromInputFile(jointCorpusPath, jointTagPath);
		
	}
	
	private ArrayList<ParallelData> readFromInputFile(String jointCorpusPath, String jointTagPath) throws IOException {
		
		ArrayList<String> jointSentences = new ArrayList<String>(reader.readContents(jointCorpusPath));
		ArrayList<String> jointTags = new ArrayList<String>(reader.readContents(jointTagPath));

		if(jointSentences.size() != jointTags.size()) {
			throw new RuntimeException("Corpus is dirty! Make sure they have the same size!");
		}
		
		return parseInputFile(jointSentences, jointTags);
		
	}
	
	private ArrayList<ParallelData> parseInputFile(ArrayList<String> jointSentences, ArrayList<String> jointTags) throws IOException {
		
		ArrayList<ParallelData> parallelDataListTemp = new ArrayList<ParallelData>();
		
		for(int i = 0; i<jointSentences.size(); i++) {
			String jointSentenceArr[] = jointSentences.get(i).split("\\|");
			String jointTagArr[] = jointTags.get(i).split("\\|");
			parallelDataListTemp.add(new ParallelData().of(
					jointSentenceArr[0], 
					jointSentenceArr[1], 
					jointTagArr[0], 
					jointTagArr[1]));
		}
		
		if(parallelDataListTemp.isEmpty())
			return new ArrayList(Collections.EMPTY_LIST);
		else return parallelDataListTemp;
	}

}
