package com.projectmd5.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
   @NotBlank(message = "User name can not be blank")
   private String username;
   @NotBlank(message = "Password can not be blank")
   private String password;

   public void setUsername(String username) {
      this.username = username.trim();
   }

   public void setPassword(String password) {
      this.password = password.trim();
   }
}
