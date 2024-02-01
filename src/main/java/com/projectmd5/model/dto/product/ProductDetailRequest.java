package com.projectmd5.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {
   private Long id;
   private String size;
   private String dough;
   private Long unitPrice;
}
