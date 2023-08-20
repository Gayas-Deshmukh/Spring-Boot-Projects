package com.blog.api.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.app.payloads.ApiResponse;
import com.blog.api.app.payloads.UserDTO;
import com.blog.api.app.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController 
{
	@Autowired
	private UserService userService;
	
	// Get All Users
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers()
	{
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	// Get Single User
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Integer uId)
	{
		return ResponseEntity.ok(this.userService.getUserByID(uId));
	}
	
	// Create new user
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto)
	{		
		UserDTO createdUser = this.userService.createUser(userDto);
		
		return new ResponseEntity<UserDTO>(createdUser, HttpStatus.CREATED);
	}
	
	// Update existing user details
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDto, @PathVariable("userId") Integer uId)
	{		
		return ResponseEntity.ok(this.userService.updateUser(userDto, uId));
	}
	
	// Delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uId)
	{
		this.userService.deleteUser(uId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully deleted user with id " + uId, true), HttpStatus.OK);
	}
	
}
