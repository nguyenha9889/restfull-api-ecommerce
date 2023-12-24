package com.projectmd5.controller.admin;

import com.projectmd5.model.dto.request.ProductDTO;
import com.projectmd5.model.dto.response.ProductResponse;
import com.projectmd5.model.entity.Product;
import com.projectmd5.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
      ProductResponse response = productService.getAll(pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok(response);
   }

   @GetMapping("/{productId}")
   public ResponseEntity<?> getCategory(@PathVariable Long productId){
      Product pro = productService.findById(productId);
      return ResponseEntity.ok(pro);
   }
   @PostMapping(produces = {"multipart/form-data; charset=UTF-8"})
   public ResponseEntity<?> addCategory(@Valid @RequestPart("data") ProductDTO proDTO,
                                        @RequestPart("file") MultipartFile file){
      Product pro = productService.create(proDTO);
      productService.save(pro);
      ProductDTO productDTO = modelMapper.map(pro, ProductDTO.class);
      return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
   }

   @PutMapping("/{productId}")
   public ResponseEntity<?> updateCategory(@Valid @RequestBody ProductDTO proDTO,
                                           @PathVariable Long productId){
      Product pro = productService.edit(proDTO, productId);
      productService.save(pro);
      ProductDTO productDTO = modelMapper.map(pro, ProductDTO.class);
      return ResponseEntity.ok(productDTO);
   }

   @DeleteMapping("/{productId}")
   public ResponseEntity<?> deleteCategory(@PathVariable Long productId){
      productService.delete(productId);
      return ResponseEntity.ok("Product deleted successfully!");
   }
}
