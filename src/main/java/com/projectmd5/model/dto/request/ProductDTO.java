package com.projectmd5.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectmd5.validation.TypeFile;
import com.projectmd5.validation.ProductNameUnique;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
   private Long productId;

   @NotBlank(message = "Product name is not blank")
   @ProductNameUnique
   private String productName;

   private String sku;

   @NotBlank(message = "Description is not blank")
   private String description;
   @NotNull(message = "CategoryId is not null")
   private Long categoryId;
   @Positive(message = "Unit price must be larger than 0")
   private BigDecimal unitPrice;
   @PositiveOrZero(message = "Quantity must be equal or larger than 0")
   private int quantity;
   @JsonIgnore

   @TypeFile
   private MultipartFile image;
   private String imagePath;

   private Date createdAt;
   private Date updatedAt;

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
      this.description = description.trim();
   }

   public void setCategoryId(Long categoryId) {
      this.categoryId = categoryId;
   }

   public void setUnitPrice(BigDecimal unitPrice) {
      this.unitPrice = unitPrice;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   public void setImage(MultipartFile image) {
      this.image = image;
   }

   public void setImagePath(String imagePath) {
      this.imagePath = imagePath;
   }

   public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
   }

   public void setUpdatedAt(Date updatedAt) {
      this.updatedAt = updatedAt;
   }
}
