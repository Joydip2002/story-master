package com.story.demo.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.story.demo.logic.WriterAction;
import com.story.demo.model.ApprovedStory;
import com.story.demo.model.WriterModel;
import com.story.demo.repository.ApprovedStoryRepo;
import com.story.demo.services.GoodStoryServices;

@Controller
public class MyController {
	
//	Autowired GoodStoryService  
	
	WriterModel obj;
	
	@Autowired
	GoodStoryServices services;
	
	WriterAction writeract=new WriterAction();
	
	@Autowired
	ApprovedStoryRepo approvedStoryRepo;
	
	@RequestMapping("/writersignup")
	public String writersignup() {
		return "writersignup";
	}
	
	
	@PostMapping("/WriterSignup")
	
	// This is a method that is called when the user clicks the login button.
	public String writerSignup(@ModelAttribute WriterModel info,HttpSession session) {
		
		// Checking if the email and password are correct.
		if(!info.getPassword().equals(info.getCpassword())) {
			session.setAttribute("WriterSignUpmsg", "Password Mismatch");
			return "redirect:/writersignup";
		}
		else if(services.writerEamil(info.getEmail()) != null){
			
			// Setting a message in the session.
			session.setAttribute("WriterSignUpmsg", "Email Already Exists..");
			return "redirect:/writersignup";
		}
		else {
			System.out.println(info);
			services.writersave(info);
			writeract.createFolder(info.getId());
			session.setAttribute("WriterSignUpmsg", "Registered Successfully...Please Login Here");
			return "redirect:/writerlogin";
		}
		
	}
	
	
	@RequestMapping("/writerlogin")
	public String writerlogin(HttpSession session) {
		if(session.getAttribute("writerDetailsMassage")!=null) {
			return "redirect:/WriterDash";
		}
		else {
			return "writerlogin";
		}
	}
	
	
//	Writer login Mapping part 
	@PostMapping("/WriterLogin")
	public String writeLogin(String email,String password,HttpSession session) {
		
		if(services.checkEmailPass(email, password) == null) {
			System.out.println("Login Un-Sucessfull..");
			session.setAttribute("WriterSignUpmsg", "Please Provide Registered Email Id And Password");
			return "redirect:/writerlogin";
		}
		else {
			System.out.println("Login Sucessfull..");
			obj = services.checkEmailPass(email, password);
			session.setAttribute("writerDetailsMassage", obj);
			return "redirect:/WriterDash";
		}
			 
	}
	
	@RequestMapping("/WriterDash")
	public ModelAndView writerdash(HttpSession session) {
		if(session.getAttribute("writerDetailsMassage")!=null) {
			WriterModel writerModel =(WriterModel) session.getAttribute("writerDetailsMassage");
			int writerId=writerModel.getId();
			int writerStoryCount=approvedStoryRepo.findTotalStoryCount(writerId);
			int viewOfWriter;
			if(writerStoryCount==0) {
				viewOfWriter=0;
			}
			else {
				viewOfWriter=approvedStoryRepo.findTotalViewOFWriter(writerId);
			}
			session.setAttribute("totalStoryApproved", writerStoryCount);
			session.setAttribute("viewOfWriter", viewOfWriter);
			
			ModelAndView modelAndView=new ModelAndView("WriterDash");
			ArrayList<ApprovedStory> allStoryOfWriter=approvedStoryRepo.findAllStoryOfWriter(writerId);
			modelAndView.addObject("allStoryOfWriter", allStoryOfWriter);
			
			return modelAndView;
		}
		else {
			return new ModelAndView("writerlogin");
		}
	}
	
	
	
	@RequestMapping("/WriterLogout")
	public String writerLogout(HttpSession session) {
		if(session.getAttribute("writerDetailsMassage")!=null) {
			session.removeAttribute("writerDetailsMassage");
			return "redirect:/";
		}
		else {
			return "redirect:/writerlogin";
		}
	}
	
	
	@RequestMapping("/writerlist")
	public ModelAndView writerlist(HttpSession session) {
		ModelAndView modelAndView;
		if(session.getAttribute("writerDetailsMassage")!=null) {
			modelAndView=new ModelAndView("writerlist");
			WriterModel writerModel =(WriterModel) session.getAttribute("writerDetailsMassage");
			int writerId=writerModel.getId();
			ArrayList<ApprovedStory> allStoryOfWriter=approvedStoryRepo.findAllStoryOfWriter(writerId);
			modelAndView.addObject("allStoryOfWriter", allStoryOfWriter);
		}
		else {
			modelAndView=new ModelAndView("writerlogin");
		}
		return modelAndView;
	}
	
	
	@RequestMapping("/tables")
	public String writerTables() {
		return "tables";
	}
	
	
	@RequestMapping("/writerprofile")
	public ModelAndView writerprofile(HttpSession session) {
		if(session.getAttribute("writerDetailsMassage") != null) {
			String folder="D:\\SpringBoot\\story\\story-master\\src\\main\\resources\\static\\writerimage\\";
			String img="/writerimage/";
			Path path = Paths.get(folder+obj.getId()+".jpeg");
			if(Files.exists(path) && !Files.isDirectory(path)) {
		      img=img+obj.getId()+".jpeg";
			}
			else {
				img+="avatar7.png";
			}
			ModelAndView modelAndView=new ModelAndView("writerprofile");
			modelAndView.addObject("img", img);
			return modelAndView;
		}
		else {
			return new ModelAndView("redirect:/writerlogin");
		}
	}
	
	 @RequestMapping("/writerImage")
	 public String userImageChange(@RequestParam("userImg") MultipartFile file,HttpSession session) throws IOException {
		 if(session.getAttribute("writerDetailsMassage") != null) {
			 String folder="D:\\SpringBoot\\story\\story-master\\src\\main\\resources\\static\\writerimage\\";
			 byte[] bytes=file.getBytes();
			 Path path=Paths.get(folder+obj.getId()+".jpeg");
			 Files.write(path, bytes);
			 return "redirect:/writerprofile";
		 }
		 else{
			return "redirect:/writerlogin";
		 }
	 }
	
}
