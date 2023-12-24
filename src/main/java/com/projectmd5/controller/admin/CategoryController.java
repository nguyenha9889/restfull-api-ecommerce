package com.projectmd5.controller.admin;

import com.projectmd5.model.dto.request.CategoryDTO;
import com.projectmd5.model.dto.response.CategoryResponse;
import com.projectmd5.model.entity.Category;
import com.projectmd5.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api.myservice.com/v1/admin/categories")
public class CategoryController {

   private final ICategoryService categoryService;
   private final ModelMapper modelMapper;

   @GetMapping
   public ResponseEntity<?> getList(
         @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(value = "sortBy", defaultValue = "categoryId", required = false) String sortBy,
         @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
   ){
      CategoryResponse response = categoryService.getAll(pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok(response);
   }

   @GetMapping("/{categoryId}")
   public ResponseEntity<?> getCategory(@PathVariable Long categoryId){
      Category cate = categoryService.findById(categoryId);
      CategoryDTO cateDTO = modelMapper.map(cate, CategoryDTO.class);

      return ResponseEntity.ok(cateDTO);
   }
   @PostMapping
   public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO cateDTO){
      Category cate = categoryService.create(cateDTO);
      categoryService.save(cate);
      CategoryDTO cateDTONew = modelMapper.map(cate, CategoryDTO.class);

      return new ResponseEntity<>(cateDTONew, HttpStatus.CREATED);
   }

   @PutMapping("/{categoryId}")
   public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDTO cateDTO,
                                           @PathVariable Long categoryId){
      Category cateUpdate = categoryService.edit(cateDTO, categoryId);
      categoryService.save(cateUpdate);

      return ResponseEntity.ok(cateUpdate);
   }

   @DeleteMapping("/{categoryId}")
   public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
      categoryService.delete(categoryId);

      return ResponseEntity.ok("Category deleted successfully!");
   }
}
