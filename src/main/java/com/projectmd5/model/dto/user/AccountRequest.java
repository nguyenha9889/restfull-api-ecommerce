package com.projectmd5.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
   private Long userId;
   private String fullName;
   private String username;
   private String email;
   private String phone;
   private MultipartFile image;
   private String avatar;

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public void setEmail(String email) {
      this.email = email.toLowerCase().trim();
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

}
