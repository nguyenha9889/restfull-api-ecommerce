package com.projectmd5.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
   private Long userId;
   private String accessToken;
   private String refreshToken;
   private Long expiredAt;
   private List<String> roles;
}