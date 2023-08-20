package com.blog.api.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blog.api.app.entities.User;
import com.blog.api.app.exceptions.ResourceNotFoundException;
import com.blog.api.app.payloads.UserDTO;
import com.blog.api.app.repository.UserRepo;
import com.blog.api.app.services.UserService;


@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDTO createUser(UserDTO userDto) 
	{
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		
		return this.userToDTO(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userId) 
	{
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		UserDTO userDtoUpdated  = this.userToDTO(updatedUser);
		
		return userDtoUpdated;
	}

	@Override
	public UserDTO getUserByID(Integer userID) 
	{
		User user = this.userRepo.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
		return this.userToDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() 
	{
		List<User> users = this.userRepo.findAll();
		
		List<UserDTO> userDtos = users.stream().map(user -> this.userToDTO(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) 
	{
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id", userId));
		
		this.userRepo.delete(user);
	}
	
	private User dtoToUser(UserDTO userDTO)
	{
		User user = this.modelMapper.map(userDTO, User.class);
		
//		User user = new User();
//		
//		user.setId(userDTO.getId());
//		user.setName(userDTO.getName());
//		user.setEmail(userDTO.getEmail());
//		user.setPassword(userDTO.getPassword());
//		user.setAbout(userDTO.getAbout());
		
		return user;
	}
	
	private UserDTO userToDTO(User user)
	{
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);

//		UserDTO userDTO = new UserDTO();
//		
//		userDTO.setId(user.getId());
//		userDTO.setName(user.getName());
//		userDTO.setEmail(user.getEmail());
//		userDTO.setPassword(user.getPassword());
//		userDTO.setAbout(user.getAbout());
		
		return userDTO;
	}

}
