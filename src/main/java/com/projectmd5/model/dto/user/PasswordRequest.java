package com.projectmd5.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {
   private Long userId;
   private String username;
   private String oldPassword;
   private String password;
   private String confirmPassword;

}
