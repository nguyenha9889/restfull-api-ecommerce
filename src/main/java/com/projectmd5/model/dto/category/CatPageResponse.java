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
public class CatPageResponse {
   private List<Category> data;
   private int pageNo;
   private int pageSize;
   private long totalElements;
   private int totalPages;
   private boolean last;
}
