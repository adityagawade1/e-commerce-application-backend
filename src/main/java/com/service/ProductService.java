package com.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.dto.ProductDto;

public interface ProductService extends BaseInterface<ProductDto>{

	//ProductDto create(T entity);
	
	void uploadProductImage(MultipartFile multiPartFile, int id) throws IOException;

}
