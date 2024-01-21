package com.projectmd5.service;

import com.projectmd5.model.dto.category.CategoryRequest;
import com.projectmd5.model.dto.category.CatPageResponse;
import com.projectmd5.model.entity.Category;

import java.util.List;

public interface ICategoryService extends IGenericService<Category, Long> {
   CatPageResponse getAllWithPaging(int pageNo, int pageSize, String sortBy, String sortDir);
   List<Category> findAllActive();
   Category add(CategoryRequest cateRequest);
   Category update(Category category, CategoryRequest cateRequest);
   boolean existCategoryName(Long id, String name);
   CatPageResponse search(String name, int pageNo, int pageSize, String sortBy, String sortDir);
}
