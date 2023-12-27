package com.projectmd5.model.dto.product;

import com.projectmd5.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseProductResponse {
   private Long productId;
   private String productName;
   private String sku;
   private String size;
   private String dough;
   private String description;
   private Long categoryId;
   private BigDecimal unitPrice;
   private String imagePath;
}
