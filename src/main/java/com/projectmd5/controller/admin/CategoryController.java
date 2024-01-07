package com.projectmd5.controller.admin;

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
         @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(value = "sortBy", defaultValue = "categoryId", required = false) String sortBy,
         @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
   ){
      CatPageResponse response = categoryService.getAll(pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok(response);
   }

   @GetMapping(CATEGORY_ID)
   public ResponseEntity<?> getCategory(@PathVariable Long categoryId){
      Category cate = categoryService.findById(categoryId);
      return ResponseEntity.ok(cate);
   }
   @PostMapping
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
   public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryRequest cateDTO){
      Category cateUpdate = categoryService.update(categoryId, cateDTO);
      return ResponseEntity.ok(cateUpdate);
   }

   @DeleteMapping(CATEGORY_ID)
   public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
      categoryService.delete(categoryId);
      return ResponseEntity.ok(DELETE_SUCCESS);
   }
}
