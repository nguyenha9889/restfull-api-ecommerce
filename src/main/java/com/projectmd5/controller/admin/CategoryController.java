package com.projectmd5.controller.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projectmd5.model.dto.MessageResponse;
import com.projectmd5.model.dto.category.CatPageResponse;
import com.projectmd5.model.dto.category.CategoryRequest;
import com.projectmd5.model.entity.Category;
import com.projectmd5.service.ICategoryService;
import com.projectmd5.validation.CategoryValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.projectmd5.constants.MessageConstant.CATEGORY_NOT_FOUND;
import static com.projectmd5.constants.PathConstant.*;
import static com.projectmd5.constants.MessageConstant.DELETE_SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_V1_ADMIN)
public class CategoryController {

   private final ICategoryService categoryService;
   private final CategoryValidator validator;

   @GetMapping(CATEGORIES)
   public ResponseEntity<?> getList(
         @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
         @RequestParam(name = "sortBy", defaultValue = "categoryId", required = false) String sortBy,
         @RequestParam(name = "sortDir", defaultValue = "dsc", required = false) String sortDir,
         @RequestParam(name = "categoryName", defaultValue = "", required = false) String name
   ){
      CatPageResponse catPageResponse = null;
      if (Objects.equals(name, "") || name.isBlank()){
         catPageResponse = categoryService.getAll(pageNo, pageSize, sortBy, sortDir);
         return ResponseEntity.ok(catPageResponse);
      }
      catPageResponse = categoryService.search(name, pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok(catPageResponse);
   }

   @GetMapping(CATEGORY_ID)
   public ResponseEntity<?> getCategory(@PathVariable String categoryId){
      Category cate = categoryService.findById(Long.parseLong(categoryId));
      if (cate == null){
         return new ResponseEntity<>(
               new MessageResponse(CATEGORY_NOT_FOUND),
               HttpStatus.NOT_FOUND);
      }
      return ResponseEntity.ok(cate);
   }
   @PostMapping(CATEGORIES)
   public ResponseEntity<?> addCategory(@RequestBody CategoryRequest cateRequest,
                                        BindingResult bindingResult) {
      validator.validate(cateRequest, bindingResult);

      if (bindingResult.hasErrors()){
         Map<String, String> errors = new HashMap<>();
         bindingResult.getFieldErrors().forEach(err ->
               errors.put(err.getField(), err.getCode()));
         return ResponseEntity.badRequest().body(errors);
      }

      Category category = categoryService.add(cateRequest);
      return new ResponseEntity<>(category, HttpStatus.CREATED);
   }

   @PutMapping(CATEGORY_ID)
   public ResponseEntity<?> updateCategory(@PathVariable String categoryId,
                                           @Valid @RequestBody CategoryRequest cateRequest,
                                           BindingResult bindingResult){
      validator.validate(cateRequest, bindingResult);
      if (bindingResult.hasErrors()){
         Map<String, String> errors = new HashMap<>();
         bindingResult.getFieldErrors().forEach(err ->
               errors.put(err.getField(), err.getCode()));
         return ResponseEntity.badRequest().body(errors);
      }

      Category cate = categoryService.findById(Long.parseLong(categoryId));
      if (cate == null){
         return new ResponseEntity<>(
               new MessageResponse(CATEGORY_NOT_FOUND),
               HttpStatus.NOT_FOUND);
      }

      Category cateUpdate = categoryService.update(Long.parseLong(categoryId), cateRequest);
      return ResponseEntity.ok(cateUpdate);
   }

   @DeleteMapping(CATEGORY_ID)
   public ResponseEntity<?> deleteCategory(@PathVariable String categoryId){
      Category cate = categoryService.findById(Long.parseLong(categoryId));
      if (cate == null){
         return new ResponseEntity<>(
               new MessageResponse(CATEGORY_NOT_FOUND),
               HttpStatus.NOT_FOUND);
      }
      categoryService.delete(Long.parseLong(categoryId));
      return ResponseEntity.ok(cate);
   }
}
