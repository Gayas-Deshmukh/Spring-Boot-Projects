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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blog.api.app.payloads.ApiResponse;
import com.blog.api.app.payloads.CategoryDTO;
import com.blog.api.app.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/categories")
public class CategoryController 
{
	@Autowired
	private CategoryService categoryService;
	
	// Get All Categories
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllCategories()
	{
		return ResponseEntity.ok(this.categoryService.getAllCategories());
	}
	
	// Get Single Category
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable("categoryId") Integer categoryId)
	{
		return ResponseEntity.ok(this.categoryService.getCategoryByID(categoryId));
	}
	
	// Create new Category
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO)
	{		
		CategoryDTO category = this.categoryService.createCategory(categoryDTO);
		
		return new ResponseEntity<CategoryDTO>(category, HttpStatus.CREATED);
	}
	
	// Update existing Category details
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable("categoryId") Integer categoryId)
	{		
		return ResponseEntity.ok(this.categoryService.updateCategory(categoryDTO, categoryId));
	}
	
	// Delete Category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("categoryId") Integer categoryId)
	{
		this.categoryService.deleteCategory(categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully deleted Category with id : " + categoryId, true), HttpStatus.OK);
	}
}
