package com.controller.request;

import com.annotation.Password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class UserRequest {

	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name should not contain number and special characters")
	private String name;

	@Email(message = "invalid email id")
	private String email;

	private String gender;

	//@Password
	 @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d!@#$%^&*()_+]{10,}$" ,message="password contain atleast one special character and length 10")
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
