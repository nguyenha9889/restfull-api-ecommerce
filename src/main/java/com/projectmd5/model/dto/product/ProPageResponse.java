package com.projectmd5.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProPageResponse {
   List<ProductResponse> data;
   private int pageNo;
   private int pageSize;
   private long totalElements;
   private int totalPages;
   private boolean last;
}
