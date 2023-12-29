package com.projectmd5.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
   private Long productId;
   private String productName;
   private String sku;
   private String size;
   private String dough;
   private String description;
   private Long categoryId;
   private BigDecimal unitPrice;
   private MultipartFile image;
   private String imagePath;

   public void setProductId(Long productId) {
      this.productId = productId;
   }

   public void setProductName(String productName) {
      this.productName = productName.trim();
   }

   public void setSku(String sku) {
      this.sku = sku;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setCategoryId(Long categoryId) {
      this.categoryId = categoryId;
   }

   public void setUnitPrice(BigDecimal unitPrice) {
      this.unitPrice = unitPrice;
   }

   public void setImage(MultipartFile image) {
      this.image = image;
   }

   public void setImagePath(String imagePath) {
      this.imagePath = imagePath;
   }
}
