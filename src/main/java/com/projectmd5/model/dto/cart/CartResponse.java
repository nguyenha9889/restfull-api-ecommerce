package com.projectmd5.model.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
   private Long cartId;
   private Long userId;
   private List<CartItemResponse> cartItems;
   private Long totalPrice;
}
