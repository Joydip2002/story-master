package com.story.demo.controller;

import java.io.IOException;
import java.text.ParseException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.story.demo.logic.StoryAction;
import com.story.demo.logic.WriterAction;
import com.story.demo.model.ApprovedStory;
import com.story.demo.model.CreateStoryModel;
import com.story.demo.model.WriterModel;
import com.story.demo.repository.ApprovedStoryRepo;
import com.story.demo.services.CreateStoryService;


@Controller
public class CreateStoryController {

	@Autowired
	CreateStoryService createStoryService;
	@Autowired
	ApprovedStoryRepo approvedStoryRepo;
	WriterAction writeract=new WriterAction();
	StoryAction storyAction=new StoryAction();
	int editStroyCheck=0;
	int flagForEditOrCreate=0;
	int sid,aid;
	String story="";
	
	
	
	
	@RequestMapping("/writestory")
	public String storycreate(HttpSession session) {
		session.setAttribute("flagForEditOrCreate", flagForEditOrCreate);
		if(editStroyCheck==1) {
			return "writestory";
		}
		else if(session.getAttribute("storyInfo")==null){
			 return "redirect:/createstory";
			
		}
		else {
			editStroyCheck=0;
			flagForEditOrCreate=0;
			session.setAttribute("flagForEditOrCreate", flagForEditOrCreate);
			return "writestory";
		}
		
	}
	
	
	
	
	
 //	 Postmapping WriteStory 
	
	@PostMapping("/createstory")
	public String writerStories(@ModelAttribute CreateStoryModel info,HttpSession session) throws ParseException {
		WriterModel writerModel = (WriterModel) session.getAttribute("writerDetailsMassage");
		flagForEditOrCreate=0;
		info.setAuthor_id(writerModel.getId());
		System.out.println(info);	
		//Save data in database
		createStoryService.saveStory(info);		
		//Session
//		session.setAttribute("createStoryMsg", "Your Story is Successfully Added..");
		session.setAttribute("storyInfo", info);
		return "redirect:/writestory";
	}
	
	
	
	
	@RequestMapping("/createstory")
	public String writesto(HttpSession session) {
		if(session.getAttribute("writerDetailsMassage")!=null) {			
			return "createstory";
		}
		else {
			return "redirect:/writerlogin";
		}
		
	}
	
	
	
	
	@PostMapping("/writestory")
	public String storywrite(String sto,HttpSession session) throws IOException {
		if(session.getAttribute("writerDetailsMassage")!=null) {
			if(flagForEditOrCreate==1) {
				writeract.writeStory(sto, sid, aid);
				session.setAttribute("storyInfo", null);
			}
			else {
				CreateStoryModel story=(CreateStoryModel) session.getAttribute("storyInfo");
				writeract.writeStory(sto, story.getStory_id(),story.getAuthor_id());
				
			}
			return "redirect:/WriterDash";
		}
		else {
			return "redirect:/writerlogin";
		}
	}
	
	
	
	@RequestMapping("/editStory-{authorId}-{storyId}")
	public ModelAndView editStory(@PathVariable("authorId") int authorId,@PathVariable("storyId") int storyId,HttpSession session) throws IOException {
		WriterModel writerModel = (WriterModel) session.getAttribute("writerDetailsMassage");
		if(authorId!=writerModel.getId()) {
			return new ModelAndView("404");
		}
		if(session.getAttribute("writerDetailsMassage")!=null) {
			editStroyCheck=1;
			flagForEditOrCreate=1;
			session.setAttribute("flagForEditOrCreate", flagForEditOrCreate);
			sid=storyId;
			aid=authorId;
			story=storyAction.getStory(authorId, storyId);
			if(story=="") {
				return new ModelAndView("404");
			}
			ModelAndView modelAndView=new ModelAndView("writestory");
			ApprovedStory approvedStory=approvedStoryRepo.getReferenceById(storyId);
			modelAndView.addObject("imageLink", approvedStory.getImg_link());
			modelAndView.addObject("story", story);
			return modelAndView;
		}
		else {
			return new ModelAndView("redirect:/writerlogin");
		}
	}
	
	
	
	@RequestMapping("/deleteStoryByWriter/{id}")
	public String deleteStory(@PathVariable("id") int id) {
		approvedStoryRepo.deleteById(id);
		return "redirect:/WriterDash";
	}
}
