package com.story.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.story.demo.model.WriterModel;
import com.story.demo.repository.GoodStoryRepo;

@Service
public class GoodStoryServices {

	@Autowired
	GoodStoryRepo repo;
	
	public void writersave(WriterModel model) {
		repo.save(model);
	}
	
	public WriterModel writerEamil(String email) {
		WriterModel obj = repo.findWriterbyEmailId(email);
		return obj;
	}
	
	public WriterModel checkEmailPass(String email, String password) {
		WriterModel obj = repo.writerLoginCheck(email,password);
		return obj;
	}
	
}
