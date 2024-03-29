package com.projectmd5.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPageResponse {
   private List<AccountResponse> data;
   private int pageNo;
   private int pageSize;
   private long totalElements;
   private int totalPages;
   private boolean last;
}