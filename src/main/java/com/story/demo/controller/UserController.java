package com.story.demo.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.story.demo.model.UserModel;
import com.story.demo.model.WriterModel;
import com.story.demo.services.UserService;

@Controller
public class UserController {
	@Autowired
  UserService userService;
 
	@RequestMapping("/")
	public String myHome() {
		return "index";
	}
	@RequestMapping("/story")
	public String myStory() {
		return "single-post";
	}
	
	
   @RequestMapping("/userLogin")
	public String login() {
		return "login";
	}
   
   @PostMapping("/userLogin")
    public String Userlogin(String useremail,String userpassword,HttpSession session) {
	   System.out.println("\n\n"+useremail+ userpassword);
	   if(userService.checkingEmailPass(useremail, userpassword) == null) {
		   
			System.out.println("Login Un-Sucessfull..");
			session.setAttribute("usermsgWrongPass", "Please Provide Registered Email Id And Password");
			return "redirect:/userLogin";
		}
		else {
			System.out.println("Login Sucessfull..");
//			UserModel obj = userService.checkingEmailPass(useremail, userpassword);
//			session.setAttribute("usermsg", obj);
			return "redirect:/";
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
}
