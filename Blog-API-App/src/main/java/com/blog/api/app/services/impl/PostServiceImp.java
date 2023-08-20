package com.blog.api.app.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.blog.api.app.entities.Category;
import com.blog.api.app.entities.Post;
import com.blog.api.app.entities.User;
import com.blog.api.app.exceptions.ResourceNotFoundException;
import com.blog.api.app.payloads.CategoryDTO;
import com.blog.api.app.payloads.PostDTO;
import com.blog.api.app.payloads.PostPaginationResponse;
import com.blog.api.app.payloads.UserDTO;
import com.blog.api.app.repository.PostRepo;
import com.blog.api.app.services.CategoryService;
import com.blog.api.app.services.PostService;
import com.blog.api.app.services.UserService;

@Service
public class PostServiceImp implements PostService
{
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;

	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) 
	{
		User 		user 		=	this.modelMapper.map(this.userService.getUserByID(userId), User.class);
		Category	category	=	this.modelMapper.map(this.categoryService.getCategoryByID(categoryId), Category.class);
		
		Post post = this.modelMapper.map(postDTO, Post.class);
		
		post.setCategory(category);
		post.setUser(user);
		post.setImageName("Default.png");
		post.setPostAddedDate(new Date());
		
		Post newPost = this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) 
	{
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id",  postId));

		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImageName(postDTO.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDTO.class);	
	}

	@Override
	public void deletePost(Integer postId) 
	{
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id",  postId));
		
		this.postRepo.delete(post);
	}

	@Override
	public List<PostDTO> getAllPost() 
	{
		List<Post> 		posts 		=	this.postRepo.findAll();		
		List<PostDTO>	postList	= 	posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

		return postList;
	}
	
	@Override
	public PostPaginationResponse getAllPostWithPagination(Integer pageNum, Integer pageSize, String sortBy, String sortDir) 
	{
		Sort sort = null;
		
		if (sortDir.equalsIgnoreCase("asc"))
		{
			sort = Sort.by(Direction.ASC, sortBy);
		}
		else if (sortDir.equalsIgnoreCase("desc"))
		{
			sort = Sort.by(Direction.DESC, sortBy);
		}
		
		Pageable 		page 		= 	PageRequest.of(pageNum, pageSize, sort);
		Page<Post> 		allPosts 	= 	this.postRepo.findAll(page);		
		List<Post> 		posts 		= 	allPosts.getContent();
		List<PostDTO>	postList	= 	posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

		PostPaginationResponse	response	=	new PostPaginationResponse();
		
		response.setContent(postList);
		response.setPageNumber(allPosts.getNumber());
		response.setPageSize(allPosts.getSize());
		response.setTotalElements(allPosts.getTotalElements());
		response.setTotalPages(allPosts.getTotalPages());
		response.setLastPage(allPosts.isLast());
		
		return response;
	}

	@Override
	public PostDTO getPostById(Integer postId) 
	{
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id",  postId));
		
		return this.modelMapper.map(post, PostDTO.class);
	}

	@Override
	public List<PostDTO> getPostByCategory(Integer catgoryId) 
	{
		Category		category	=	this.modelMapper.map(this.categoryService.getCategoryByID(catgoryId), Category.class);
		List<Post> 		listOfPost 	= 	this.postRepo.findByCategory(category);
		List<PostDTO>	posts 		= 	listOfPost.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		
		return posts;
	}

	@Override
	public List<PostDTO> getPostByUser(Integer userId) 
	{
		User 			user 		=	this.modelMapper.map(this.userService.getUserByID(userId), User.class);
		List<Post> 		listOfPost 	= 	this.postRepo.findByUser(user);
		List<PostDTO>	posts 		= 	listOfPost.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
	
		return posts;
	}

	@Override
	public List<PostDTO> searchPosts(String searchKeyword) 
	{
		//List<Post> 		posts 			=	this.postRepo.searchByTitle(searchKeyword);
		// OR
		List<Post> 		posts 			=	this.postRepo.findByTitleContaining(searchKeyword);
		List<PostDTO>	requiredposts	= 	posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

		return requiredposts;
	}
}
