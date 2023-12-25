package com.projectmd5.service;

import com.projectmd5.model.dto.request.CategoryDTO;
import com.projectmd5.model.dto.response.CategoryResponse;
import com.projectmd5.model.entity.Category;

public interface ICategoryService extends IGenericService<Category, Long> {
   CategoryResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir);
   Category create(CategoryDTO cateDTO);
   Category edit(CategoryDTO cateDTO, Long categoryId);
   boolean existCategoryName(Long id, String name);
}
