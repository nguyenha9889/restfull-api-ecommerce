package com.projectmd5.model.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateRequest {
   @NotNull(message = "Số lượng không được để trống")
   @Min(value =0, message = "Số lượng sản phẩm tối thiểu bằng 0")
   private Integer quantity;
}
