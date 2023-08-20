package com.blog.api.app.payloads;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO
{
	private int postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date postAddedDate;
	
	private CategoryDTO category;
	
	private UserDTO user;
}
