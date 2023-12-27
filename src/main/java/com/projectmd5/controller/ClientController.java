package com.projectmd5.controller;

import com.projectmd5.model.entity.Category;
import com.projectmd5.service.ICategoryService;
import com.projectmd5.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.myservice.com/v1")
public class ClientController {
   private final ICategoryService categoryService;
   private final IProductService productService;
   @GetMapping("/categories")
   public ResponseEntity<?> getCategories(){
      List<Category> categories = categoryService.findByStatusTrue();
      return ResponseEntity.ok().body(categories);
   }
}
