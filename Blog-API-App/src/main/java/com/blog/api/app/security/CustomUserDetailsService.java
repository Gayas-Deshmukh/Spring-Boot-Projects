package com.blog.api.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.blog.api.app.entities.User;
import com.blog.api.app.exceptions.ResourceNotFoundException;
import com.blog.api.app.repository.UserRepo;


@Service
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User	user	=	this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", " email", 0));
		
		return user;
	}

}
