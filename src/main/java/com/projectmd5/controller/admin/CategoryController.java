package com.projectmd5.controller.admin;

import com.projectmd5.model.dto.category.CatPageResponse;
import com.projectmd5.model.dto.category.CategoryRequest;
import com.projectmd5.model.entity.Category;
import com.projectmd5.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api.myservice.com/v1/admin/categories")
public class CategoryController {

   private final ICategoryService categoryService;

   @GetMapping
   public ResponseEntity<?> getList(
         @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(value = "sortBy", defaultValue = "categoryId", required = false) String sortBy,
         @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
   ){
      CatPageResponse response = categoryService.getAll(pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok(response);
   }

   @GetMapping("/{categoryId}")
   public ResponseEntity<?> getCategory(@PathVariable Long categoryId){
      Category cate = categoryService.findById(categoryId);
      return ResponseEntity.ok(cate);
   }
   @PostMapping
   public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryRequest cateRequest){

      Category category = categoryService.add(cateRequest);
      return new ResponseEntity<>(category, HttpStatus.CREATED);
   }

   @PutMapping("/{categoryId}")
   public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryRequest cateDTO){
      Category cateUpdate = categoryService.update(categoryId, cateDTO);
      return ResponseEntity.ok(cateUpdate);
   }

   @DeleteMapping("/{categoryId}")
   public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
      categoryService.delete(categoryId);
      return ResponseEntity.ok("Category deleted successfully!");
   }
}
