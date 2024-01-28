package com.projectmd5.model.dto.user;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
   private String fullName;
   private String email;
   private String phone;
   private MultipartFile image;
   private String avatar;
}
