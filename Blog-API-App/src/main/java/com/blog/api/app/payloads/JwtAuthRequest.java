package com.blog.api.app.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest 
{
	private String username;
	
	private String password;
}
