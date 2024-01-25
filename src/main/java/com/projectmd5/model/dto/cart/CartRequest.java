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
   @NotNull
   private Long productId;
   @Min(value = 1, message = "Số lượng sản phẩm phải lớn hơn 0")
   private int quantity;
}
