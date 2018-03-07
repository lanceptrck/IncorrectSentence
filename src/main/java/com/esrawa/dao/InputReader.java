package com.esrawa.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;

public class InputReader {
	
	public static List<String> readContents(String path) throws IOException{
		
		FileReader fr = new FileReader(path);
		BufferedReader textReader = new BufferedReader(fr);
		
		List<String> lines = new ArrayList<String>();
		
		String aLine;
		
		while((aLine = textReader.readLine())!=null){
			lines.add(aLine);
		}
		
		return lines;
		
	}

}
