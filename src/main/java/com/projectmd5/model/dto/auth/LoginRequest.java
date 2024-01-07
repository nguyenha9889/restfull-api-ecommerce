package com.projectmd5.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static com.projectmd5.constants.MessageConstant.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
   @NotBlank(message = USERNAME_NOT_BLANK)
   private String username;
   @NotBlank(message = PASSWORD_NOT_BLANK)
   private String password;

   public void setUsername(String username) {
      this.username = username.trim();
   }

   public void setPassword(String password) {
      this.password = password.trim();
   }
}
