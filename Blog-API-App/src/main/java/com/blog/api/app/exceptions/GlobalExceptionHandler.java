package com.blog.api.app.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.api.app.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getMessage(), true), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		Map<String, String> errorMsg = new LinkedHashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String field = ((FieldError)error).getField();
			String msg	 = error.getDefaultMessage();
			
			errorMsg.put(field, msg);
		});
		
		return new ResponseEntity<Map<String, String>>(errorMsg, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ApiResponse> handleJwtException(JwtException ex)
	{
		return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getMessage(), false), HttpStatus.NOT_FOUND);
	}
}
