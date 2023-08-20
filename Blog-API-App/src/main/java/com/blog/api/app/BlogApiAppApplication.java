package com.blog.api.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogApiAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApiAppApplication.class, args);
	}
	
	@Bean
	public ModelMapper getModelmapper()
	{
		return new ModelMapper();
	}

}
