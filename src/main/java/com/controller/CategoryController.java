package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CategoryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.CategoryService;
import com.utils.Constants;
import com.utils.Headers;
import com.utils.ResponseTo;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService service;

	@PostMapping
	@RolesAllowed(value = "ADMIN")
	public ResponseEntity<ResponseTo> createCategory(@Valid @RequestBody CategoryDto categoryDto,
			@RequestHeader("Authorization") String token) {

		service.create(categoryDto);
		return new ResponseEntity<>(
				new ResponseTo(HttpStatus.CREATED.value(), Constants.SUCCESS, Constants.CATEGORY_SUCCESS),
				Headers.getHeader(), HttpStatus.CREATED);
	}

	@RolesAllowed(value = "ADMIN")
	@GetMapping
	public ResponseEntity<ResponseTo> getCategories(@RequestHeader("Authorization") String token,
			@RequestHeader("x-query") String query) throws JsonProcessingException {
		return new ResponseEntity<>(
				new ResponseTo(HttpStatus.OK.value(), Constants.SUCCESS, service.getPaginatedData(query)),
				Headers.getHeader(), HttpStatus.OK);
	}

	@RolesAllowed(value = "ADMIN")
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseTo> deleteCategory(@RequestHeader("Authorization") String token,
			@PathVariable("id") int id) {

		service.delete(id);
		return new ResponseEntity<>(new ResponseTo(HttpStatus.OK.value(), Constants.SUCCESS, Constants.SUCCESS),
				Headers.getHeader(), HttpStatus.OK);

	}

	@RolesAllowed(value = "ADMIN")
	@GetMapping("/all")
	public ResponseEntity<ResponseTo> getCategories() {

		return new ResponseEntity<>(new ResponseTo(HttpStatus.OK.value(), Constants.SUCCESS, service.getCategories()),
				HttpStatus.OK);
	}

}
