package com.blog.api.app.services;

import java.util.List;

import com.blog.api.app.payloads.UserDTO;

public interface UserService 
{
	UserDTO createUser(UserDTO userDto);
	UserDTO updateUser(UserDTO userDto , Integer userId);
	UserDTO getUserByID(Integer userID);
	List<UserDTO>getAllUsers();
	void deleteUser(Integer userId);
}
