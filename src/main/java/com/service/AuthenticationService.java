package com.service;

import java.util.Map;

import javax.security.auth.login.AccountLockedException;

import com.controller.request.UserRequest;
import com.exception.PasswordExpireException;
import com.exception.UnAuthorizedException;
import com.exception.UserNotFoundException;

public interface AuthenticationService {

	Map<String, Object> createJwtToken(String userName, String password) throws UnAuthorizedException, UserNotFoundException, AccountLockedException, PasswordExpireException;

	Map<String, String> refreshToken(String token);

	void unLockUser(UserRequest userRequest) throws UserNotFoundException;

	void updatePassword(UserRequest userRequest) throws UserNotFoundException;

}
