package com.blog.api.app.exceptions;

public class ResourceNotFoundException  extends RuntimeException
{
	String resourceName;
	String fieldName;
	long fieldValue;
	
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) 
	{
		super(String.format("%s with %s %d is not found", resourceName, fieldName, fieldValue));
		this.resourceName 	= 	resourceName;
		this.fieldName 		=	fieldName;
		this.fieldValue 	= 	fieldValue;
	}
	
}
