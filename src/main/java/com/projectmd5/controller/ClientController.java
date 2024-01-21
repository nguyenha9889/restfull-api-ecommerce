package com.projectmd5.controller;

import com.projectmd5.model.dto.MessageResponse;
import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.entity.Category;
import com.projectmd5.model.entity.Product;
import com.projectmd5.service.ICategoryService;
import com.projectmd5.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
      List<Category> categories = categoryService.findAllActive();
      return ResponseEntity.ok().body(categories);
   }

   // Danh sách sản phẩm được bán
   @GetMapping("/products")
   public ResponseEntity<?> getPublishProduct(
         @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(name = "sortBy", defaultValue = "productId", required = false) String sortBy,
         @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
   ){

      ProPageResponse response = productService.getAllPublishWithPaging(pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok().body(response);
   }

   @GetMapping("/products/{productId}")
   public ResponseEntity<?> getProduct(@PathVariable Long productId){
      Product product = productService.findById(productId);
      if (!product.getCategory().isStatus()){
         return new ResponseEntity<>(
               new MessageResponse("This product belongs to the category that is currently inactive"),
               HttpStatus.NOT_ACCEPTABLE);
      }
      return ResponseEntity.ok().body(product);
   }

   // Danh sách sản phẩm mới nhất
   @GetMapping("/products/new-products")
   public ResponseEntity<?> getNewProducts(){
      return ResponseEntity.ok().body(productService.getCreatedList());
   }

   // Tìm các sản phẩm theo category
   @GetMapping("/products/categories/{categoryId}")
   public ResponseEntity<?> getProductByCategory(@PathVariable Long categoryId){
      List<Product> products = productService.getAllByCategoryActive(categoryId);
      return ResponseEntity.ok().body(products);
   }
}
