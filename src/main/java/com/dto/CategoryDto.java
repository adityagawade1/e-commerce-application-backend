package com.dto;

import jakarta.validation.constraints.Pattern;

public class CategoryDto {

	private int id;

	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name should not contain number and special characters")
	private String name;

	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "type should not contain number and special characters")
	private String type;
	private String description;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
