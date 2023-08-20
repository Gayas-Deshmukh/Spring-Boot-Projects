package com.blog.api.app.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 1. Validation related annotations added here are coming from JPA
 * 2. In Spring boot for those annotations support has been given
 * 3. We will need to add required dependency for this annotations.
 * 4. To enable this validation we will need to use @Valid annotation at the place where this class/Bean is used to accept data from end user. 
 */


@NoArgsConstructor
@Getter
@Setter
public class UserDTO 
{
	private int id;	
	
	@NotEmpty
	@Size(min = 4, message = "Name must be of minimun 4 chars")
	private String name;
	
	@Email(message = "Email address is not valid")
	private String email;
	
	@NotEmpty
	@Size(min = 4, max = 6 , message = "Password must be min of 4 chars and max of 6 chars.")
	private String password;
	
	@NotEmpty
	private String about;
}
