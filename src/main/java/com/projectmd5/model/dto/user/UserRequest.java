package com.projectmd5.model.dto.user;

import com.projectmd5.validation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
   private Long userId;

   @NotBlank(message = "Full name can not be blank")
   private String fullName;

   @NotBlank(message = "User name can not be blank")
   @UsernameUnique(username = "username")
   private String username;

   @NotBlank(message = "The email is required.")
   @Email(message = "The email is not a valid email.")
   @EmailUnique(email = "email")
   private String email;

   @Pattern(regexp = "^(84|0[3|5|7|8|9])+([0-9]{8})$", message = "Phone number is not valid")
   @PhoneUnique(phone = "phone")
   private String phone;

   @TypeFile
   private MultipartFile image;
   private String avatar;
   private String address;

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

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public void setImage(MultipartFile image) {
      this.image = image;
   }

   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }

   public void setAddress(String address) {
      this.address = address;
   }
}
