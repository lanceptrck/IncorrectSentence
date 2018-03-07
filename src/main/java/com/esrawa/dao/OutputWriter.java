package com.esrawa.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class OutputWriter {
	
	public static void write(String path, ArrayList<String> data) throws IOException{
		
	PrintWriter writer = new PrintWriter(path, "UTF-8");
	
	for(int i = 0; i<data.size(); i++)
		writer.println(data.get(i));
	
	writer.close();
		
	}

}
