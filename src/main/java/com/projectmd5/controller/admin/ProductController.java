package com.projectmd5.controller.admin;

import com.projectmd5.exception.BadRequestException;
import com.projectmd5.model.dto.product.BaseProductResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.entity.Product;
import com.projectmd5.service.FilesStorageService;
import com.projectmd5.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api.myservice.com/v1/admin/products")
public class ProductController {

   private final IProductService productService;
   private final ModelMapper modelMapper;
   private final FilesStorageService storageService;

   @GetMapping
   public ResponseEntity<?> getList(
         @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(value = "sortBy", defaultValue = "productId", required = false) String sortBy,
         @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
   ){
      ProPageResponse response = productService.getAll(pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok(response);
   }

   @GetMapping("/{productId}")
   public ResponseEntity<?> getProduct(@PathVariable Long productId){
      Product pro = productService.findById(productId);
      return ResponseEntity.ok(pro);
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
   public ResponseEntity<?> updateCategory(@PathVariable Long productId, @Valid @RequestBody ProductRequest proRequest){

      if (productService.existProductName(productId, proRequest.getProductName())){
         throw new BadRequestException("Product name is existed!");
      }

      BaseProductResponse response = productService.update(productId,proRequest);
      return ResponseEntity.ok(response);
   }

   @DeleteMapping("/{productId}")
   public ResponseEntity<?> deleteCategory(@PathVariable Long productId){
      productService.delete(productId);
      return ResponseEntity.ok("Product deleted successfully!");
   }

   @PostMapping("/upload")
   public ResponseEntity<?> upload(@RequestParam MultipartFile file){
      if (file == null || file.isEmpty()){
         throw new MultipartException("pls upload file");
      }
      String image = storageService.uploadFile(file);
      ProductRequest dto = new ProductRequest();
      dto.setProductName("Test upload");
      dto.setImagePath(image);
      return ResponseEntity.ok().body(dto);
   }
}
