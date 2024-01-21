package com.projectmd5.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.projectmd5.constants.MessageConstant.PASSWORD_NOT_BLANK;
import static com.projectmd5.constants.MessageConstant.USERNAME_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
   @NotBlank(message = USERNAME_NOT_BLANK)
   private String username;
   @NotBlank(message = PASSWORD_NOT_BLANK)
   private String password;
}
