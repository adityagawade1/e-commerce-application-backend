package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.UserService;
import com.utils.Constants;
import com.utils.Headers;
import com.utils.ResponseTo;

@RestController
@RequestMapping(value = "/user/")
public class UserController {
	
	@Autowired
	private UserService userService;

	
	@GetMapping("{id}")
	public ResponseEntity<ResponseTo> getUser(@PathVariable("id")int id, @RequestHeader("Authorization")String token){
		
		return new ResponseEntity<>(new ResponseTo(HttpStatus.OK.value(), Constants.SUCCESS, userService.getUser(id)),Headers.getHeader() ,HttpStatus.OK);
	}
}
