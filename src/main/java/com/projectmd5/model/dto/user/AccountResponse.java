package com.projectmd5.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
   private Long userId;
   private String fullName;
   private String username;
   private String email;
   private String phone;
   private boolean status;
   private String avatar;
}
