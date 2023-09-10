package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controller.request.UserRequest;
import com.entity.User;
import com.exception.CommonException;
import com.service.NonAuthenticatedService;
import com.utils.Constants;
import com.utils.Headers;
import com.utils.ResponseTo;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/non-authenticate")
public class NonAuthenticatedController {

	@Autowired
	private NonAuthenticatedService service;

	@PostMapping("/register")
	public ResponseEntity<ResponseTo> registerUser(@Valid @RequestBody UserRequest user) throws CommonException {
		service.registerUser(user);

		return new ResponseEntity<>(
				new ResponseTo(HttpStatus.CREATED.value(), Constants.SUCCESS, Constants.REGISTER_USER_SUCCESS),
				Headers.getHeader(), HttpStatus.CREATED);
	}
}
