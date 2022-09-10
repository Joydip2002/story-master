package com.story.demo.services;

import java.util.ArrayList;
import java.util.List;

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
	
//	public List<CreateStoryModel> findAllDueStory(){
//		ArrayList<CreateStoryModel> allDueStory=(ArrayList<CreateStoryModel>) createStoryRepo.findAllStory();
//		return allDueStory;
//	}
	public void deleteStory(int id) {
		createStoryRepo.deleteById(id);
	}
}
