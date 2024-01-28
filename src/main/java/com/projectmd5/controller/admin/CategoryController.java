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
import java.util.Objects;

import static com.projectmd5.constants.MessageConstant.DELETE_SUCCESS;
import static com.projectmd5.constants.PathConstant.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_V1_ADMIN)
public class CategoryController {

   private final ICategoryService categoryService;
   private final CategoryValidator validator;

   @GetMapping(CATEGORIES)
   public ResponseEntity<?> getList(
         @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(name = "sortBy", defaultValue = "categoryId", required = false) String sortBy,
         @RequestParam(name = "sortDir", defaultValue = "desc", required = false) String sortDir,
         @RequestParam(name = "search", defaultValue = "", required = false) String name
   ){
      CatPageResponse catPageResponse = null;
      if (name.isBlank()){
         if (pageSize == 1) {
            catPageResponse = categoryService.getAllWithPaging(pageNo, categoryService.findAll().size(), sortBy, sortDir);
         } else {
            catPageResponse = categoryService.getAllWithPaging(pageNo, pageSize, sortBy, sortDir);
         }
         return ResponseEntity.ok(catPageResponse);
      }
      catPageResponse = categoryService.search(name, pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok(catPageResponse);
   }

   @GetMapping(CATEGORY_ID)
   public ResponseEntity<?> getCategory(@PathVariable Long categoryId){
      Category cate = categoryService.findById(categoryId);
      return ResponseEntity.ok(cate);
   }
   @PostMapping(CATEGORIES)
   public ResponseEntity<?> addCategory(@ModelAttribute CategoryRequest cateRequest,
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
   public ResponseEntity<?> updateCategory(@PathVariable Long categoryId,
                                           @ModelAttribute CategoryRequest cateRequest,
                                           BindingResult bindingResult){
      validator.validate(cateRequest, bindingResult);
      if (bindingResult.hasErrors()){
         Map<String, String> errors = new HashMap<>();
         bindingResult.getFieldErrors().forEach(err ->
               errors.put(err.getField(), err.getCode()));
         return ResponseEntity.badRequest().body(errors);
      }

      Category cate = categoryService.findById(categoryId);
      Category cateUpdate = categoryService.update(cate, cateRequest);
      return ResponseEntity.ok(cateUpdate);
   }

   @DeleteMapping(CATEGORY_ID)
   public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
      Category cate = categoryService.findById(categoryId);
      categoryService.delete(cate);
      return ResponseEntity.ok(DELETE_SUCCESS);
   }
}
