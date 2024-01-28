package com.projectmd5.model.dto.category;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
   private Long categoryId;
   private String categoryName;
   private String description;
   private Boolean status;
}
