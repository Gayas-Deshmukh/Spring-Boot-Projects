package com.blog.api.app.services;

import java.util.List;

import com.blog.api.app.payloads.PostDTO;
import com.blog.api.app.payloads.PostPaginationResponse;

public interface PostService 
{
	PostDTO createPost (PostDTO postDTO, Integer userId, Integer categoryId);
	PostDTO updatePost (PostDTO postDTO, Integer postId);
	void deletePost(Integer postId);
	List<PostDTO> getAllPost();
	PostDTO getPostById(Integer postId);
	List<PostDTO> getPostByCategory(Integer catgoryId);
	List<PostDTO> getPostByUser(Integer userId);
	List<PostDTO> searchPosts(String searchKeyword);
	PostPaginationResponse getAllPostWithPagination(Integer pageNum, Integer pageSize, String sortBy, String sortDir);
}
