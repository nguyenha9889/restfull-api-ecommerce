package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.request.CategoryDTO;
import com.projectmd5.model.dto.response.CategoryResponse;
import com.projectmd5.model.entity.Category;
import com.projectmd5.repository.ICategoryRepository;
import com.projectmd5.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
   private final ICategoryRepository categoryRepository;
   private final ModelMapper modelMapper;

   @Override
   public Category findById(Long id) {
      return categoryRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Category not found with id " + id)
      );
   }

   @Override
   public CategoryResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      Page<Category> pages = categoryRepository.findAll(pageable);
      List<Category> data = pages.getContent();

      List<CategoryDTO> dataDTO = data.stream().map(
            cate -> modelMapper.map(cate, CategoryDTO.class)).toList();

      return CategoryResponse.builder()
            .data(dataDTO)
            .pageNo(pageNo)
            .pageSize(pageSize)
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   @Override
   public void save(Category category) {
      categoryRepository.save(category);
   }

   @Override
   public void delete(Long id) {
      categoryRepository.delete(findById(id));
   }

   @Override
   public Category create(CategoryDTO categoryDTO) {
      if (categoryDTO.getStatus() == null){
         categoryDTO.setStatus(true);
      }

      return modelMapper.map(categoryDTO, Category.class);
   }

   @Override
   public Category edit(CategoryDTO categoryDTO, Long categoryId) {
      Category cate = findById(categoryId);
      cate.setCategoryName(categoryDTO.getCategoryName());
      cate.setDescription(categoryDTO.getDescription());

      if (categoryDTO.getStatus() != null){
         cate.setStatus(categoryDTO.getStatus());
      }

      return cate;
   }

   @Override
   public boolean existByCategoryName(String name) {
      return categoryRepository.existsByCategoryNameEqualsIgnoreCase(name.trim());
   }
}
