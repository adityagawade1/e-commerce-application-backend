package com.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dto.ProductDto;
import com.service.ProductService;
import com.utils.Constants;
import com.utils.Headers;
import com.utils.ResponseTo;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping
	public ResponseEntity<ResponseTo> addProduct(@RequestBody ProductDto productDto) {
		
		return new ResponseEntity<>(new ResponseTo(HttpStatus.OK.value(), Constants.SUCCESS, productService.create(productDto)),
				Headers.getHeader(), HttpStatus.CREATED);
	}

	@PostMapping("/upload-image")
	public ResponseEntity<ResponseTo> uploadProductImage(@RequestParam("image") MultipartFile multiPartFile, @RequestParam("id")int id) throws IOException {
		productService.uploadProductImage(multiPartFile,id);
		return new ResponseEntity<>(new ResponseTo(HttpStatus.OK.value(), Constants.SUCCESS, Constants.SUCCESS),
				Headers.getHeader(), HttpStatus.CREATED);
	}
}
