package com.blog.api.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.app.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>
{

}
