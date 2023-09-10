package com.dto;

import com.entity.Role;

public class UserDto {

	private int id;
	private String name;
	private String email;
	private String userName;
	private String gender;
	private String expiredTime;
	private boolean isEnable;
	private long loginAttempt;
	private boolean isAccountLock;
	private Role role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public long getLoginAttempt() {
		return loginAttempt;
	}

	public void setLoginAttempt(long loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

	public boolean isAccountLock() {
		return isAccountLock;
	}

	public void setAccountLock(boolean isAccountLock) {
		this.isAccountLock = isAccountLock;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
