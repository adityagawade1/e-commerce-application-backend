package com.service;

import com.controller.request.UserRequest;
import com.dto.UserDto;
import com.entity.User;
import com.exception.UserNotFoundException;

public interface UserService {

	void createUser(UserRequest user);

	User getUserByEmailAndIsEnable(String userName, boolean b) throws UserNotFoundException;

	void updateUser(User user) throws UserNotFoundException;

	User getUser(String email);

	UserDto getUser(int id);
	
	
}
