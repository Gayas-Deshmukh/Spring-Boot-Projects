package com.blog.api.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.app.payloads.ApiResponse;
import com.blog.api.app.payloads.CommentDTO;
import com.blog.api.app.services.CommentService;

@RestController
public class CommentController 
{
	@Autowired
	private CommentService commentService;
	
	// Create Comment
	@PostMapping("/comments/{postId}")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable("postId") Integer postId)
	{
		CommentDTO createdComment = this.commentService.createComment(commentDTO, postId);
		
		return new ResponseEntity<CommentDTO>(createdComment, HttpStatus.CREATED);
	}
	
	// Delete Comment
	@DeleteMapping("comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId)
	{
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment with id " + commentId + " deleted successfully", true), HttpStatus.OK);
	}
}
