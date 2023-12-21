package com.projectmd5.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
   @NotBlank
   private String fullName;

   @Size(min = 6, max = 100)
   private String username;


   private String email;


   private String phone;


   private String password;
   private String confirmPassword;

   private String avatar;

   private String address;
}
