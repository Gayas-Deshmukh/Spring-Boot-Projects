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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.app.payloads.ApiResponse;
import com.blog.api.app.payloads.PostDTO;
import com.blog.api.app.payloads.PostPaginationResponse;
import com.blog.api.app.services.PostService;

@RestController
public class PostController 
{
	@Autowired
	private PostService postService;
	
	// get post by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable("userId") Integer userId)
	{
		List<PostDTO> posts = this.postService.getPostByUser(userId);
		
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}
	
	// get post by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable("categoryId") Integer categoryId)
	{
		List<PostDTO> posts = this.postService.getPostByCategory(categoryId);
		
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}
	
	// Get All posts
	@GetMapping("/posts")
	public ResponseEntity<List<PostDTO>> getAllPosts()
	{
		List<PostDTO> allPost = this.postService.getAllPost();
		
		return new ResponseEntity<List<PostDTO>>(allPost, HttpStatus.OK);
	}
	
	// Get Post By Id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable("postId") Integer postId)
	{
		 PostDTO post = this.postService.getPostById(postId);
		
		return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
	}
	
	// Get Posts with Page Number & Size
	@GetMapping("/posts/p")
	public  ResponseEntity<PostPaginationResponse> getPostsWithPegination(@RequestParam(name = "pageNum", defaultValue = "0", required = false) Integer pageNum,
																 @RequestParam(name = "pageSize", defaultValue = "2", required = false) Integer pageSize,
																 @RequestParam(name = "sortBy", defaultValue = "postId", required = false) String sortBy,
																 @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir)
	{
		PostPaginationResponse response  = this.postService.getAllPostWithPagination(pageNum, pageSize, sortBy, sortDir);
		 
		return new ResponseEntity<PostPaginationResponse>(response, HttpStatus.OK);
	}
	
	// Create the post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO , @PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId)
	{
		PostDTO savedPost = this.postService.createPost(postDTO, userId, categoryId);
		
		return new ResponseEntity<PostDTO>(savedPost, HttpStatus.CREATED);
	}
	
	// Update Post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable("postId") Integer postId)
	{
		PostDTO updatePost = this.postService.updatePost(postDTO, postId);
		
		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
	}
	
	// Delete Post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId)
	{
		this.postService.deletePost(postId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully deleted post with id " + postId, true), HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDTO>> searPost(@PathVariable("keyword") String keyword)
	{
		List<PostDTO> posts = this.postService.searchPosts(keyword);
		
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}
	
}
