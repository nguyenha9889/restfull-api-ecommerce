package com.projectmd5.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectmd5.validation.EmailUnique;
import com.projectmd5.validation.PasswordMatching;
import com.projectmd5.validation.PhoneUnique;
import com.projectmd5.validation.UserNameUnique;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatching(
      password = "password",
      confirmPassword = "confirmPassword"
)
@Builder
public class RegisterRequest {
   @NotBlank(message = "Full name can not be blank")
   private String fullName;

   @NotBlank(message = "User name can not be blank")
   @Pattern(regexp = "(?!.*[@#$%^&+=!*()]).{6,30}",
         message = "Username must be from 6 to 20 characters and does not contain any special characters")
   @UserNameUnique
   private String username;

   @NotBlank(message = "The email is required.")
   @Email(message = "The email is not a valid email.")
   @EmailUnique
   private String email;

   @Pattern(regexp = "^(84|0[3|5|7|8|9])+([0-9]{8})$", message = "Phone number is not valid")
   @PhoneUnique
   private String phone;

   @JsonIgnore
   @NotBlank(message = "Password can not be blank")
   @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z]).{8,}$", message = "Password must be 8 characters long and combination of lowercase letters, numbers")
   private String password;

   @JsonIgnore
   @NotBlank(message = "ConfirmPassword can not be blank")
   private String confirmPassword;

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public void setUsername(String username) {
      this.username = username.trim();
   }

   public void setEmail(String email) {
      this.email = email.trim();
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