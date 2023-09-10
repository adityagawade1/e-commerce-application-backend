package com.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.controller.response.PaginatedResponse;
import com.dto.CategoryDto;
import com.entity.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.repository.CategoryRepository;
import com.service.CategoryService;
import com.utils.Constants;
import com.utils.UtilityMethod;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	@Transactional
	public CategoryDto create(CategoryDto entity) {

		Category category = this.modelMapper.map(entity, Category.class);
		category.setCreatedAt(new Date());

		return this.modelMapper.map(categoryRepo.save(category), CategoryDto.class);

	}

	@Override
	@Transactional
	public void update(CategoryDto entity) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public CategoryDto get(int id) {
		if (id == 0) {
			throw new IllegalArgumentException(Constants.EMPTY_ID);
		} else if (!exists(id)) {
			throw new IllegalArgumentException(Constants.CATEGORY_NOT_PRESENT);
		}
		return null;
	}

	@Override
	@Transactional
	public CategoryDto get(String name) {
		if (name == null) {
			throw new IllegalArgumentException(Constants.EMPTY_NAME);
		} else if (!exists(name)) {
			throw new IllegalArgumentException(Constants.CATEGORY_NOT_PRESENT);
		}
		Optional<Category> optionalCategory = categoryRepo.findByName(name);
		return optionalCategory.isPresent() ? this.modelMapper.map(optionalCategory.get(), CategoryDto.class) : null;
	}

	@Override
	@Transactional
	public void delete(int id) {
		if (id == 0) {
			throw new IllegalArgumentException("Category not found");
		}
		Optional<Category> category = categoryRepo.findById(id);
		if (category.isPresent()) {
			categoryRepo.delete(category.get());
		}
	}

	@Override
	public PaginatedResponse<CategoryDto> getPaginatedData(String query) throws JsonProcessingException {

		PaginatedResponse<CategoryDto> paginatedResponse = new PaginatedResponse<>();

		Map<String, String> parseQuery = UtilityMethod.parseQuery(query);
		int pageNumber = Integer.parseInt(parseQuery.get("pageNumber"));
		int pageSize = Integer.parseInt(parseQuery.get("pageSize"));

		Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);

		Page<Category> categories = categoryRepo.findAll(pageRequest);
		paginatedResponse.setPageNumber(pageNumber);
		paginatedResponse.setTotalCount((int) categories.getTotalElements());
		paginatedResponse.setData(categories.getContent().stream()
				.map(category -> this.modelMapper.map(category, CategoryDto.class)).toList());

		return paginatedResponse;
	}

	@Override
	public List<CategoryDto> getCategories() {
		return categoryRepo.findAll().stream().map(category -> this.modelMapper.map(category, CategoryDto.class))
				.toList();
	}

	@Override
	public boolean exists(int id) {
		if (id == 0) {
			throw new IllegalArgumentException(Constants.EMPTY_ID);
		}
		Optional<Category> optionalCategory = categoryRepo.findById(id);
		return optionalCategory.isPresent();
	}

	@Override
	public boolean exists(String name) {
		if (name == null) {
			throw new IllegalArgumentException(Constants.EMPTY_NAME);
		}
		return categoryRepo.findByName(name).isPresent();
	}

}
