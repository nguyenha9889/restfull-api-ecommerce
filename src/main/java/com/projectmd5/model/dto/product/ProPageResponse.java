package com.projectmd5.model.dto.product;

import com.projectmd5.model.entity.Product;
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
   List<Product> products;
   private int pageNo;
   private int pageSize;
   private long totalElements;
   private int totalPages;
   private boolean last;
}
