package com.projectmd5.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {
   private String sku;
   private String size;
   private String dough;
   private Long price;
}
