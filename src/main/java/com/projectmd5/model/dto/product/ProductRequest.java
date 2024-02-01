package com.projectmd5.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
   private Long productId;
   private String productName;
   private List<ProductDetailRequest> productDetails;
   private String description;
   private Long categoryId;
   private List<MultipartFile> images;
   private String imagePath;
}
