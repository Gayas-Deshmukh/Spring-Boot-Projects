package com.blog.api.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.app.payloads.JwtAuthRequest;
import com.blog.api.app.payloads.JwtAuthResponse;
import com.blog.api.app.security.JwtTokenHelper;

import io.jsonwebtoken.JwtException;

@RestController
@RequestMapping("api/auth")
public class AuthController 
{
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request)
	{
		this.authenticate(request.getUsername(), request.getPassword());
		
		String token = this.jwtTokenHelper.generateToken(this.userDetailsService.loadUserByUsername(request.getUsername()));
	
		JwtAuthResponse authResponse = new JwtAuthResponse();
		
		authResponse.setToken(token);
		
	    return new ResponseEntity<JwtAuthResponse>(authResponse, HttpStatus.OK);
	}

	private void authenticate(String username, String password) 
	{
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		try
		{
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		}
		catch (DisabledException e) 
		{
			throw new JwtException("User is disabled");
		}
		
	}
}
