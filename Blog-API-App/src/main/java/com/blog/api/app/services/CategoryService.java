package com.blog.api.app.services;

import java.util.List;
import com.blog.api.app.payloads.CategoryDTO;

public interface CategoryService
{
	CategoryDTO createCategory(CategoryDTO categoryDto);
	CategoryDTO updateCategory(CategoryDTO categoryDto , Integer categoryId);
	CategoryDTO getCategoryByID(Integer categoryId);
	List<CategoryDTO>getAllCategories();
	void deleteCategory(Integer categoryId);
}
