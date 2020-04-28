package com.domen.entity;

import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Document()
@ApiModel(description = "Employee Object")
public class Employee {

//private String id;
private String fullname;

@Id
private String username;
private String password;
private String mobile;
public String getFullname() {
	return fullname;
}
public void setFullname(String fullname) {
	this.fullname = fullname;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
@Override
public String toString() {
	return "User [fullname=" + fullname + ", username=" + username + ", password=" + password + ", mobile=" + mobile
			+ "]";
}
public Employee(String fullname, String username, String password, String mobile) {
	super();
	this.fullname = fullname;
	this.username = username;
	this.password = password;
	this.mobile = mobile;
}
/*
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}*/

	public Employee() {
	super();
}



}