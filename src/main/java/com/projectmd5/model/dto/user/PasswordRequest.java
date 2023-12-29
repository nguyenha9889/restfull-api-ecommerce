package com.projectmd5.model.dto.user;

import com.projectmd5.validation.PasswordMatching;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatching(
      password = "password",
      confirmPassword = "confirmPassword"
)
public class PasswordRequest {
   private Long userId;
   private String username;

   @NotBlank(message = "Yêu cầu nhập mật khẩu cũ")
   private String oldPassword;
   @NotBlank(message = "Yêu cầu nhập mật khẩu mới")
   private String password;
   private String confirmPassword;

}
