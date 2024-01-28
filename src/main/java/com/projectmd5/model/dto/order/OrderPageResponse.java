package com.projectmd5.model.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPageResponse {
   private List<OrderResponse> data;
   private int pageNo;
   private int pageSize;
   private long totalElements;
   private int totalPages;
   private boolean last;
}
