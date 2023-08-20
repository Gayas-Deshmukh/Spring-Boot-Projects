package com.blog.api.app.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO 
{
	private int id;
	
	@NotEmpty
	@Size(min = 3 , message = "Category title should be more than 3 chars")
	private String categoryTilte;
	
	@NotEmpty(message = "Description should not be empty")
	private String categoryDescription;
}
