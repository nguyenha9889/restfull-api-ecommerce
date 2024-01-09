package com.projectmd5.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
   private Long userId;
   private String accessToken;
   private String refreshToken;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date expiredAt;
   private List<String> roles;

   public AuthResponse(String accessToken) {
   }
}