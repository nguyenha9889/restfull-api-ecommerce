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
public class CartItemResponse {
   private Long cartItemId;
   private ProductDetailResponse productDetail;
   private int quantity;
}
