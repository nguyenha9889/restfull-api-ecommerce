package com.projectmd5.model.dto.auth;

import com.projectmd5.validation.EmailUnique;
import com.projectmd5.validation.PasswordMatching;
import com.projectmd5.validation.PhoneUnique;
import com.projectmd5.validation.UsernameUnique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.projectmd5.constants.MessageConstant.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatching(
      password = "password",
      confirmPassword = "confirmPassword"
)
@Builder
public class RegisterRequest {
   @NotBlank(message = FULLNAME_NOT_BLANK)
   private String fullName;

   @NotBlank(message = USERNAME_NOT_BLANK)
   @UsernameUnique
   private String username;

   @NotBlank(message = EMAIL_NOT_BLANK)
   @Email(message = EMAIL_INVALID)
   @EmailUnique
   private String email;

   @Pattern(regexp = "^(84|0[3|5|7|8|9])+([0-9]{8})$", message = PHONE_INVALID)
   @PhoneUnique
   private String phone;

   @Pattern(regexp = "(?=.*\\d)(?=.*[a-z]).{8,}$", message = PASSWORD_RULE)
   private String password;

   private String confirmPassword;

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public void setUsername(String username) {
      this.username = username.toLowerCase().trim();
   }

   public void setEmail(String email) {
      this.email = email.toLowerCase().trim();
   }

   public void setPhone(String phone) {
      this.phone = phone.trim();
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setConfirmPassword(String confirmPassword) {
      this.confirmPassword = confirmPassword;
   }
}
