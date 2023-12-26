package com.projectmd5.service;

import com.projectmd5.model.dto.category.CategoryRequest;
import com.projectmd5.model.dto.category.CatPageResponse;
import com.projectmd5.model.entity.Category;

public interface ICategoryService extends IGenericService<Category, Long> {
   CatPageResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir);
   Category add(CategoryRequest cateRequest);
   Category update(Long categoryId, CategoryRequest cateRequest);
   boolean existCategoryName(Long id, String name);
}
