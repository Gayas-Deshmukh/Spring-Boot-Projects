package com.blog.api.app.payloads;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDTO 
{
	private int id;
	private String content;
}
