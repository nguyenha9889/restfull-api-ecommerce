package com.projectmd5.controller.admin;

import com.projectmd5.exception.BadRequestException;
import com.projectmd5.model.dto.request.ProductDTO;
import com.projectmd5.model.dto.response.ProductResponse;
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
      ProductResponse response = productService.getAll(pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok(response);
   }

   @GetMapping("/{productId}")
   public ResponseEntity<?> getCategory(@PathVariable Long productId){
      Product pro = productService.findById(productId);
      return ResponseEntity.ok(pro);
   }
   @PostMapping
   public ResponseEntity<?> addCategory(@Valid @RequestBody ProductDTO proDTO){

      if (productService.existProductName(null, proDTO.getProductName())){
         throw new BadRequestException("Product name is existed!");
      }
      if (proDTO.getImage() == null || proDTO.getImage().isEmpty()){
         throw new MultipartException("Upload one image with size less than 1MB");
      }
      String imagePath = storageService.uploadFile(proDTO.getImage());

      //String imagePath = proDTO.getImagePath();
      Product pro = productService.create(proDTO);
      pro.setImagePath(imagePath);
      pro.setSku(UUID.randomUUID().toString());
      productService.save(pro);

      ProductDTO productDTO = modelMapper.map(pro, ProductDTO.class);
      return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
   }

   @PutMapping("/{productId}")
   public ResponseEntity<?> updateCategory(@PathVariable Long productId,
                                           @Valid @RequestBody ProductDTO proDTO){

      if (productService.existProductName(productId, proDTO.getProductName())){
         throw new BadRequestException("Product name is existed!");
      }
      Product pro = productService.edit(productId, proDTO);
      if (proDTO.getImage() != null && proDTO.getImage().getSize() > 0){
         pro.setImagePath(storageService.uploadFile(proDTO.getImage()));
      }

      productService.save(pro);
      ProductDTO productDTO = modelMapper.map(pro, ProductDTO.class);
      return ResponseEntity.ok(productDTO);
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
      ProductDTO dto = new ProductDTO();
      dto.setProductName("Test upload");
      dto.setImagePath(image);
      return ResponseEntity.ok().body(dto);
   }
}
