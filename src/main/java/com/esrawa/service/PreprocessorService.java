package com.esrawa.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.esrawa.dao.OutputWriter;
import com.opencsv.CSVReader;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class PreprocessorService {

	private ArrayList<String> phraseList = new ArrayList<String>();
	private CSVReader reader;
	private OutputWriter outputWriter;
	private ArrayList<String> jointSentences = new ArrayList<String>();
	private ArrayList<String> jointTags = new ArrayList<String>();
	private final MaxentTagger tagger = new MaxentTagger("src\\main\\resources\\filipino-left5words-owlqn2-distsim-pref6-inf2.tagger");
	
	public static void main(String[] args){
		
		try {
			new PreprocessorService().startProcess("src\\main\\resources\\Parallel Corpus.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void startProcess(String path) throws IOException{
		reader = new CSVReader(new FileReader(path));
		String[] nextLine;
		loadReferenceFile();
		
		while ((nextLine = reader.readNext()) != null) {
			toPrescribedFormat(nextLine[0],nextLine[1]);
		}
		
		outputWriter.write("src\\main\\resources\\jointSentences.txt", jointSentences);
		outputWriter.write("src\\main\\resources\\jointTags.txt", jointTags);
	}
	
	public String removeSpecialCharacters(String sentence){
		
		sentence = sentence.replaceAll(", ", " , ");
		sentence = sentence.replaceAll("\\.", " \\.");
		sentence = sentence.replaceAll("\\!", " \\! ");
		sentence = sentence.replaceAll("\\?", "\\? ");
		sentence = sentence.replaceAll("\\;", " \\;");
		sentence = sentence.replaceAll("\\s+$", "");
		
		return sentence;
		
	}
	
	public void toPrescribedFormat(String correct, String incorrect) {

		correct = formatAccordingToPhraseList(correct);
		incorrect = formatAccordingToPhraseList(incorrect);

		correct = removeSpecialCharacters(correct);
		incorrect = removeSpecialCharacters(incorrect);

		jointSentences.add(correct + "|" + incorrect);
		jointTags.add(generateTagOf(correct)  + "|" + generateTagOf(incorrect));
		
	}
	
	public String formatAccordingToPhraseList(String sentence) {
		
		for (String s : phraseList) {
			if (StringUtils.containsIgnoreCase(sentence, s)) {

				int index = StringUtils.indexOfIgnoreCase(sentence, s) - 1;
				if ((index >= 0 && sentence.charAt(index) == ' ') || index == -1) {

					// copy exact word with different case (uppercase,
					// lowercase)
					int newIndex = StringUtils.indexOfIgnoreCase(sentence, s);
					String newString = sentence.substring(newIndex, newIndex + s.length());
					String temp = newString.replaceAll(" ", "_");
					sentence = sentence.replaceAll(newString, temp);
				}

			}
		}
		
		return sentence;
		
	}
	
	private void loadReferenceFile() {

		File file = new File("src\\main\\resources\\PhraseList.txt");

		// Specify the encoding of the file.
		Charset encoding = Charset.forName("UTF-8");
		// Path to the file
		Path path = Paths.get(file.getAbsolutePath());

		// Try with resources to close reader at the end.
		try (BufferedReader br = Files.newBufferedReader(path, encoding)) {
			// The line read in.
			String line;

			// Combine assignment with null check (null being EOF).
			// Could put the assignment on its on line if you'd prefer.
			while ((line = br.readLine()) != null) {
				phraseList.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String generateTagOf(String sentence){
		
		String tagged = tagger.tagString(sentence);
		//System.out.println(tagged);
		
		String[] array = tagged.split(" ");
		StringBuilder rawSB = new StringBuilder();
		StringBuilder taggedSB = new StringBuilder();
				

		for(int a=0; a<array.length; a++)
		{
			String[] temp = array[a].split("\\|");
			rawSB.append(temp[0] + " ");
			taggedSB.append(temp[1] + " ");
		}

		String raw = rawSB.toString();
		String taggedProper = taggedSB.toString();
		
		System.out.println(sentence+"/"+taggedProper);
		return taggedProper.substring(0, taggedProper.length()-1);
	}
	

}
