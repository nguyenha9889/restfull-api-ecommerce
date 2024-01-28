package com.projectmd5.model.dto.cart;

import com.projectmd5.model.dto.product.ProductDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
   private Long cartId;
   private Long productId;
   private String productName;
   private String imagePath;
   private ProductDetailResponse productDetail;
   private int quantity;
}
