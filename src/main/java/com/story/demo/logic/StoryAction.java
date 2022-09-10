package com.story.demo.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StoryAction {
	public String getStory(int author_id,int story_id) throws IOException{
		String path="C:\\Users\\BRAJA KISHOR JANA\\Desktop\\story\\"+author_id+"\\"+story_id+".txt";
		File f=new File(path);
		if(!f.exists())
			return "";
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		    return everything;
		} finally {
		    br.close();
		}
	}
}
