package com.blog.api.app.exceptions;

public class JwtException extends RuntimeException
{
	public JwtException(String errorMsg) 
	{
		super(errorMsg);
	}
}
