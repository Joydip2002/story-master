package com.story.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.story.demo.model.CreateStoryModel;
import com.story.demo.repository.CreateStoryRepo;

@Service
public class CreateStoryService {

	@Autowired
	CreateStoryRepo createStoryRepo;
	
	public void saveStory(CreateStoryModel createStoryModel){
		createStoryRepo.save(createStoryModel);
	}
}
