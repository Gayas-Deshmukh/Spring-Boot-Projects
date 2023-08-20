package com.blog.api.app.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PostPaginationResponse 
{
	 private List<PostDTO> content;
	 private int  pageNumber;
	 private int  pageSize;
	 private long  totalElements;
	 private int  totalPages;
	 private boolean isLastPage; 
}
