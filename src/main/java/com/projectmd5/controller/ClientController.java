package com.projectmd5.controller;

import com.projectmd5.model.dto.MessageResponse;
import com.projectmd5.model.dto.category.ListCateResponse;
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

import static com.projectmd5.constants.MessageConstant.PRODUCT_NOT_FOUND;
import static com.projectmd5.constants.PathConstant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1)
public class ClientController {
   private final ICategoryService categoryService;
   private final IProductService productService;
   @GetMapping(CATEGORIES)
   public ResponseEntity<?> getCategories(){
      List<Category> categories = categoryService.findAllActive();
      ListCateResponse response = ListCateResponse.builder()
            .data(categories)
            .totalElements(categories.size())
            .build();
      return ResponseEntity.ok().body(response);
   }

   // Danh sách sản phẩm được bán, tim kiếm theo tên sản phẩm hoặc tên mô tả
   @GetMapping(PRODUCTS)
   public ResponseEntity<?> getPublishProduct(
         @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
         @RequestParam(name = "sortBy", defaultValue = "productId", required = false) String sortBy,
         @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir,
         @RequestParam(name = "search", defaultValue = "", required = false) String query
   ){
      if (query.isEmpty()){
         ProPageResponse response = productService.getAllPublishWithPaging(pageNo, pageSize, sortBy, sortDir);
         return ResponseEntity.ok().body(response);
      }
      ProPageResponse response = productService.searchByNameOrDescription(query ,pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok().body(response);
   }

   @GetMapping(PRODUCT_ID)
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
   public ResponseEntity<?> getNewProducts(
         @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
         @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
   ){
      ProPageResponse response = productService.getAllPublishWithPaging(pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok().body(response);
   }

   // Tìm các sản phẩm theo category
   @GetMapping("/products/categories/{categoryId}")
   public ResponseEntity<?> getProductByCategory(
         @PathVariable Long categoryId,
         @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
         @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
   ){
      ProPageResponse response = productService.searchAllByCategory(categoryId, pageNo, pageSize, sortBy, sortDir);
      if (response.getData().isEmpty()){
         return new ResponseEntity<>(
               new MessageResponse(PRODUCT_NOT_FOUND),
               HttpStatus.NOT_FOUND);
      }
      return ResponseEntity.ok().body(response);
   }
}
