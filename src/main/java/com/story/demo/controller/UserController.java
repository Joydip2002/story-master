package com.story.demo.controller;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.story.demo.logic.StoryAction;
import com.story.demo.model.ApprovedStory;
import com.story.demo.model.UserModel;
import com.story.demo.repository.ApprovedStoryRepo;
import com.story.demo.services.UserService;

@Controller
public class UserController {
	@Autowired
  	UserService userService;
	
	@Autowired
	ApprovedStoryRepo approvedStoryRepo;
 
	StoryAction storyAction=new StoryAction();
	
	String checkForRedirectPost="";
  
	@RequestMapping("/")
	public ModelAndView myHome(HttpSession session) {
		ArrayList<ApprovedStory> stories=approvedStoryRepo.findAllOrderByView();
		ArrayList<ApprovedStory> Drama = approvedStoryRepo.findStoryOfCatagory("Drama");
		ArrayList<ApprovedStory> Comedy = approvedStoryRepo.findStoryOfCatagory("Comedy");
		ArrayList<ApprovedStory> Sci_fi = approvedStoryRepo.findStoryOfCatagory("Sci-fi");
		ArrayList<ApprovedStory> Horror = approvedStoryRepo.findStoryOfCatagory("Horror");
		ArrayList<ApprovedStory> Tradegy = approvedStoryRepo.findStoryOfCatagory("Tradegy");
		ArrayList<ApprovedStory> Romantic = approvedStoryRepo.findStoryOfCatagory("Romantic");
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("Drama",Drama);
		mv.addObject("Comedy",Comedy);
		mv.addObject("Sci_fi",Sci_fi);
		mv.addObject("Horror",Horror);
		mv.addObject("Tradegy",Tradegy);
		mv.addObject("Drama",Drama);
		mv.addObject("Romantic",Romantic);
		session.setAttribute("stories", stories);
	
		return mv;
	}
	
  
	@RequestMapping("/story-{author_id}-{story_id}")
	public ModelAndView myStory(@PathVariable("author_id") int author_id,@PathVariable("story_id") int story_id ,HttpSession session) throws IOException{
		if(session.getAttribute("usermsg")!=null) {
			String storyFile=storyAction.getStory(author_id, story_id);
			if(storyFile=="") {
				ModelAndView modelAndView=new ModelAndView("404");
				return modelAndView;
			}
			ModelAndView modelAndView=new ModelAndView("single-post");
			modelAndView.addObject("storyFile", storyFile);
			ApprovedStory storyInfo=approvedStoryRepo.getReferenceById(story_id);
			int view=storyInfo.getView_count();
			view++;
			approvedStoryRepo.incrementViewCount(story_id, view);
			modelAndView.addObject("storyInfo", storyInfo);
			return modelAndView;
		}
		else {
			ModelAndView modelAndView=new ModelAndView("login");
			checkForRedirectPost="story-"+author_id+"-"+story_id;
			return modelAndView;
		}
	}
	
	
   @RequestMapping("/userLogin")
	public String login() {
		return "login";
	}
   
   @PostMapping("/userLogin")
    public String Userlogin(String useremail,String userpassword,HttpSession session) {
	   if(userService.checkingEmailPass(useremail, userpassword) == null) {		   
			System.out.println("Login Un-Sucessfull..");
			session.setAttribute("usermsgWrongPass", "Please Provide Registered Email Id And Password");
			return "redirect:/userLogin";
		}
		else {
			System.out.println("Login Sucessfull..");
			UserModel obj = userService.checkingEmailPass(useremail, userpassword);
			session.setAttribute("usermsg", obj);
			if(checkForRedirectPost=="")
				return "redirect:/";
			else
				return "redirect:/"+checkForRedirectPost;
		}
   }
	
	@RequestMapping("/userSignup")
	public String signup() {	
		return "signup";
	}
	
	 @PostMapping("/userSignup")
	   public String UserSignup(@ModelAttribute UserModel info,HttpSession session) {
		   if (!info.getUserpassword().equals(info.getUsercpaasword())) {
			   session.setAttribute("userMassage", "Password Mismatch");
			return "redirect:/userSignup";
		 }
		   else if(userService.checkDuplicateEmail(info.getUseremail()) != null) {
			   session.setAttribute("userMassage", "Email Already Exist");
				return "redirect:/userSignup";
		   }
		 else{
			   System.out.println(info); 
			   userService.userSave(info);
			   session.setAttribute("userLoginMassage", "Registered Successful..Login Here");
			return "redirect:/userLogin"; 
		   }
		     
	   }
	 
	 @RequestMapping("/userprofile")
		public String userprofile(HttpSession session) {
			if(session.getAttribute("usermsg") != null) {
				return "userprofile";
			}
			else{
				checkForRedirectPost="userprofile";
				return "redirect:/userLogin";
			}
		}
	 @RequestMapping("/userLogout")
	 public String userLogout(HttpSession session) {
		 if(session.getAttribute("usermsg")!=null) {
				session.removeAttribute("usermsg");
		 }
		 return "redirect:/";	
	 }
	 
	 @RequestMapping("/*")
	 public String handleWhiteLevelError() {
		 return "404";
	 }
}
