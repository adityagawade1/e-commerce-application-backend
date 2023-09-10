package com.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.controller.response.PaginatedResponse;
import com.dto.CategoryDto;
import com.dto.ProductDto;
import com.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.repository.ProductRepository;
import com.service.CategoryService;
import com.service.ProductService;
import com.utils.JsonUtils;
import com.utils.LOG;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryService categoryService;

	@Value("${upload.directory}")
	private String uploadPath;

	@Transactional
	@Override
	public ProductDto create(ProductDto entity) {

		LOG.info("Product : "+ JsonUtils.getGson().toJson(entity));
		
		CategoryDto categoryDto = categoryService.get(entity.getCategoryDto().getName());
		if (categoryDto == null)
			throw new IllegalArgumentException("No category present with this name");

		Product product = this.modelMapper.map(entity, Product.class);
		Date date = new Date();
		product.setCreatedAt(String.valueOf(date.getTime()));
		return this.modelMapper.map(productRepository.save(product), ProductDto.class);

	}

	@Transactional
	@Override
	public void update(ProductDto entity) {

		Optional<Product> optionalProduct = productRepository.findById(entity.getId());
		Product product = optionalProduct.isPresent() ? optionalProduct.get() : null;
		if (product != null) {
			product.setName(entity.getName());
			product.setDescription(entity.getDescription());
			product.setDiscountedPrice(entity.getDiscountedPrice());
			product.setPrice(entity.getPrice());
			product.setModel(entity.getModel());

			Date date = new Date();
			product.setUpdatedAt(String.valueOf(date.getTime()));

			productRepository.save(product);
		}
	}

	@Override
	public ProductDto get(int id) {
		if (id == 0) {
			throw new IllegalArgumentException("product id is not available");
		}

		Optional<Product> optionalProduct = productRepository.findById(id);
		return optionalProduct.isPresent() ? this.modelMapper.map(optionalProduct.get(), ProductDto.class) : null;
	}

	@Override
	public ProductDto get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginatedResponse<ProductDto> getPaginatedData(String query) throws JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void uploadProductImage(MultipartFile multiPartFile, int id) throws IOException {

		try {
			String fileName = StringUtils.cleanPath(multiPartFile.getOriginalFilename());

			File file = new File(uploadPath);

			if (!file.exists()) {
				file.mkdirs();
			}
			Path path = Paths.get(uploadPath, fileName);
			Files.copy(multiPartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			ProductDto productDto = get(id);
			productDto.setImageUrl(String.valueOf(path));
			productRepository.save(this.modelMapper.map(productDto, Product.class));
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to upload image");
		}

	}

	@Override
	public boolean exists(int id) {
		return false;
	}

}
