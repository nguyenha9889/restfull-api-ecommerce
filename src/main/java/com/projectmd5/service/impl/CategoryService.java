package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.category.CatPageResponse;
import com.projectmd5.model.dto.category.CategoryRequest;
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
import java.util.Objects;

import static com.projectmd5.constants.MessageConstant.CATEGORY_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
   private final ICategoryRepository categoryRepository;
   private final ModelMapper modelMapper;

   @Override
   public List<Category> findAll() {
      return categoryRepository.findAll();
   }

   @Override
   public Category findById(Long id) {
      return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND));
   }

   @Override
   public CatPageResponse getAllWithPaging(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      Page<Category> pages = categoryRepository.findAll(pageable);
      List<Category> data = pages.getContent();

      return CatPageResponse.builder()
            .data(data)
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
   public void delete(Category category) {
      categoryRepository.delete(category);
   }

   @Override
   public Category add(CategoryRequest categoryRequest) {
      Category category = modelMapper.map(categoryRequest, Category.class);
      category.setStatus(true);
      categoryRepository.save(category);
      return category;
   }

   @Override
   public Category update(Category cate, CategoryRequest categoryRequest) {
      cate.setCategoryName(categoryRequest.getCategoryName());
      cate.setDescription(categoryRequest.getDescription());

      if (categoryRequest.getStatus() != null){
         cate.setStatus(categoryRequest.getStatus());
      }
      categoryRepository.save(cate);
      return cate;
   }

   @Override
   public boolean existCategoryName(Long id, String name) {
      for (Category c: findAll()) {
         if (c.getCategoryName().equalsIgnoreCase(name.trim())) {
            return !Objects.equals(c.getCategoryId(), id);
         }
      }
      return false;
   }

   @Override
   public CatPageResponse search(String name, int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      Page<Category> pages = categoryRepository.findAllByCategoryNameContainingIgnoreCase(name, pageable);
      List<Category> data = pages.getContent();
      return CatPageResponse.builder()
            .data(data)
            .pageNo(pageNo)
            .pageSize(pageSize)
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   //=======================================CLIENT=================================================
   @Override
   public List<Category> findAllPublish(){
      List<Category> activeList = categoryRepository.findAllByStatusIsTrue();
      return activeList.stream().filter(c -> !c.getProducts().isEmpty()).toList();
   }
}
