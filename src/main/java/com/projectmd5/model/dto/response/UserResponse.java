package com.projectmd5.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
   private Long userId;
   private String username;
   private String email;
   private List<String> roles;
   private String accessToken;
   private String refreshToken;
   private final String type = "Bearer Token";
}