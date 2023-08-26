package com.blog.api.app.services;

import com.blog.api.app.payloads.CommentDTO;

public interface CommentService 
{
	CommentDTO createComment(CommentDTO commentDTO, Integer postId);
	void deleteComment (Integer commentId);
}
