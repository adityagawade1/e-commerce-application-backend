package com.entity;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.utils.ExpirationDate;
import com.utils.LOG;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertFalse.List;

@Entity
@Table(name = "user")
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	@Column(name = "user_name")
	private String userName;
	private String password;
	private String gender;
	@Column(name = "expired_time")
	private String expiredTime;
	@Column(name = "is_enable")
	private boolean isEnable;
	@Column(name = "login_attempt")
	private long loginAttempt;
	@Column(name = "is_account_lock")
	private boolean isAccountLock;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id")
	private Role role = new Role();

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserName() {
		return userName;
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

	public boolean isAccountLock() {
		return isAccountLock;
	}

	public void setAccountLock(boolean isAccountLock) {
		this.isAccountLock = isAccountLock;
	}

	public void setLoginAttempt(long loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		java.util.List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		Role userRole = getRole();
		LOG.info("Role "+userRole.getRole());
		authorities.add(new SimpleGrantedAuthority(userRole.getRole()));
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return !isAccountLock();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !ExpirationDate.checExpiredDate(getExpiredTime());
	}

	@Override
	public boolean isEnabled() {
		return isEnable();
	}

}
