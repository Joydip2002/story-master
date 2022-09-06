package com.story.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.story.demo.model.UserModel;
import com.story.demo.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo repo;
	
	public void userSave(UserModel model) {
		repo.save(model);
	}
	
	public UserModel checkDuplicateEmail(String email) {
	 	UserModel obj= repo.findUserEmailId(email);
	 	return obj;
	}
	
	public UserModel checkingEmailPass(String useremail,String userpassword) {
		UserModel obj =  repo.cehckUserEmailPass(useremail, userpassword);
		return obj;
	}

}
