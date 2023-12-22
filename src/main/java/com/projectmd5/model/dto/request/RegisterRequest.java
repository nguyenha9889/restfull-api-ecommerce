package com.projectmd5.model.dto.request;

import com.projectmd5.validation.EmailUnique;
import com.projectmd5.validation.PasswordMatching;
import com.projectmd5.validation.PhoneUnique;
import com.projectmd5.validation.UserNameUnique;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatching(
      password = "password",
      confirmPassword = "confirmPassword"
)
public class RegisterRequest {
   @NotBlank(message = "Full name can not be blank")
   private String fullName;

   // other pattern special character (?=.*[@#$%^&+=!*()])
   @Size(min = 6, max = 100, message = "Username must be from 6 to 100 characters")
   @Pattern(regexp = "(?!.*\\W)", message = "Username must not contain any special characters")
   @UserNameUnique
   private String username;

   @NotBlank(message = "The email is required.")
   @Email(message = "The email is not a valid email.")
   @EmailUnique
   private String email;

   @Pattern(regexp = "^(84|0[3|5|7|8|9])+([0-9]{8})$", message = "Phone number is not valid")
   @PhoneUnique
   private String phone;

   @NotBlank(message = "Password can not be blank")
   @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z]).{8,}$", message = "Password must be 8 characters long and combination of lowercase letters, numbers")
   private String password;

   @NotBlank(message = "ConfirmPassword can not be blank")
   private String confirmPassword;

   @NotBlank(message = "Address can not be blank")
   private String address;

   private Set<String> roles;
}
