package com.blog.api.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.app.entities.Category;
import com.blog.api.app.exceptions.ResourceNotFoundException;
import com.blog.api.app.payloads.CategoryDTO;
import com.blog.api.app.repository.CategoryRepo;
import com.blog.api.app.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService
{
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDto) 
	{
		Category category = this.categoryRepo.save(this.modelMapper.map(categoryDto, Category.class));
		
		return this.modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDto, Integer categoryId) 
	{

		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		
		category.setCategoryTilte(categoryDto.getCategoryTilte());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.categoryRepo.save(category);
			
		return this.modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO getCategoryByID(Integer categoryId) 
	{
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

		return this.modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategories() 
	{
		List<Category> 		categories 		= 	this.categoryRepo.findAll();	
		List<CategoryDTO>	categoriesDtos	=	categories.stream().map(category -> this.modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
		
		return categoriesDtos;
	}

	@Override
	public void deleteCategory(Integer categoryId) 
	{
		Category	category	=	this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

		this.categoryRepo.delete(category);
	}

}
