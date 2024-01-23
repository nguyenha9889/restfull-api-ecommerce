package com.projectmd5.model.dto.category;

import com.projectmd5.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListCateResponse {
   private List<Category> data;
   private int totalElements;
}
