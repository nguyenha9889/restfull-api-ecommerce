package com.projectmd5.controller.admin;

import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.Product;
import com.projectmd5.model.entity.ProductDetail;
import com.projectmd5.service.IProductDetailService;
import com.projectmd5.service.IProductService;
import com.projectmd5.validation.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.projectmd5.constants.MessageConstant.DELETE_SUCCESS;
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
         @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(value = "sortBy", defaultValue = "productId", required = false) String sortBy,
         @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
   ){
      Pageable pageable = productService.getPageable(pageNo, pageSize, sortBy, sortDir);
      ProPageResponse response = productService.getAllWithPaging(pageable);
      return ResponseEntity.ok(response);
   }

   @GetMapping(PRODUCT_ID)
   public ResponseEntity<?> getProduct(@PathVariable Long productId){
      Product product = productService.findById(productId);
      return ResponseEntity.ok(product);
   }
   @PostMapping(PRODUCT_ID)
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
      List<ProductDetail> proDetail = productDetailService.create(product.getProductId(), proRequest);
      product.setProductDetail(proDetail);
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

      Product product = productService.update(productId,proRequest);
      List<ProductDetail> proDetail = productDetailService.update(productId, proRequest);
      product.setProductDetail(proDetail);
      productService.save(product);
      return ResponseEntity.ok(product);
   }

   @DeleteMapping(PRODUCT_ID)
   public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
      productService.delete(productId);
      return ResponseEntity.ok(DELETE_SUCCESS);
   }
}
