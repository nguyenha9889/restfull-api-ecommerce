package com.projectmd5.model.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projectmd5.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
   private Long productId;
   private String productName;
   private List<ProductDetailResponse> productDetails;
   private String description;
   private Category category;
   private String imagePath;
   @JsonFormat(pattern = "MM-dd-yyyy")
   private Date createdAt;
   @JsonFormat(pattern = "MM-dd-yyyy")
   private Date updatedAt;
}
