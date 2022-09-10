package com.story.demo.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.story.demo.logic.WriterAction;
import com.story.demo.model.CreateStoryModel;
import com.story.demo.model.WriterModel;
import com.story.demo.services.CreateStoryService;
@Controller
public class CreateStoryController {

	@Autowired
	CreateStoryService createStoryService;
	WriterAction writeract=new WriterAction();
	
	@RequestMapping("/writestory")
	public String storycreate(HttpSession session) {		
		 if(session.getAttribute("storyInfo")==null){
			 return "redirect:/createstory";
			
		}
		else {
			return "writestory";
		}
		
	}
	
 //	 Postmapping WriteStory 
	
	@PostMapping("/createstory")
	public String writerStories(@ModelAttribute CreateStoryModel info,HttpSession session) throws ParseException {
		WriterModel writerModel = (WriterModel) session.getAttribute("writerDetailsMassage");
		info.setAuthor_id(writerModel.getId());
		System.out.println(info);
		
		//Save data in database
		createStoryService.saveStory(info);
		
		//Session
		session.setAttribute("createStoryMsg", "Your Story is Successfully Added..");
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
			CreateStoryModel story=(CreateStoryModel) session.getAttribute("storyInfo");
			WriterModel writerModel=(WriterModel) session.getAttribute("writerDetailsMassage");
			writeract.writeStory(sto, story.getStory_id(),writerModel.getId());
			return "WriterDash";
		}
		else {
			return "redirect:/writerlogin";
		}
	}
}
