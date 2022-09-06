package com.story.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.story.demo.model.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Integer>{
//Unique Email Already Exists 
	 @Query("select u from UserModel u where useremail = ?1")
	public UserModel findUserEmailId(String useremail);
	
//	 Login Credentials
	 @Query("select u from UserModel u where useremail = ?1 and userpassword = ?2")
	 public UserModel cehckUserEmailPass(String useremail,String userpassword); 
}
