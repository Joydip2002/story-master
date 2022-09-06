package com.story.demo.controller;

import java.io.Writer;

import javax.servlet.http.HttpSession;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.story.demo.model.WriterModel;

import com.story.demo.services.GoodStoryServices;

@Controller
public class MyController {
	
//	Autowired GoodStoryService  
	
	@Autowired
	GoodStoryServices services;
	
	
	
	
	
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
			session.setAttribute("WriterSignUpmsg", "Registered Successfully...Please Login Here");
			return "redirect:/writerlogin";
		}
		
	}
	
	
	@RequestMapping("/writerlogin")
	public String writerlogin() {
		return "writerlogin";
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
			WriterModel obj = services.checkEmailPass(email, password);
			session.setAttribute("writerDetailsMassage", obj);
			return "redirect:/WriterDash";
		}
			 
	}
	
	@RequestMapping("/WriterDash")
	public String writerdash(HttpSession session) {
		if(session.getAttribute("writerDetailsMassage")!=null) {
			return "WriterDash";
		}
		else {
			return "redirect:/writerlogin";
		}
	}
	
	
	
	
	@RequestMapping("/tables")
	public String writerTables() {
		return "tables";
	}
	@RequestMapping("/createstory")
	public String storycreate() {
		return "createstory";
	}
	@RequestMapping("/writestory")
	public String storywrite() {
		return "writestory";
	}
	@RequestMapping("/adminsignin")
	public String adminsignin() {
		return "adminsignin";
	}
	@RequestMapping("/adminpanel")
	public String adminpanel() {
		return "adminpanel";
	}
	@RequestMapping("/writerlist")
	public String writerlist() {
		return "writerlist";
	}
	@RequestMapping("/admindue")
	public String admindue() {
		return "admindue";
	}
	@RequestMapping("/adminapprove")
	public String adminapprove() {
		return "adminapprove";
	}
	
	@RequestMapping("/writerprofile")
	public String writerprofile() {
		return "writerprofile";
	}
	@RequestMapping("/userprofile")
	public String userprofile() {
		return "userprofile";
	}
	@RequestMapping("/allWriterList")
	public String WriterList() {
		return "allWriterList";
	}
}