package com.projectmd5.model.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
   @Setter
   private Long productId;
   private String productName;
   @Setter
   private String sku;
   private String size;
   private String dough;
   @Setter
   private String description;
   @Setter
   private Long categoryId;
   @Setter
   private BigDecimal unitPrice;
   @Setter
   private MultipartFile image;
   @Setter
   private String imagePath;

   public void setProductName(String productName) {
      this.productName = productName.trim();
   }
}
