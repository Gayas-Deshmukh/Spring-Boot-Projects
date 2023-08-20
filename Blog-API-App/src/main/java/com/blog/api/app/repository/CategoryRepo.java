package com.blog.api.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.api.app.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>
{

}
