package com.projectmd5.model.dto.product;

import com.projectmd5.model.entity.Category;
import com.projectmd5.model.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseProductResponse {
   private Long productId;
   private String productName;
   private ProductDetail productDetail;
   private String description;
   private Category category;
   private String imagePath;
}
