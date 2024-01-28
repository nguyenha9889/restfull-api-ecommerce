package com.projectmd5.model.dto.order;

import com.projectmd5.model.dto.product.ProductDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailResponse {
      private Long productId;
      private String productName;
      private String imagePath;
      private ProductDetailResponse productDetail;
      private int quantity;
}
