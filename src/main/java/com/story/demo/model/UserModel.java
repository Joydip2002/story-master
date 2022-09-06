package com.story.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String userName;
	private String usermobile;
	private String useremail;
	private String useraddress;
	private String userpassword;
	private String usercpaasword;
	
	public UserModel() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUsermobile() {
		return usermobile;
	}

	public void setUsermobile(String usermobile) {
		this.usermobile = usermobile;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getUseraddress() {
		return useraddress;
	}

	public void setUseraddress(String useraddress) {
		this.useraddress = useraddress;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getUsercpaasword() {
		return usercpaasword;
	}

	public void setUsercpaasword(String usercpaasword) {
		this.usercpaasword = usercpaasword;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", userName=" + userName + ", usermobile=" + usermobile + ", useremail="
				+ useremail + ", useraddress=" + useraddress + ", userpassword=" + userpassword + ", usercpaasword="
				+ usercpaasword + "]";
	}
	
	
	

	 
	
	
	 
	
}
