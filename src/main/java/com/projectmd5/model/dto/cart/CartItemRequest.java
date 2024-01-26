package com.projectmd5.model.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {
   @NotNull
   private Long productId;
   @NotNull
   private String sku; // product detail Id
   @Min(value = 1, message = "Số lượng sản phẩm phải lớn hơn 0")
   private int quantity;
}
