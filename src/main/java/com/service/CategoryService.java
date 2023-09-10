package com.service;


import java.util.List;

import com.dto.CategoryDto;

public interface CategoryService extends BaseInterface<CategoryDto>{

	List<CategoryDto> getCategories();

	boolean exists(String name);

}
