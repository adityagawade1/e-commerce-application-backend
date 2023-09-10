package com;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import com.service.RoleService;
import com.utils.LOG;

@SpringBootApplication
public class ECommerceApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ECommerceApplication.class);
	}

	
	@Autowired
	private RoleService roleService;
	
	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() {

		LOG.getInstance();
		
		if(roleService.getRoles().isEmpty()) {
			roleService.saveRole();
		}
		
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
