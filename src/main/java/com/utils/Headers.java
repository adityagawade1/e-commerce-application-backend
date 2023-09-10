package com.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;

public class Headers {

	
	private final List<String> allowedOrigins = Arrays.asList("http://localhost:3000", "https://172.31.31.3",
			"http://20.219.137.179", "https://172.31.31.23", "https://14.139.108.234");

	
	public static HttpHeaders getHeader() {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		
		//httpHeaders.add("Access-Control-Allow-Origin", "http://localhost:3000");
//		httpHeaders.add("Vary", "Origin");
//
//		// Access-Control-Max-Age
//		httpHeaders.add("Access-Control-Max-Age", "3600");
//
//		// Access-Control-Allow-Credentials
//		httpHeaders.add("Access-Control-Allow-Credentials", "true");
//
//		// Access-Control-Allow-Methods
//		httpHeaders.add("Access-Control-Allow-Methods", "POST,PUT,PATCH, GET,DELETE");

		// Access-Control-Allow-Headers
		httpHeaders.add("Access-Control-Allow-Headers",
				"Origin, Authorization, myheader, X-Requested-By,X-query,refreshtoken,token, X-Requested-With, Content-Type, Accept, "
						+ "X-CSRF-TOKEN");
		
		return httpHeaders;
	}
}
