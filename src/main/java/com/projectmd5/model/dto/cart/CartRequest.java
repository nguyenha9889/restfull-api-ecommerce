package com.projectmd5.model.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
   private Long cartId;
   @NotNull(message = "productId not null")
   private Long productId;
   @NotNull(message = "sku not null")
   private String sku;
   @Min(value = 1, message = "Số lượng sản phẩm phải lớn hơn 0")
   private int quantity;
}
