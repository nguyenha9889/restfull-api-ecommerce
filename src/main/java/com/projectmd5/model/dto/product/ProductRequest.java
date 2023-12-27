package com.projectmd5.model.dto.product;

import com.projectmd5.validation.TypeFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

   @NotBlank(message = "Product name is not blank")
   private String productName;

   private String sku;
   private String size;
   private String dough;

   @NotBlank(message = "Description is not blank")
   private String description;
   @NotNull(message = "CategoryId is not null")
   private Long categoryId;
   @Positive(message = "Unit price must be larger than 0")
   private BigDecimal unitPrice;

   @TypeFile
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
