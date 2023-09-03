package com.blog.api.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ContentConfig implements WebMvcConfigurer
{
	
	/**
	 * Request to get data in specific format
	 * 
	 * 1. To get data in JSON format
	 * http://localhost:8084/api/users/?contentType=json
	 * 
	 * 2. To get data in XML format
	 * http://localhost:8084/api/users/?contentType=xml
	 */
	
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) 
	{
		configurer.favorParameter(true)
					.parameterName("contentType")
					.defaultContentType(MediaType.APPLICATION_JSON)
					.mediaType("json", MediaType.APPLICATION_JSON)
					.mediaType("xml", MediaType.APPLICATION_XML);
	}
}
