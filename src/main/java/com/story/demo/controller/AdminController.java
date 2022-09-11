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
import com.story.demo.model.WriterModel;
import com.story.demo.repository.ApprovedStoryRepo;
import com.story.demo.repository.CreateStoryRepo;
import com.story.demo.repository.GoodStoryRepo;
import com.story.demo.services.ApprovedStoryService;
import com.story.demo.services.CreateStoryService;

@Controller
public class AdminController {
	
	
	CreateStoryService createStoryService=new CreateStoryService();
	@Autowired
	CreateStoryRepo createStoryRepo;
	
	ApprovedStoryService approvedStoryService=new ApprovedStoryService();
	@Autowired
	ApprovedStoryRepo approvedStoryRepo;
	
	@Autowired
	GoodStoryRepo goodStoryRepo;
	
	
	ArrayList<CreateStoryModel> allDueStory;
	ArrayList<ApprovedStory> approvedStories;
	ArrayList<WriterModel> allWriterList;
	
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
	
	@RequestMapping("/adminLogout")
	public String adminLogout() {
		checkLogin=0;
		return "redirect:/";
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
			allDueStory=(ArrayList<CreateStoryModel>) createStoryRepo.findAll();
			session.setAttribute("allDueStory", allDueStory);
			return "admindue";
		}			
		else
			return "adminsignin";
	}
	
	
	@RequestMapping("/approvedStory/{story}")
	public String dueToApproved(@PathVariable("story") int story) {
		CreateStoryModel obj=createStoryRepo.getReferenceById(story);
		ApprovedStory object=new ApprovedStory(obj.getStory_id(),obj.getStory_name(),obj.getAuthor_id(),obj.getImg_link(),obj.getStory_category(),
				obj.getStory_desc(),obj.getView_count(),obj.getPublish_Date());
		approvedStoryRepo.save(object);
//		int id=obj.getStory_id();
		createStoryRepo.deleteById(obj.getStory_id());
		return "redirect:/admindue";
	}
	
	@RequestMapping("/adminapprove")
	public String adminapprove(HttpSession session) {
		if(checkLogin==1) {
			approvedStories=(ArrayList<ApprovedStory>) approvedStoryRepo.findAll();
			session.setAttribute("allApprovedStory", approvedStories);
			return "adminapprove";	
		}
		else
			return "adminsignin";
	}
	
	@RequestMapping("/deleteStory/{id}")
	public String deleteStory(@PathVariable("id") int id) {
		approvedStoryRepo.deleteById(id);
		return "redirect:/adminapprove";
	}
	
	
	@RequestMapping("/allWriterList")
	public String WriterList(HttpSession session) {
		if(checkLogin==1) {
			allWriterList=(ArrayList<WriterModel>) goodStoryRepo.findAll();
			for (WriterModel writerModel : allWriterList) {
				int view;
				int total_story=approvedStoryRepo.findTotalStoryCount(writerModel.getId());
				if(total_story==0) {
					view=0;
				}
				else {
					view=approvedStoryRepo.findTotalViewOFWriter(writerModel.getId());
				}
				writerModel.setMobile(Integer.toString(view));
				writerModel.setEmail(Integer.toString(total_story));
			}
			session.setAttribute("allWriterList", allWriterList);
			return "allWriterList";
		}
		else
			return "adminsignin";
	}
	
	
	@RequestMapping("/writerDelete/{writerId}")
	public String writerDelete(@PathVariable("writerId") int writerId) {
		goodStoryRepo.deleteById(writerId);
		approvedStoryRepo.deleteStoryByWriterId(writerId);
		return "redirect:/allWriterList";
	}
	
}
