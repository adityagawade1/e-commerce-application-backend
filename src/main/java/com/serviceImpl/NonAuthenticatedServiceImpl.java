package com.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.controller.request.UserRequest;
import com.entity.User;
import com.exception.CommonException;
import com.exception.UserNotFoundException;
import com.service.NonAuthenticatedService;
import com.service.UserService;

@Service
public class NonAuthenticatedServiceImpl implements NonAuthenticatedService {

	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public void registerUser(UserRequest user) throws CommonException {
		
		User getUser = userService.getUser(user.getEmail());
		
		if(getUser != null && getUser.isEnable()) {
			throw new CommonException("User already present with this mail id ");
		}
		
		userService.createUser(user);
		
	}

}
