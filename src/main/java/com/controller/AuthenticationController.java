package com.controller;

import javax.security.auth.login.AccountLockedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.controller.request.UserRequest;
import com.exception.PasswordExpireException;
import com.exception.UnAuthorizedException;
import com.exception.UserNotFoundException;
import com.service.AuthenticationService;
import com.utils.Constants;
import com.utils.Headers;
import com.utils.ResponseTo;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

	@Autowired
	private AuthenticationService service;

	@GetMapping("/login")
	public ResponseEntity<ResponseTo> login(@RequestParam("username") String username,
			@RequestParam("password") String password)
			throws AccountLockedException, UnAuthorizedException, UserNotFoundException, PasswordExpireException {

		return new ResponseEntity<>(
				new ResponseTo(HttpStatus.OK.value(), Constants.SUCCESS, service.createJwtToken(username, password)),
				Headers.getHeader(), HttpStatus.OK);

	}

	@PostMapping("/refresh-token")
	public ResponseEntity<ResponseTo> refreshToken(@RequestHeader(Constants.AUTHORIZATION) String token) {

		return new ResponseEntity<>(
				new ResponseTo(HttpStatus.OK.value(), Constants.SUCCESS, service.refreshToken(token)),
				Headers.getHeader(), HttpStatus.OK);
	}

	@PutMapping("/unlock-user")
	public ResponseEntity<ResponseTo> unLockUserAccount(@RequestBody UserRequest userRequest)
			throws UserNotFoundException {

		service.unLockUser(userRequest);
		return new ResponseEntity<>(
				new ResponseTo(HttpStatus.OK.value(), Constants.SUCCESS,Constants.UNLOCK_SUCCESS),Headers.getHeader(), HttpStatus.OK);
	}

	@PutMapping("/update-password")
	public ResponseEntity<ResponseTo> updatePasssword(@RequestBody UserRequest userRequest)
			throws UserNotFoundException, InterruptedException {

		Thread.sleep(3000);
		service.updatePassword(userRequest);
		return new ResponseEntity<>(
				new ResponseTo(HttpStatus.OK.value(), Constants.SUCCESS, Constants.PASSWORD_UPDATE_SUCCESS),Headers.getHeader(),
				HttpStatus.OK);
	}

}
