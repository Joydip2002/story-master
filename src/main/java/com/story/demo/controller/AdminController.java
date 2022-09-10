package com.story.demo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.story.demo.model.ApprovedStory;
import com.story.demo.model.CreateStoryModel;
import com.story.demo.repository.ApprovedStoryRepo;
import com.story.demo.repository.CreateStoryRepo;
import com.story.demo.services.ApprovedStoryService;
import com.story.demo.services.CreateStoryService;

@Controller
public class AdminController {
	CreateStoryService createStoryService=new CreateStoryService();
	ApprovedStoryService approvedStoryService=new ApprovedStoryService();
	@Autowired
	CreateStoryRepo repo;
	@Autowired
	ApprovedStoryRepo approvedStoryRepo;
	ArrayList<CreateStoryModel> allDueStory;
	int checkLogin=0;
	@RequestMapping("/adminsignin")
	public String adminsignin() {
		if(checkLogin==1)
			return "adminpanel";
		else
			return "adminsignin";
	}
	@PostMapping("/adminLogin")
	public String adminLogin(String email,String password) {
		if(email.equals("sjtpb2020@gmail.com") && password.equals("sjtpb")) {
			checkLogin=1;
			
			
			return "adminpanel";
		}
		else {
			return "adminsignin";
		}
	}
	@RequestMapping("/adminpanel")
	public String adminpanel() {
		if(checkLogin==1)
			return "adminpanel";
		else
			return "adminsignin";
	}
	
	@RequestMapping("/admindue")
	public String admindue(HttpSession session) {
		if(checkLogin==1) {		
			allDueStory=(ArrayList<CreateStoryModel>) repo.findAll();
			session.setAttribute("allDueStory", allDueStory);
			return "admindue";
		}			
		else
			return "adminsignin";
	}
	@RequestMapping("/approvedStory/{story}")
	public String dueToApproved(@PathVariable("story") int story) {
		CreateStoryModel obj=repo.getReferenceById(story);
		ApprovedStory object=new ApprovedStory(obj.getStory_id(),obj.getStory_name(),obj.getAuthor_id(),obj.getImg_link(),obj.getStory_category(),
				obj.getStory_desc(),obj.getView_count(),obj.getPublish_Date());
		approvedStoryRepo.save(object);
		int id=obj.getStory_id();
		repo.deleteById(obj.getStory_id());
//		allDueStory=(ArrayList<CreateStoryModel>) createStoryService.findAllDueStory();
		return "redirect:/admindue";
	}
	@RequestMapping("/adminapprove")
	public String adminapprove() {
		if(checkLogin==1)
			return "adminapprove";
		else
			return "adminsignin";
	}
	
	@RequestMapping("/allWriterList")
	public String WriterList() {
		if(checkLogin==1)
			return "allWriterList";
		else
			return "adminsignin";
	}
	
}
