package com.blog.api.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.api.app.entities.Category;
import com.blog.api.app.entities.Post;
import com.blog.api.app.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>
{
	// These methods will automatically map to the attribute & will give you data
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String keyWords);
	
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String keyWords);
}
