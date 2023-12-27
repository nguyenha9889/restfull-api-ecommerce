package com.projectmd5.controller.admin;

import com.projectmd5.exception.BadRequestException;
import com.projectmd5.model.dto.product.BaseProductResponse;
import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.Product;
import com.projectmd5.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api.myservice.com/v1/admin/products")
public class ProductController {

   private final IProductService productService;
   private final ModelMapper modelMapper;

   @GetMapping
   public ResponseEntity<?> getList(
         @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(value = "sortBy", defaultValue = "productId", required = false) String sortBy,
         @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
   ){
      Pageable pageable = productService.getPageable(pageNo, pageSize, sortBy, sortDir);
      ProPageResponse response = productService.getAll(pageable);
      return ResponseEntity.ok(response);
   }

   @GetMapping("/{productId}")
   public ResponseEntity<?> getProduct(@PathVariable Long productId){
      Product pro = productService.findById(productId);
      BaseProductResponse response = modelMapper.map(pro, BaseProductResponse.class);
      return ResponseEntity.ok(response);
   }
   @PostMapping
   public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest proRequest) {
      if (productService.existProductName(null, proRequest.getProductName())) {
         throw new BadRequestException("Product name is existed!");
      }

      BaseProductResponse response = productService.add(proRequest);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
   }

   @PutMapping("/{productId}")
   public ResponseEntity<?> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductRequest proRequest){

      BaseProductResponse response = productService.update(productId,proRequest);
      return ResponseEntity.ok(response);
   }

   @DeleteMapping("/{productId}")
   public ResponseEntity<?> deleteCategory(@PathVariable Long productId){
      productService.delete(productId);
      return ResponseEntity.ok("Product deleted successfully!");
   }
}
