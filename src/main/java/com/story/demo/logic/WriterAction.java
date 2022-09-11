package com.story.demo.logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterAction {
	
	
	public void createFolder(int id) {
		String path="C:\\Users\\HP\\Downloads\\writerStory\\"+id;
		System.out.println("folder created");
		File f = new File(path);
		f.mkdir();
	}
	
	public void writeStory(String story,int storyId,int authorId) throws IOException {
		String path="C:\\Users\\HP\\Downloads\\writerStory\\"+authorId+"\\"+storyId+".txt";
		File f=new File(path);
		f.createNewFile();
		FileWriter myWriter = new FileWriter(path);
	    myWriter.write(story);
	    myWriter.close();
	}
}
