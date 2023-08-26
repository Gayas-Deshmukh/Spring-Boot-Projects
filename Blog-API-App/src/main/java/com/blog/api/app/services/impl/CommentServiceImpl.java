package com.blog.api.app.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.app.entities.Comment;
import com.blog.api.app.entities.Post;
import com.blog.api.app.exceptions.ResourceNotFoundException;
import com.blog.api.app.payloads.CommentDTO;
import com.blog.api.app.repository.CommentRepo;
import com.blog.api.app.repository.PostRepo;
import com.blog.api.app.services.CommentService;
import com.blog.api.app.services.PostService;

@Service
public class CommentServiceImpl implements CommentService
{
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId) 
	{
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id",  postId));

		Comment comment = this.modelMapper.map(commentDTO, Comment.class);
		
		comment.setPost(post);
		
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) 
	{
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id",  commentId));
		
		this.commentRepo.delete(comment);
	}
	
}
