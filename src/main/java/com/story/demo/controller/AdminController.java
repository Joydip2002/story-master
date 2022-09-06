package com.story.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
	@RequestMapping("/adminsignin")
	public String adminsignin() {
		return "adminsignin";
	}
	@RequestMapping("/adminpanel")
	public String adminpanel() {
		return "adminpanel";
	}
	
	@RequestMapping("/admindue")
	public String admindue() {
		return "admindue";
	}
	@RequestMapping("/adminapprove")
	public String adminapprove() {
		return "adminapprove";
	}
	
}
