package com.service;

import com.controller.response.PaginatedResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface BaseInterface<T> {

	T create(T entity);

	void update(T entity);

	T get(int id);

	T get(String name);
	
	PaginatedResponse<T> getPaginatedData(String query) throws JsonProcessingException;

	void delete(int id);
	
	boolean exists(int id);
	
	
}
