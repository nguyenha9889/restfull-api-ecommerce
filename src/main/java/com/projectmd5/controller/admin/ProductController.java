package com.projectmd5.controller.admin;

import com.projectmd5.model.dto.MessageResponse;
import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.Product;
import com.projectmd5.model.entity.ProductDetail;
import com.projectmd5.service.IProductDetailService;
import com.projectmd5.service.IProductService;
import com.projectmd5.validation.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.projectmd5.constants.MessageConstant.PRODUCT_NOT_FOUND;
import static com.projectmd5.constants.PathConstant.*;


@RequiredArgsConstructor
@RestController
@RequestMapping(API_V1_ADMIN)
public class ProductController {

   private final IProductService productService;
   private final IProductDetailService productDetailService;
   private final ProductValidator validator;

   @GetMapping(PRODUCTS)
   public ResponseEntity<?> getList(
         @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(name = "sortBy", defaultValue = "productId", required = false) String sortBy,
         @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir,
         @RequestParam(name = "productName", defaultValue = "", required = false) String name
   ){
      ProPageResponse proPageResponse = null;
      if (Objects.equals(name, "") || name.isBlank()){
         proPageResponse = productService.getAllWithPaging(pageNo, pageSize, sortBy, sortDir);
         return ResponseEntity.ok(proPageResponse);
      }
      proPageResponse = productService.searchWithPaging(name, pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok(proPageResponse);
   }

   @GetMapping(PRODUCT_ID)
   public ResponseEntity<?> getProduct(@PathVariable Long productId){
      Product product = productService.findById(productId);
      if (product == null){
         return new ResponseEntity<>(
               new MessageResponse(PRODUCT_NOT_FOUND),
               HttpStatus.NOT_FOUND);
      }
      return ResponseEntity.ok(product);
   }

   @PostMapping(value = PRODUCTS)
   public ResponseEntity<?> addProduct(@ModelAttribute ProductRequest proRequest,
                                       BindingResult bindingResult) {

      validator.validate(proRequest, bindingResult);

      if (bindingResult.hasErrors()){
         Map<String, String> errors = new HashMap<>();
         bindingResult.getFieldErrors().forEach(err ->
               errors.put(err.getField(), err.getCode()));
         return ResponseEntity.badRequest().body(errors);
      }

      Product product = productService.create(proRequest);
      List<ProductDetail> proDetail = productDetailService.create(product, proRequest);
      product.setProductDetails(proDetail);
      productService.save(product);

      return new ResponseEntity<>(product, HttpStatus.CREATED);
   }

   @PutMapping(PRODUCT_ID)
   public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                          @ModelAttribute ProductRequest proRequest,
                                          BindingResult bindingResult){
      validator.validate(proRequest, bindingResult);

      if (bindingResult.hasErrors()){
         Map<String, String> errors = new HashMap<>();
         bindingResult.getFieldErrors().forEach(err ->
               errors.put(err.getField(), err.getCode()));
         return ResponseEntity.badRequest().body(errors);
      }

      Product product = productService.findById(productId);
      if (product == null){
         return new ResponseEntity<>(
               new MessageResponse(PRODUCT_NOT_FOUND),
               HttpStatus.NOT_FOUND);
      }
      Product productUpdate = productService.update(product,proRequest);
      List<ProductDetail> proDetail = productDetailService.update(productUpdate, proRequest);
      product.setProductDetails(proDetail);
      productService.save(product);
      return ResponseEntity.ok(product);
   }

   @DeleteMapping(PRODUCT_ID)
   public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
      Product product = productService.findById(productId);
      if (product == null){
         return new ResponseEntity<>(
               new MessageResponse(PRODUCT_NOT_FOUND),
               HttpStatus.NOT_FOUND);
      }
      productService.delete(product);
      return ResponseEntity.ok(product);
   }
}
