package com.story.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.story.demo.model.ApprovedStory;
import com.story.demo.model.CreateStoryModel;
import com.story.demo.repository.ApprovedStoryRepo;
import com.story.demo.repository.CreateStoryRepo;

@Service
public class ApprovedStoryService {

	@Autowired
	ApprovedStoryRepo approvedStoryRepo;
	
	public void saveStory(ApprovedStory apsto){
		approvedStoryRepo.save(apsto);
	}
}
