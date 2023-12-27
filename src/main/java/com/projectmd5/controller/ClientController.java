package com.projectmd5.controller;

import com.projectmd5.exception.BadRequestException;
import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.entity.Category;
import com.projectmd5.model.entity.Product;
import com.projectmd5.service.ICategoryService;
import com.projectmd5.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

   @GetMapping("/products/{productId}")
   public ResponseEntity<?> getProduct(@PathVariable Long productId){
      Product product = productService.findById(productId);
      if (!product.getCategory().isStatus()){
         throw new BadRequestException("This product belongs to the category that is currently inactive");
      }
      return ResponseEntity.ok().body(product);
   }

   @GetMapping("/products/search")
   public ResponseEntity<?> searchProduct(@RequestParam String query){
      List<Product> products = null;
      if (query.trim().isEmpty()){
         products = productService.findAll();
      } else {
         products = productService.findByNameOrDescription(query, query);
      }
      return ResponseEntity.ok().body(products);
   }

   // Danh sách sản phẩm được bán
   @GetMapping("/products")
   public ResponseEntity<?> getPublishProduct(
         @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(value = "sortBy", defaultValue = "productId", required = false) String sortBy,
         @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
   ){
      Pageable pageable = productService.getPageable(pageNo, pageSize, sortBy, sortDir);
      ProPageResponse response = productService.getAllPublishWithPaging(pageable);
      return ResponseEntity.ok().body(response);
   }

   // Danh sách sản phẩm mới nhất
   @GetMapping("/products")
   public ResponseEntity<?> getNewProducts(){
      return ResponseEntity.ok().body(productService.getAllNewCreated());
   }

   // Tìm các sản phẩm theo category
   @GetMapping("/products/categories/{categoryId}")
   public ResponseEntity<?> getProductByCategory(@PathVariable Long categoryId){
      List<Product> products = productService.getAllByCategoryId(categoryId);
      return ResponseEntity.ok().body(products);
   }
}
