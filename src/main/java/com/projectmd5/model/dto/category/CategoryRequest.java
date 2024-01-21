package com.projectmd5.model.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
   @Setter
   private Long categoryId;
   private String categoryName;
   @Setter
   private String description;
   @Setter
   private Boolean status;

   public void setCategoryName(String categoryName) {
      this.categoryName = categoryName.trim();
   }

}
