package com.projectmd5.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
   @Setter
   private Long productId;
   private String productName;
   private List<ProductDetailRequest> productDetails;
   @Setter
   private String description;
   @Setter
   private Long categoryId;
   @Setter
   private MultipartFile image;
   @Setter
   private String imagePath;

   public void setProductName(String productName) {
      this.productName = productName.trim();
   }
}
