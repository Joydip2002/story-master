package com.story.demo.controller;

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

import com.story.demo.model.CreateStoryModel;
import com.story.demo.model.WriterModel;
import com.story.demo.services.CreateStoryService;
@Controller
public class CreateStoryController {

	@Autowired
	CreateStoryService createStoryService;
	
	@RequestMapping("/createstory")
	public String storycreate(HttpSession session) {
		
		if(session.getAttribute("writerDetailsMassage")!=null) {
			return "createstory";
		}
		else {
			return "redirect:/writerlogin";
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
		
		return "redirect:/WriterDash";
	}
}
