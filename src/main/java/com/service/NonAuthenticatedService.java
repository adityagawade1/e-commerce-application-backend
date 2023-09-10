package com.service;

import com.controller.request.UserRequest;
import com.exception.CommonException;

public interface NonAuthenticatedService  {

	void registerUser(UserRequest user) throws CommonException ;

}
