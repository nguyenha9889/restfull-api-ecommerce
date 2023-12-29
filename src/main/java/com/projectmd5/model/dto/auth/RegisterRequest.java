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
import org.springframework.beans.factory.annotation.Value;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatching(
      password = "password",
      confirmPassword = "confirmPassword"
)
@Builder
public class RegisterRequest {
   @NotBlank(message = "Full name không được để trống")
   private String fullName;

   @NotBlank(message = "User name không được để trống")
   @UsernameUnique
   private String username;

   @NotBlank(message = "Email không được để trống")
   @Email(message = "Email không hợp lệ")
   @EmailUnique
   private String email;

   @Pattern(regexp = "^(84|0[3|5|7|8|9])+([0-9]{8})$", message = "Số điện thoại không hợp lệ")
   @PhoneUnique
   private String phone;

   @NotBlank(message = "Mật khẩu không được để trống")
   @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z]).{8,}$", message = "Mật khẩu dài tối thiểu 8 ký tự, bao gồm số và chữ thường")
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
