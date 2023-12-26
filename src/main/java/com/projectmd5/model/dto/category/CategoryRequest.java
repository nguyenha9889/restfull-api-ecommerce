package com.projectmd5.model.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
   private Long categoryId;

   @NotBlank(message = "Category name is not blank")
   private String categoryName;

   @NotBlank(message = "Description is not blank")
   private String description;

   private Boolean status;

   public void setCategoryId(Long categoryId) {
      this.categoryId = categoryId;
   }

   public void setCategoryName(String categoryName) {
      this.categoryName = categoryName.trim();
   }

   public void setDescription(String description) {
      this.description = description.trim();
   }

   public void setStatus(Boolean status) {
      this.status = status;
   }
}
